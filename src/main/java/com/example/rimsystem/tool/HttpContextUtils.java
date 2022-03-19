package com.example.rimsystem.tool;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @auther luyu
 */
public class HttpContextUtils {
    /*
    为什么要使用这个工具类来获取httpservletrequest？
    因为一个请求通过dispacther分发到controller层，当我们需要在别的层，如service层获取这个request可以使用
    RequestContextHolder.getRequestAttributes()).getRequest();
    springboot对发来的请求，会存到ThreadLocal中我们可以从中获取
    但是注意，如果在service中新开一个线程是拿不到这个请求的，原因就是这个ThreadLocal
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
