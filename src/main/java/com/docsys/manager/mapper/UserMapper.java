package com.docsys.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.docsys.manager.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("UserMapper")
public interface UserMapper extends BaseMapper<User> {
}
