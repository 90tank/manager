package com.docsys.manager.shiro;


import com.auth0.jwt.exceptions.TokenExpiredException;

import com.docsys.manager.entity.User;
import com.docsys.manager.exception.CustomTokenExpireException;
import com.docsys.manager.jwt.JWTUtil;
import com.docsys.manager.redis.RedisUtil;
import com.docsys.manager.service.UserService;
import com.docsys.manager.token.JWTToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    //根据token判断此Authenticator是否使用该realm
    //必须重写不然shiro会报错
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如@RequiresRoles,@RequiresPermissions之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权~~~~~");
        String token=principals.toString();
        String username= JWTUtil.getUsername(token);

        // 这里去进行这个用户的权限查询
        User user=userService.getUser(username);
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //查询数据库来获取用户的角色
        //info.addRole(user.getRoles());
        //查询数据库来获取用户的权限
        //info.addStringPermission(user.getPermission());
        return info;
    }


    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可，在需要用户认证和鉴权的时候才会调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证~~~~~~~");
        String jwt= (String) token.getCredentials();
        String username= null;
        try {
            username= JWTUtil.getUsername(jwt);
        }catch (Exception e){
            throw new AuthenticationException("token非法，不是规范的token，可能被篡改了，或者过期了");
        }
        if (username==null){
            throw new AuthenticationException("token中无用户名");
        }
        User user=userService.getUser(username);
        if (user==null){
            throw new AuthenticationException("该用户不存在");
        }
        //开始认证，只要AccessToken没有过期，或者refreshToken的时间节点和AccessToken一致即可
        if (redisUtil.hasKey(username)){
            //判断AccessToken有无过期
            if (!JWTUtil.verify(jwt)){
                System.out.println("TokenExpiredException !");
                // 这里抛出的 TokenExpiredException 异常会打印 ，但是其他地方throw的不打印 ，why ?
                throw new TokenExpiredException("token认证失效，token过期，重新登陆");
//                throw new CustomTokenExpireException("token认证失效，token过期，重新登陆");
            } else {
                //判断AccessToken和refreshToken的时间节点是否一致
                long current= (long) redisUtil.get(username);
                if (current== JWTUtil.getExpire(jwt)){
                    return new SimpleAuthenticationInfo(jwt,jwt,"MyRealm");
                }else{
                    throw new AuthenticationException("token已经失效，请重新登录！");
                }
            }
        } else {
            throw new AuthenticationException("token过期或者Token错误！！");
        }
    }
}


