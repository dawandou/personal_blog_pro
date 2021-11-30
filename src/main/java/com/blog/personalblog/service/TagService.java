package com.blog.personalblog.service;

import com.blog.personalblog.config.page.PageRequest;
import com.blog.personalblog.entity.Tag;

import java.util.List;

/**
 * @author: SuperMan
 * @create: 2021-11-28
 */
public interface TagService {

    /**
     * 获取所有的标签（分页）
     * @return
     */
    List<Tag> getTagPage(PageRequest pageRequest);

    /**
     * 新建标签
     * @param tag
     * @return
     */
    int saveTag(Tag tag);

    /**
     * 修改标签
     * @param tag
     * @return
     */
    int updateTag(Tag tag);

    /**
     * 删除标签
     * @param tagId
     */
    void deleteTag(Integer tagId);

    /**
     * 批量添加
     * @param tags
     * @return
     */
    boolean batchAddTag(String tags) throws Exception;

    /**
     * 批量删除标签
     * @param ids
     * @return
     */
    boolean batchDelTag(String ids);

    /**
     * 根据标签查找
     * @param tagName
     * @return
     */
    Tag findByTagName(String tagName);

}
