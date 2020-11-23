# 接口分析： 

## 系统管理员
1. /login/get/token  -- 获取token 令牌 ，入参用户名 密码
2. /login/delete/token -- 删除token, 登出
3. /login/vcode/send/ -- 获取验证码  ，入参用户名（手机号）
4. /login/vcode/check/ -- 检查验证码 ，入参是 用户名 和 手机号 （把生成的验证码放到redis中,并设置失效时间2min）
5. /user/update/password -- 更新用户密码
6. /user/update/changebind -- 换绑手机号码 （根据PK，找对应关系）
7. /user/add -- 添加用户
8. /user/delete -- 删除用户 (可批量 可单个)
9. /user/list -- 查询所有用户 （分页查询）
10. /user/get/permissions -- 获取用户按钮权限       （根据用户PK进行关联查询） （需预先添加权限信息到数据库 t_sys_permission，**要去重！**）
11. /user/get/menus -- 获取用户可见菜单 (左侧目录栏)  （根据用户PK进行关联查询） （需预先添加菜单信息到数据库 t_sys_permission，**要去重！**）


## 企业管理员
1. /company/login  -- 企业用户登录
2. /company/logout -- 企业用户登出
3. /company/user/authorization/wechat   -- 授权微信，获取微信头像  （中间可能涉及多次请求 ，这里只写一个作为标记， 待分析）
4. /company/user/list  -- 查看所有企业管理员信息
5. /company/user/delete (可批量 可单个)
6. /company/user/update  -- 企业管理员信息，更新 
7. /company/user/tags/update  -- 企业管理员标签更新（支持批量）

 
##### 遗留问题 ：
1. 小程序管理页面 （应该主要是调用企业微信的某接口 ）
2. 企业管理员 ，table 未设计， 主要涉及 ： 用户微信昵称， 用户微信头像， 用户真实姓名， 用户与标签的关系映射
   应提供最基本的关于企业管理员的 增删改查接口， 其中增 （微信授权，保存用户微信信息这部分属于难点 ，需研究）
   
    
       
 
