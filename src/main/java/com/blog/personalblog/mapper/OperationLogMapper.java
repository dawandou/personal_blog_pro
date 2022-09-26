package com.blog.personalblog.mapper;

import com.blog.personalblog.entity.OperationLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: SuperMan
 * @create: 2022-04-02
 **/
@Repository
public interface OperationLogMapper {

    /**
     * 创建操作日志
     * @param operationLog
     * @return
     */
    int createOperationLog(OperationLog operationLog);

    /**
     * 分类列表（分页）
     * @return
     */
    List<OperationLog> getOperationLogPage();

}
