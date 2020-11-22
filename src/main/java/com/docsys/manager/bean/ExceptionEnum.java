package com.docsys.manager.bean;

import lombok.Getter;

/**
 * 异常枚举类
 *
 * @author  
 * @date  
 */
@Getter
public enum ExceptionEnum {

    ERROR_TOKEN("10001", "token is invalid"),

    ERROR_PARAM("40001", "parameter is invalid"),
    ERROR_QUERY_DB("40002", "query DB error"),
    ERROR_PRIMARY_KEY_NULL("40003", "primary key can't be null"),
    SERVICE_UNABLE("40004", "service not available"),
    DUPLICATE_EXIST("40005", "duplicate is exist"),
    PARAM_INCOMPLETE("4006", "Parameter incomplete"),
    DATA_EXIST("40008", "data is not exist"),

    ERROR_INSERT("41001", "insert is fail"),
    ERROR_DELETE("41002", "delete is fail"),
    ERROR_UPDATE("41003", "update is fail"),
    ERROR_EXPORT("41004", "export is fail"),

    ERROR_ORGCODE("41005", "组织编码不存在"),

    DUPLICATE_NAME("42003", "名称重复"),

    ERROR_DEV_BIND_FAILD("43001", "设备绑定失败"),
    DUPLICATE_TIME_TEMPLATE_NAME("43002", "时间模板名称相同"),
    TIME_TEMPLATE_RELATION_STORAGE_PLAN("43003", "时间模板已绑定录像计划，无法删除"),

    QUERY_FAILED("44012", "查询出错"),

    LOCK_RECORD_FAILD("50001","开启事件录像失败"),

    UNLOCK_RECORD_FAILD("50002","录像解锁失败"),

    RECORD_HAS_UNLOCK("50003","录像文件非锁定状态"),

    RECORD_PARAM_TIME("50004","录像文件解锁时间不能早于当前时间前一天"),


    EXCEPTION_50005("50005","楼层绑定车位，不允许删除")
    ;

    private String exceptionCode;
    private String exceptionMessage;

    ExceptionEnum(String exceptionCode, String exceptionMessage) {
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
    }
}
