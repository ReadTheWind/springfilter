package com.spring.filter.config;

import com.alibaba.fastjson.JSONObject;
import com.spring.filter.annotation.ModuleParams;
import com.spring.filter.annotation.ParamName;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

@Component
public class MyInterceptor implements HandlerInterceptor {

    //在请求处理之前进行调用（Controller方法调用之前
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        System.out.println("preHandle被调用");
        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
        String body = requestWrapper.getBody();
        System.out.print("post数据："+body);
        Map requestObj=(Map)JSONObject.parse(body);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Class[] clazz=method.getParameterTypes();
        for (Class c:clazz){
            Field[] fields=c.getDeclaredFields();
            for (Field f:fields) {
                f.setAccessible(true);
                ParamName paramName=f.getAnnotation(ParamName.class);
                if(paramName != null){
                    String filedName=f.getName();
                    Object obj= requestObj.get(filedName);
                    System.out.println("获取参数注解值："+paramName.name()+",参数名称："+filedName+"，参数值："+obj.toString());
                }
            }
        }

        //获取对象注解的值
//        Annotation annotation=clazz.getClass().getAnnotation(ParamName.class);
//        System.out.println("params"+((ParamName) annotation).name());
        //获取参数的注解集合
        Annotation[][] annotations = method.getParameterAnnotations();
        for (Annotation[] anos : annotations) {
            System.out.println("length:" + anos.length);
            for (Annotation ano : anos) {
                boolean b = ano.annotationType().isInstance(ParamName.class);
                if (b) {
                    System.out.println("param");
                }
            }
        }
        ModuleParams moduleParams = method.getAnnotation(ModuleParams.class);
        if (moduleParams != null && !StringUtils.isEmpty(moduleParams.moduleNames())) {
            String[] moduleNames = moduleParams.moduleNames();
            System.out.println("获取自定义参数注解值" + moduleNames.toString());
            return true;
        }


//        Map map = (Map) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//        System.out.println(map.get("name"));
//        System.out.println(httpServletRequest.getParameter("username"));
//        if (map.get("name").equals("zhangsan")) {
//            return true;    //如果false，停止流程，api被拦截
//        } else {
//            PrintWriter printWriter = httpServletResponse.getWriter();
//            printWriter.write("please login again!");
//            return false;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle被调用");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion被调用");
    }
}
