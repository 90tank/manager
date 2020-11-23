package com.docsys.manager.dao;

import com.docsys.manager.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserPermissionMapper {
    List<Permission>  getPermissionsByUserId(@Param("userId") int userId);
}
