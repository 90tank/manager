CREATE TABLE `t_sys_user` (
`id` int(10) NULL AUTO_INCREMENT COMMENT 'ID',
`user_id` int(11) NOT NULL COMMENT '用户Id主键',
`user_name` varchar(255) NOT NULL COMMENT '系统管理员用户名(手机号码)',
`password` varchar(25) NOT NULL COMMENT '系统管理员密码',
`create_time` timestamp NOT NULL COMMENT '创建时间',
`update_time` timestamp NULL COMMENT '更新时间',
`delete_status` tinyint(10) NULL DEFAULT 1 COMMENT '是否有效',
PRIMARY KEY (`user_id`) 
);

CREATE TABLE `t_sys_user_group_map` (
`id` int(10) NULL AUTO_INCREMENT COMMENT 'ID',
`user_id` int(11) NOT NULL COMMENT '用户id',
`group_id` int(10) NOT NULL COMMENT '权限组Id'
);

CREATE TABLE `t_role_group_map` (
`id` int(10) NULL AUTO_INCREMENT COMMENT 'ID',
`role_id` int(10) NOT NULL COMMENT '角色Id',
`group_id` int(10) NOT NULL COMMENT '权限组Id'
);

CREATE TABLE `t_sys_role_permission_map` (
`id` int NULL AUTO_INCREMENT COMMENT 'ID',
`role_id` int NOT NULL COMMENT '角色Id',
`permission_id` int NOT NULL COMMENT '权限Id'
);

CREATE TABLE `t_sys_role` (
`id` int(10) NULL AUTO_INCREMENT COMMENT 'ID',
`role_id` int UNSIGNED NULL AUTO_INCREMENT COMMENT '角色主键',
`role_name` varchar NULL COMMENT '角色名称',
`create_time` timestamp NULL,
`update_time` timestamp NULL,
`delete_status` tinyint UNSIGNED NULL
);

CREATE TABLE `t_sys_permission` (
`id` int NULL AUTO_INCREMENT COMMENT 'ID',
`permission_id` int NULL COMMENT '权限编号',
`menu_code` varchar NULL COMMENT '菜单代号',
`permission_code` varchar NULL COMMENT '权限代号',
`menu_name` varchar NULL COMMENT '菜单名称',
`permission_name` varchar NULL,
`required_permission` int UNSIGNED NULL COMMENT '权限大类（预留）'
);

CREATE TABLE `t_sys_group` (
`id` int NULL AUTO_INCREMENT COMMENT 'ID',
`group_id` int NOT NULL,
`group_name` varchar NULL COMMENT '权限组名'
);

