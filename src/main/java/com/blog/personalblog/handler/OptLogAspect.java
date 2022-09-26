package com.blog.personalblog.handler;

import com.alibaba.fastjson.JSON;
import com.blog.personalblog.annotation.OperationLogSys;
import com.blog.personalblog.entity.OperationLog;
import com.blog.personalblog.service.OperationLogService;
import com.blog.personalblog.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 操作日志切面
 *
 * @author: SuperMan
 * @create: 2022-04-02
 **/
@Slf4j
@Aspect
@Component
public class OptLogAspect {

    @Resource
    private OperationLogService operationLogService;

    /**
     * 日志 切面 自定义注解  切到任意方法
     */
    @Pointcut("@annotation(com.blog.personalblog.annotation.OperationLogSys)")
    public void optLog() {

    }

    @Before("optLog()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("进入方法前执行...");
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    @AfterReturning(value = "optLog()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) throws Throwable {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationLogSys annotation = signature.getMethod().getAnnotation(OperationLogSys.class);
        // 获取切入点所在的方法
        Method method = signature.getMethod();

        OperationLog operationLog = new OperationLog();
        if (annotation != null) {
            //操作类型
            String operationType = annotation.operationType().getValue();
            operationLog.setOperationType(operationType);
            //IP地址
            String ipAddr = IpUtil.getIpAddr(request);
            operationLog.setOperationIp(ipAddr);
            //IP来源
            operationLog.setOperaLocation(IpUtil.getIpInfo(ipAddr));
            //操作人
            String userName = request.getRemoteUser();
            operationLog.setOperationName(userName);
            //操作方法名
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            methodName = className + "." + methodName;
            operationLog.setMethods(methodName);
            //参数
            operationLog.setArgs(JSON.toJSONString(joinPoint.getArgs()));
            //返回结果
            operationLog.setReturnValue(JSON.toJSONString(result));
            operationLogService.saveOperationLog(operationLog);
        }
    }

}
