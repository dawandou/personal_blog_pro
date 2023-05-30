package com.blog.personalblog.service;

import com.blog.personalblog.config.page.PageRequest;
import com.blog.personalblog.entity.Notice;

import java.util.List;

/**
 * @author: SuperMan
 * @create: 2021-11-23
 */
public interface NoticeService {
    /**
     * 获取所有的分类（分页）
     * @return
     */
    List<Notice> getNoticePage(PageRequest pageRequest);

    /**
     * 新建分类
     * @param notice
     * @return
     */
    int saveNotice(Notice notice);

    /**
     * 修改分类
     * @param notice
     * @return
     */
    int updateNotice(Notice notice);

    /**
     * 删除分类
     * @param noticeId
     */
    void deleteNotice(Integer noticeId);

    /**
     * 根据公告id获取公告
     * @param noticeId
     * @return
     */
    Notice getNoticeById(Integer noticeId);

    /**
     * 获取前5条公告
     * @return
     */
    List<Notice> getNoticeTopFive();


}
