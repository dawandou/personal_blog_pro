package com.blog.personalblog.config.page;

import lombok.Data;

/**
 * 分页请求
 *
 * @author: SuperMan
 * @create: 2021-11-22
 */
@Data
public class PageRequest {

    /**
     * 页码
     */
    private int pageNum;

    /**
     * 每页的数据条数
     */
    private int pageSize;
}
