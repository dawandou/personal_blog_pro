package com.blog.personalblog.mapper;

import com.blog.personalblog.bo.TagBO;
import com.blog.personalblog.entity.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: SuperMan
 * @create: 2021-11-28
 */
@Repository
public interface TagMapper {
    /**
     * 创建
     * @param tag
     * @return
     */
    int createTag(Tag tag);

    /**
     * 修改
     * @param tag
     * @return
     */
    int updateTag(Tag tag);

    /**
     * 分类列表（分页）
     * @return
     */
    List<Tag> getTagPage();

    /**
     * 删除
     * @param id
     */
    void deleteTag(Integer id);

    /**
     * 批量添加标签
     * @param strings
     * @return
     */
    boolean batchAddTag(List<Tag> strings);

    /**
     * 批量删除标签
     * @param ids
     */
    boolean deleteBatch(List<Tag> ids);

    /**
     * 根据标签查找该对象
     * @param tag
     * @return
     */
    Tag getByTagName(Tag tag);

    /**
     * 根据标签id查找标签
     *
     * @param tagId
     * @return
     */
    Tag getTagById(@Param("tagId") Integer tagId);

    /**
     * 查询文章标签
     * @param bo
     * @return
     */
    List<Tag> findTagsByTagName(TagBO bo);
}
