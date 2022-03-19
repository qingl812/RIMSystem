package com.example.rimsystem.aspect;

import com.example.rimsystem.annotation.Log;
import com.example.rimsystem.bean.UserLog;
import com.example.rimsystem.service.UserLogService;
import com.example.rimsystem.tool.HttpContextUtils;
import com.example.rimsystem.tool.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @auther luyu
 */
@Aspect
@Component
public class LogAspect {
    @Autowired
    UserLogService userLogService;
    @Pointcut("@annotation(com.example.rimsystem.annotation.Log)")
    public void pointCut(){};

    @Around("pointCut()")
    public Object after(ProceedingJoinPoint joinPoint){
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis()-beginTime;
        saveLog(joinPoint,time);
        return result;
    }
    private void saveLog(ProceedingJoinPoint joinPoint,long time){
        UserLog userLog = new UserLog();
        userLog.setTime((int)time/1000);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log annotation = method.getAnnotation(Log.class);
        if(annotation!=null){
            userLog.setOperation(annotation.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        userLog.setMethod(className + "." + methodName + "()");
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
           userLog.setParams(params);
        }
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ipAddr = IPUtils.getIpAddr(request);
        userLog.setIp(ipAddr);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userLog.setUsername(authentication.getName());
        userLog.setCreateTime(new Timestamp(new Date().getTime()));
        userLogService.insert(userLog);
    }
}
