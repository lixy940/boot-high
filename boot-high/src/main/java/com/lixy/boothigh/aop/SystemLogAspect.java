package com.lixy.boothigh.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * @Author: MR LIS
 * @Description: 切面AOP 注解方式
 * @Date: 15:13 2018/4/18
 * @return
 */
@Aspect
@Component
public  class SystemLogAspect {


    //本地异常日志记录对象
    private final static Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    //Service层切点
    @Pointcut("@annotation(com.lixy.boothigh.aop.SystemServiceLog)")
    public  void serviceAspect() {
    }

    //Controller层切点
    @Pointcut("@annotation(com.lixy.boothigh.aop.SystemControllerLog)")
    public  void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("serviceAspect()")
    public  void doControllerBefore(JoinPoint joinPoint) {
        //获得http请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
        //User user = (User) session.getAttribute(WebConstants.CURRENT_USER);
        //请求的IP
        String ip = request.getRemoteAddr();
        try {
            String desc = getServiceMethodDescription(joinPoint);
            logger.info("getServiceMethodDescription："+desc);
        } catch (Exception e) {
            logger.error("aop处理异常：", e);
        }
    }

   /**
     * 对请求和返回结果进行日志打印
     *
     * @param pjp 切点
     */
    @Around("controllerAspect()")
    public  Object doControllerAround(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
/*        LoginUserVO user = (LoginUserVO) session.getAttribute(BConstants.CURRENT_USER_KEY);
        String custId = "";
        if (user != null) {
            custId = "custId:" + user.getCustId();
        }*/
        Object res = null;
        try {
            Class<?> aClass = pjp.getTarget().getClass();
            String className = aClass.getSimpleName();
            String methodDesc = getControllerMethodDescription(pjp);
            //请求参数
            //Object[] args = pjp.getArgs();
            //log.info(controllerLog.methodDesc() + " requestParams :" + JSONObject.toJSONString(args));
            res = pjp.proceed();
            logger.info(" ====>"+className+":"+methodDesc+" execute success aopResult:" + JSONObject.toJSONString(res));
            
        }  catch (Exception e) {
            //记录本地异常日志
            logger.error("aop doControllerAround异常信息:",e);
        }

        return res;
    }

    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public  static String getServiceMethodDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    //SystemServiceLog systemServiceLog = method.getAnnotation(SystemServiceLog.class);
                    description = method.getAnnotation(SystemServiceLog. class).methodDesc();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    SystemControllerLog controllerLog = method.getAnnotation(SystemControllerLog. class);
                    description = controllerLog.methodDesc();
                    break;
                }
            }
        }
        return description;
    }





}