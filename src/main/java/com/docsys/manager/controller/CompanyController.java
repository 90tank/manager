package com.docsys.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docsys.manager.bean.annotation.Log;
import com.docsys.manager.bean.vo.PageVO;
import com.docsys.manager.service.CompanyService;
import com.docsys.manager.util.PageUtil;
import com.github.pagehelper.PageHelper;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/company")
public class CompanyController {
	/**
	 * 通过条件查询企业信息
	 */
	@Autowired
	private CompanyService companyService;

	@PostMapping("/getCompanyInfos")
	@ApiOperation(value = "获取企业信息",notes = "获取企业信息")
	@Log
	public Object getCompanyInfos(@RequestBody Map<String, Object> param) {

		PageHelper.startPage((int) (param.getOrDefault("page", 1)), (int) (param.getOrDefault("pageSize", 10)));
		List<Map<String, Object>> lst = companyService.getCompanyInfos(param);
		PageVO pageVO = PageUtil.getPageVO(lst);
		return pageVO;
	}

}
