package com.spring.filter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/zxc/foo").setViewName("foo");
//    }


    @Autowired
    MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**");
    }


//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @Bean
//    public FilterRegistrationBean filterRegist() {
//        FilterRegistrationBean frBean = new FilterRegistrationBean();
//        frBean.setFilter(myFilter);
//        frBean.addUrlPatterns("/*");
//        System.out.println("filter");
//        return frBean;
//    }

//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @Bean
//    public ServletListenerRegistrationBean listenerRegist() {
//        ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
//        srb.setListener(new MyHttpSessionListener());
//        System.out.println("listener");
//        return srb;
//    }
}
