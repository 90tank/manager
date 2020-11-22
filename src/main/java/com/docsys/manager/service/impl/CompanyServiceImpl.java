package com.docsys.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docsys.manager.dao.CompanyMapper;
import com.docsys.manager.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private CompanyMapper 	companyMapper;

	@Override
	public List<Map<String, Object>> getCompanyInfos(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return companyMapper.getCompanyInfos(param);
	}

}
