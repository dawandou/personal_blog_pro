package com.blog.personalblog.controller;

import cn.hutool.core.collection.CollUtil;
import com.blog.personalblog.common.PageRequestApi;
import com.blog.personalblog.config.page.PageRequest;
import com.blog.personalblog.config.page.PageResult;
import com.blog.personalblog.entity.LoginOperationLog;
import com.blog.personalblog.entity.OperationLog;
import com.blog.personalblog.service.LoginOperationLogService;
import com.blog.personalblog.service.OperationLogService;
import com.blog.personalblog.util.JsonResult;
import com.blog.personalblog.util.PageUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author: SuperMan
 * @create: 2022-04-06
 **/

@Api(tags = "操作日志")
@RestController
@RequestMapping("/log")
public class OperationLogController {

    @Resource
    private LoginOperationLogService loginOperationLogService;

    @Resource
    private OperationLogService operationLogService;

    /**
     * 操作日志列表
     * @param
     * @return
     */
    @ApiOperation(value = "操作日志列表")
    @PostMapping("/operationLog/list")
    public JsonResult<Object> OperationLogListPage(@RequestBody @Valid PageRequestApi<PageRequest> pageRequest) {
        List<OperationLog> operationLogPage = operationLogService.getOperationLogPage(pageRequest.getBody());
        PageInfo pageInfo = new PageInfo(operationLogPage);
        PageResult pageResult = PageUtil.getPageResult(pageRequest.getBody(), pageInfo);
        return JsonResult.success(pageResult);
    }

    /**
     * 登录日志列表
     * @param pageRequest
     * @return
     */
    @ApiOperation(value = "登录日志列表")
    @PostMapping("loginOperationLog/list")
    public JsonResult<Object> LoginOperationLogListPage(@RequestBody @Valid PageRequestApi<PageRequest> pageRequest) {
        List<LoginOperationLog> loginOperationLogPage = loginOperationLogService.getLoginOperationLogPage(pageRequest.getBody());
        PageInfo pageInfo = new PageInfo(loginOperationLogPage);
        PageResult pageResult = PageUtil.getPageResult(pageRequest.getBody(), pageInfo);
        return JsonResult.success(pageResult);
    }

}
