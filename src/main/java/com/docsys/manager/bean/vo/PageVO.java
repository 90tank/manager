package com.docsys.manager.bean.vo;

import lombok.Data;

/**
 * 分页VO
 *
 * @Author 46102
 * @Version 1.0
 * @Date 2019/9/21
 */

@Data
public class PageVO {

    private Long total;

    private Object result = null;
}