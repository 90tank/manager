package com.docsys.manager.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserPermissionMapper {
    List<String>  getPermissionsByUserId(int id);
    List<String>  getMenusByUserId(int id);
}
