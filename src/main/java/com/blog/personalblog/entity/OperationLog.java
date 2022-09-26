package com.blog.personalblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author: SuperMan
 * @create: 2022-04-02
 **/

@Data
public class OperationLog {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * ip地址
     */
    private String operationIp;

    /**
     * ip来源
     */
    private String operaLocation;

    /**
     * 操作方法名
     */
    private String methods;

    /**
     * 请求参数
     */
    private String args;

    /**
     * 操作人
     */
    private String operationName;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 返回结果
     */
    private String returnValue;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
