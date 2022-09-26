package com.blog.personalblog.service;

import com.blog.personalblog.config.page.PageRequest;
import com.blog.personalblog.entity.OperationLog;
import java.util.List;

/**
 * @author: SuperMan
 * @create: 2022-04-02
 **/
public interface OperationLogService {

    /**
     * 保存操作日志
     *
     * @param operationLog
     * @return
     */
    void saveOperationLog(OperationLog operationLog);

    /**
     * 操作日志列表（分页）
     *
     * @param pageRequest
     * @return
     */
    List<OperationLog> getOperationLogPage(PageRequest pageRequest);

}
