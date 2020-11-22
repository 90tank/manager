package com.docsys.manager.jwt;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.docsys.manager.common.Result;
import com.docsys.manager.exception.CustomException;
import com.docsys.manager.redis.RedisUtil;
import com.docsys.manager.token.JWTToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;


@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {


    //是否允许访问，如果带有 token，则对 token 进行检查，否则直接通过
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //判断请求的请求头是否带上 "Token"
        System.out.println("isAccessAllowed");
        if (isLoginAttempt(request, response)) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            try {
                executeLogin(request, response);
                return true;

            } catch (Exception e) {
                /*
                 *注意这里捕获的异常其实是在Realm抛出的，但是由于executeLogin（）方法抛出的异常是从login（）来的，
                 * login抛出的异常类型是AuthenticationException，所以要去获取它的子类异常才能获取到我们在Realm抛出的异常类型。
                 * */
                String msg=e.getMessage();
                Throwable cause = e.getCause();
                if (cause!=null&&cause instanceof TokenExpiredException) {
                    System.out.println("刷新token");
                    //AccessToken过期，尝试去刷新token
                    String result=refreshToken(request, response);
                    if (result.equals("success")){
                        System.out.println("request.equals(\"success\")");
                        return true;
                    }
                    msg=result;
                } else {
                    // 这里是认证失败的情况 错误的Token 或者 redis 中已经不存在对应的key
                    msg = "错误的Token, 或缓存中已无此key ";
                    this.response401(response,msg);
//                    responseError(response,msg);
                }

            }
        } else {
            // 没有携带Token
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            // 获取当前请求类型
            String httpMethod = httpServletRequest.getMethod();
            // 获取当前请求URI
            String requestURI = httpServletRequest.getRequestURI();
            log.info("当前请求 {} Authorization属性(Token)为空 请求类型 {}", requestURI, httpMethod);
            // mustLoginFlag = true 开启任何请求必须登录才可访问
            final Boolean mustLoginFlag = true;
            if (mustLoginFlag) {
                this.response401(response, "请先登录");
                return false;
            }
        }

        return true;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req= (HttpServletRequest) request;
        String token=req.getHeader("Authorization");
        return token !=null;
    }


    /**
     * 这里我们详细说明下为什么重写
     * 可以对比父类方法，只是将executeLogin方法调用去除了
     * 如果没有去除将会循环调用doGetAuthenticationInfo方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        this.sendChallenge(request, response);
        return false;
    }

    /*
     * executeLogin实际上就是先调用createToken来获取token，这里我们重写了这个方法，就不会自动去调用createToken来获取token
     * 然后调用getSubject方法来获取当前用户再调用login方法来实现登录
     * 这也解释了我们为什么要自定义jwtToken，因为我们不再使用Shiro默认的UsernamePasswordToken了。
     * */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("executeLogin");
        HttpServletRequest req= (HttpServletRequest) request;
        String token=req.getHeader("Authorization");
        JWTToken jwt=new JWTToken(token);
        //交给自定义的realm对象去登录，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwt);

        return true;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("preHandle");
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse res= (HttpServletResponse) response;
        res.setHeader("Access-control-Allow-Origin",req.getHeader("Origin"));
        res.setHeader("Access-control-Allow-Methods","GET,POST,OPTIONS,PUT,DELETE");
        res.setHeader("Access-control-Allow-Headers",req.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            res.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /unauthorized/**
     */
    private void responseError(ServletResponse response, String message) {
        System.out.println("将非法请求跳转到 未认证页面"+message);

        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            //设置编码，否则中文字符在重定向时会变为空字符串
            message = URLEncoder.encode(message, "UTF-8");
            httpServletResponse.sendRedirect("/unauthorized/" + message);
        } catch (IOException e) {
            log.error("redirect to unauthorized");
        }
    }


    /*
     * 这里的getBean是因为使用@Autowired无法把RedisUtil注入进来
     * 这样自动去注入当使用的时候是未NULL，是注入不进去了。通俗的来讲是因为拦截器在spring扫描bean之前加载所以注入不进去。
     *
     * 解决的方法：
     * 可以通过已经初始化之后applicationContext容器中去获取需要的bean.
     * */
    public <T> T getBean(Class<T> clazz,HttpServletRequest request){
        WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return applicationContext.getBean(clazz);
    }

    //刷新token
    private String refreshToken(ServletRequest request,ServletResponse response) {
        System.out.println("refreshToken");

        HttpServletRequest req= (HttpServletRequest) request;
        RedisUtil redisUtil=getBean(RedisUtil.class,req);
        //获取传递过来的accessToken
        String accessToken=req.getHeader("Authorization");
        //获取token里面的用户名
        String username= JWTUtil.getUsername(accessToken);
        System.out.println("username"+username);
        //判断refreshToken是否过期了，过期了那么所含的username的键不存在
        System.out.println("redisUtil.hasKey(username)"+redisUtil.hasKey(username));
        if (redisUtil.hasKey(username)){
            //判断refresh的时间节点和传递过来的accessToken的时间节点是否一致，不一致校验失败
            long current= (long) redisUtil.get(username);
            if (current==JWTUtil.getExpire(accessToken)){
                //获取当前时间节点
                long currentTimeMillis = System.currentTimeMillis();
                //生成刷新的token
                String token=JWTUtil.createToken(username,currentTimeMillis);
                //重点 ： 刷新redis里面的refreshToken,过期时间是30min
                redisUtil.set(username,currentTimeMillis,30*60);
                //再次交给shiro进行认证
                JWTToken jwtToken=new JWTToken(token);
                try {
                    getSubject(request, response).login(jwtToken);
                    // 最后将刷新的AccessToken存放在Response的Header中的Authorization字段返回
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.setHeader("Authorization", token);
                    httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                    return "success";
                }catch (Exception e){
                    return e.getMessage();
                }
            }
        }
        return "token认证失效，token过期，重新登陆";
    }

    /**
     * 无需转发，直接返回Response信息
     */
    private void response401(ServletResponse response, String msg) {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = httpServletResponse.getWriter()) {

            Result result = Result.fail(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + msg, null);
            String data = JSONObject.toJSONString(result);
            out.append(data);
        } catch (IOException e) {
            log.error("直接返回Response信息出现IOException异常:{}", e.getMessage());
            throw new CustomException("直接返回Response信息出现IOException异常:" + e.getMessage());
        }
    }
}


