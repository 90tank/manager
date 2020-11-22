package com.docsys.manager.service;

import java.util.List;
import java.util.Map;

public interface CompanyService {
	
	List<Map<String,Object>> getCompanyInfos(Map<String,Object> param);

}
