package com.docsys.manager.common;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局返回值处理
 *
 * @author  zj
 * @date  2020.11.21
 */
@RestControllerAdvice(basePackages = "com.docsys.manager.controller")
public class GlobalResultWrapper implements ResponseBodyAdvice {

    private static final Class[] annos = {RequestMapping.class, GetMapping.class,
            PostMapping.class, DeleteMapping.class, PutMapping.class};


    private static final String[] EXCLUDE_URIS = {
    		"/file-to-stream"
    };

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        AnnotatedElement element = returnType.getAnnotatedElement();
        return Arrays.stream(annos).anyMatch(anno -> anno.isAnnotation() && element.isAnnotationPresent(anno));
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        String uri = request.getURI().getPath();
        //有些地址不拦截
        if(checkUriExclude(uri)){
            return body;
        }       
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return  Result.succ(body);
 
    }

    private boolean checkUriExclude(String uri){

        for(String item : EXCLUDE_URIS){
            if(uri.contains(item)){
                return true;
            }
        }

        return false;
    }
}
