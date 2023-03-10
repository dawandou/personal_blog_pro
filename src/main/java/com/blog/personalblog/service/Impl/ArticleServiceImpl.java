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
     * ????????????
     */
    @Value("${upload.local.path}")
    private String localPath;

    /**
     * ??????url
     */
    @Value("${upload.local.url}")
    private String localUrl;

    private static final String ARTICLE = "articles/";

    /**
     * key:??????id
     * value: ??????
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
            log.info("??????????????????????????????");
        } catch (Exception e) {
            log.error("?????????????????????????????????", e.getMessage());
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
                //????????????
                Category category = categoryService.findById(article.getCategoryId());
                if (category == null) {
                    article.setCategoryName("?????????");
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
        //????????????
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
        //??????????????????
        saveTags(bo, article.getId());
        this.init();
        //??????????????????????????????
        try {
            String content = "???{0}????????????\n" +
                    "??????????????????????????????: {1} ????????? \n" +
                    "??????????????????\n";
            MailInfo build = MailInfo.builder()
                    .receiveMail(user.getEmail())
                    .content(MessageFormat.format(content, user.getUserName(), article.getTitle()))
                    .title("????????????")
                    .build();
            SendMailConfig.sendMail(build);
        } catch (Exception e) {
            log.error("??????????????????{}", e.getMessage());
        }

    }

    private Category saveCategory(ArticleInsertBO bo) {
        if (StrUtil.isEmpty(bo.getCategoryName())) {
            return null;
        }
        Category category = categoryService.getCategoryByName(bo.getCategoryName());
        if (category == null && !ArticleArtStatusEnum.DRAFT.getStatus().equals(bo.getArtStatus())) {
            category.setCategoryName(bo.getCategoryName());
            categoryService.saveCategory(category);
        }
        return category;
    }

    private void saveTags(ArticleInsertBO bo, Integer articleId) {
        //?????????????????????????????????
        if (bo.getId() == null) {
            articleTagService.deleteTag(bo.getId());
        }
        //??????????????????
        List<String> tagNameList = bo.getTagNameList();
        List<Integer> tagIdList = new ArrayList<>();

        if (CollUtil.isNotEmpty(tagNameList)) {
            //?????????????????????????????????????????????
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
        //??????????????????????????????????????????
        articleTagService.deleteTag(article.getId());
        //??????????????????
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
        //?????????????????????
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
            // ????????????md5???
            String md5 = FileUtils.getMd5(file.getInputStream());
            // ?????????????????????
            String extName = FileUtils.getExtName(file.getOriginalFilename());
            // ?????????????????????
            String fileName = md5 + extName;
            // ???????????????????????????
            if (!exists(ARTICLE + fileName)) {
                // ????????????????????????
                upload(ARTICLE, fileName, file.getInputStream());
            }

            // ????????????????????????
            return getFileAccessUrl(ARTICLE + fileName);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("??????????????????");
        }
        return null;
    }

    private void upload(String path, String fileName, InputStream inputStream) throws IOException {
        File directory = new File(localPath + path);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                log.error("??????????????????");
            }
        }
        // ????????????
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
     * ????????????????????????
     *
     * @param filePath ????????????
     * @return
     */
    public Boolean exists(String filePath){
        return new File(localPath + filePath).exists();
    }

    /**
     * ??????????????????url
     *
     * @param filePath ????????????
     * @return
     */
    public String getFileAccessUrl(String filePath) {
        return localUrl + localPath + filePath;
    }

}
