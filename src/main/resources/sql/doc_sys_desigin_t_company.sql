CREATE TABLE `t_company_wx_admin` (
`admin_id` int(11) NOT NULL AUTO_INCREMENT,
`nick_name` varchar(255) NULL,
`open_id` varchar(255) NULL,
`session_key` varchar(255) NULL,
`img_url` varchar(255) NULL,
`sex` varchar(255) NULL,
PRIMARY KEY (`admin_id`) 
);

CREATE TABLE `t_company_admin_tags_map` (
`admin_id` int(10) NULL COMMENT '企业管理员Id',
`tag_id` int(10) NULL COMMENT '标签Id'
);

CREATE TABLE `t_company_admin_map` (
`company_id` int(10) NULL COMMENT '企业Id',
`admin_id` int(10) NULL COMMENT '管理员Id'
);

CREATE TABLE `t_company_tags` (
`tag_id` int(10) NULL AUTO_INCREMENT COMMENT '标签Id',
`tag_name` varchar(255) NULL COMMENT '标签名称'
);

