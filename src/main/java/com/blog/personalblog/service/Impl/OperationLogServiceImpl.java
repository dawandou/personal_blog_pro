package com.blog.personalblog.service.Impl;

import com.blog.personalblog.config.page.PageRequest;
import com.blog.personalblog.entity.OperationLog;
import com.blog.personalblog.mapper.OperationLogMapper;
import com.blog.personalblog.service.OperationLogService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: SuperMan
 * @create: 2022-04-02
 **/

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Resource
    OperationLogMapper operationLogMapper;

    @Override
    public void saveOperationLog(OperationLog operationLog) {
        operationLogMapper.createOperationLog(operationLog);
    }

    @Override
    public List<OperationLog> getOperationLogPage(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum,pageSize);
        List<OperationLog> operationLogList = operationLogMapper.getOperationLogPage();
        return operationLogList;
    }

}
