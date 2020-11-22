package com.docsys.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper {
	
	
	List<Map<String,Object>> getCompanyInfos(Map<String,Object> param);
	
	List<Map<String,Object>> getdic(Map<String,Object> param);
	
	
	
	void modify (Map<String,Object> param);

}
