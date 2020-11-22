package com.docsys.manager.util;

import java.util.List;

import com.docsys.manager.bean.vo.PageVO;
import com.github.pagehelper.PageInfo;

/**
 * PageUtil
 *
 * @auther 120420
 * @date 2020-07-20
 */
public class PageUtil {

	public static PageVO getPageVO(List<?> list) {

		PageInfo<?> pageInfo = new PageInfo<>(list);

		PageVO pageVO = new PageVO();
		pageVO.setResult(pageInfo.getList());
		pageVO.setTotal(pageInfo.getTotal());

		return pageVO;
	}
}
