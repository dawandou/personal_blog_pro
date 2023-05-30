package com.blog.personalblog.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.blog.personalblog.bo.ArticleBO;
import com.blog.personalblog.bo.ArticleInsertBO;
import com.blog.personalblog.common.ArticleArtStatusEnum;
import com.blog.personalblog.config.mail.MailInfo;
import com.blog.personalblog.config.mail.SendMailConfig;
import com.blog.personalblog.entity.Article;
import com.blog.personalblog.entity.ArticleTag;
import com.blog.personalblog.entity.Category;
import com.blog.personalblog.entity.Tag;
import com.blog.personalblog.entity.User;
import com.blog.personalblog.mapper.ArticleMapper;
import com.blog.personalblog.service.ArticleService;
import com.blog.personalblog.service.ArticleTagService;
import com.blog.personalblog.service.CategoryService;
import com.blog.personalblog.service.TagService;
import com.blog.personalblog.service.UserService;
import com.blog.personalblog.util.FileUtils;
import com.blog.personalblog.util.WordCountUtil;
import com.blog.personalblog.vo.ArticleVO;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: SuperMan
 * @create: 2021-12-01
 */
@Log4j2
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;
    @Resource
    ArticleTagService articleTagService;
    @Resource
    TagService tagService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private UserService userService;

    /**
     * 本地路径
     */
    @Value("${upload.local.path}")
    private String localPath;

    /**
     * 访问url
     */
    @Value("${upload.local.url}")
    private String localUrl;

    private static final String ARTICLE = "articles/";

    /**
     * key:文章id
     * value: 文章
     */
    Map<Integer, Article> articleMap = new LinkedHashMap<>();


    @Override
    @PostConstruct
    public void init() {
        List<Article> articleList = articleMapper.findAll();
        try {
            getTagsOrCategory(articleList);
            for(Article article : articleList) {
                articleMap.put(article.getId(), article);
            }
            log.info("文章缓存数据加载完成");
        } catch (Exception e) {
            log.error("文章缓存数据加载失败！", e.getMessage());
        }
    }

    @Override
    public List<Article> getArticlePage(ArticleBO articleBO) {
        int pageNum = articleBO.getPageNum();
        int pageSize = articleBO.getPageSize();
        PageHelper.startPage(pageNum,pageSize, "id desc");
        List<Article> articleList = articleMapper.getArticlePage(articleBO);
        getTagsOrCategory(articleList);
        return articleList;
    }


    public void getTagsOrCategory(List<Article> list) {
        if (list != null) {
            for (Article article : list) {
                //查询分类
                Category category = categoryService.findById(article.getCategoryId());
                if (category == null) {
                    article.setCategoryName("无分类");
                } else {
                    article.setCategoryName(category.getCategoryName());
                }
                List<Tag> tagList = new ArrayList<>();
                List<ArticleTag> articleTags = articleTagService.findArticleTagById(article.getId());
                if (articleTags != null) {
                    for (ArticleTag articleTag : articleTags) {
                        Tag tag = tagService.findTagById(articleTag.getTagId());
                        tagList.add(tag);
                    }
                }
                article.setTagList(tagList);
            }
        }
    }

    @Override
    public void insertOrUpdateArticle(ArticleInsertBO bo) {
        //分类添加
        Category category = saveCategory(bo);
        Article article = BeanUtil.copyProperties(bo, Article.class);
        if (category != null) {
            article.setCategoryId(category.getCategoryId());
        }
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.getUserByUserName(username);
        article.setUserId(user.getId());
        article.setAuthor(user.getUserName());
        article.setViews(0L);
        article.setTotalWords(WordCountUtil.wordCount(bo.getContent()));
        if (bo.getId() != null) {
            articleMapper.updateArticle(article);
        } else {
            articleMapper.createArticle(article);
        }
        articleMap.put(article.getId(), article);
        //添加文章标签
        saveTags(bo, article.getId());
        this.init();
        //添加文章发送邮箱提醒
        try {
            String content = "【{0}】您好：\n" +
                    "您已成功发布了标题为: {1} 的文章 \n" +
                    "请注意查收！\n";
            MailInfo build = MailInfo.builder()
                    .receiveMail(user.getEmail())
                    .content(MessageFormat.format(content, user.getUserName(), article.getTitle()))
                    .title("文章发布")
                    .build();
            SendMailConfig.sendMail(build);
        } catch (Exception e) {
            log.error("邮件发送失败{}", e.getMessage());
        }

    }

    private Category saveCategory(ArticleInsertBO bo) {
        if (StrUtil.isEmpty(bo.getCategoryName())) {
            return null;
        }
        Category cat = new Category();
        Category category = categoryService.getCategoryByName(bo.getCategoryName());
        if (category == null && !ArticleArtStatusEnum.DRAFT.getStatus().equals(bo.getArtStatus())) {
            cat.setCategoryName(bo.getCategoryName());
            categoryService.saveCategory(cat);
        }
        return cat;
    }

    private void saveTags(ArticleInsertBO bo, Integer articleId) {
        //首先判断是不是更新文章
        if (bo.getId() == null) {
            articleTagService.deleteTag(bo.getId());
        }
        //添加文章标签
        List<String> tagNameList = bo.getTagNameList();
        List<Integer> tagIdList = new ArrayList<>();

        if (CollUtil.isNotEmpty(tagNameList)) {
            //先查看添加的标签数据库里有没有
            for (String tagName : tagNameList) {
                Tag one = tagService.findByTagName(tagName);
                if (one == null) {
                    Tag tag = new Tag();
                    tag.setTagName(tagName);
                    tagService.saveTag(tag);
                    tagIdList.add(tag.getId());
                } else {
                    tagIdList.add(one.getId());
                }
            }
        }
        articleTagService.deleteTag(articleId);
        if (tagIdList != null) {
            List<ArticleTag> articleTagList = tagIdList.stream().map(tagId -> ArticleTag.builder()
                    .tagId(tagId)
                    .articleId(articleId)
                    .build()).collect(Collectors.toList());
            articleTagService.insertBatch(articleTagList);
        }
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
        articleMap.put(article.getId(), article);
        //更新文章先把原来的标签删除掉
        articleTagService.deleteTag(article.getId());
        //添加文章标签
        if (article.getTagIdList() != null) {
            List<ArticleTag> articleTagList = article.getTagIdList().stream().map(tagId -> ArticleTag.builder()
                    .tagId(tagId)
                    .articleId(article.getId())
                    .build()).collect(Collectors.toList());
            articleTagService.insertBatch(articleTagList);
        }

    }

    @Override
    public void deleteArticle(Integer articleId) {
        articleMapper.deleteArticle(articleId);
        articleMap.remove(articleId);
        //关联标签删除掉
        articleTagService.deleteTag(articleId);
    }

    @Override
    public ArticleVO findById(Integer articleId) {
        Article article = articleMap.get(articleId);

        ArticleVO articleVO = BeanUtil.copyProperties(article, ArticleVO.class);
        List<String> tagNameList = new ArrayList<>();
        if (articleVO != null) {
            if (articleVO.getTagList() != null) {
                for (Tag tag : articleVO.getTagList()) {
                    tagNameList.add(tag.getTagName());
                }
            }
        }
        articleVO.setTagNameList(tagNameList);
        articleVO.setCategoryName(article.getCategoryName());
        return articleVO;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            // 获取文件md5值
            String md5 = FileUtils.getMd5(file.getInputStream());
            // 获取文件扩展名
            String extName = FileUtils.getExtName(file.getOriginalFilename());
            // 重新生成文件名
            String fileName = md5 + extName;
            // 判断文件是否已存在
            if (!exists(ARTICLE + fileName)) {
                // 不存在则继续上传
                upload(ARTICLE, fileName, file.getInputStream());
            }

            // 返回文件访问路径
            return getFileAccessUrl(ARTICLE + fileName);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件上传失败");
        }
        return null;
    }

    @Override
    public List<Article> getAll() {
        List<Article> all = articleMapper.findAll();
        return all;
    }

    private void upload(String path, String fileName, InputStream inputStream) throws IOException {
        File directory = new File(localPath + path);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                log.error("创建目录失败");
            }
        }
        // 写入文件
        File file = new File(localPath + path + fileName);
        if (file.createNewFile()) {
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bytes = new byte[1024];
            int length;
            while ((length = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, length);
            }
            bos.flush();
            inputStream.close();
            bis.close();
            bos.close();
        }
    }


    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return
     */
    public Boolean exists(String filePath){
        return new File(localPath + filePath).exists();
    }

    /**
     * 获取文件访问url
     *
     * @param filePath 文件路径
     * @return
     */
    public String getFileAccessUrl(String filePath) {
        return localUrl + localPath + filePath;
    }

}
