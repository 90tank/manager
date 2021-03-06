package com.docsys.manager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

// Ps ： 建数据库的时候最好遵循mybatis plus 的规范，
// 则不用关注 @Table @TableField @TableId
@Data
@TableName(value = "t_sys_user")
public class User {
    @TableId(value="user_id", type = IdType.AUTO)
    Integer id;
    @TableField("user_name_pn") // 登录用户名 （即手机号）
    String userName;
    @TableField("password")
    String password;
    @TableField("real_name")
    String realName;
    @TableField("delete_status")
    String deleteStatus;
}
