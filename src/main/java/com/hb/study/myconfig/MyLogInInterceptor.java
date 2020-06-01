package com.hb.study.myconfig;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huangbing
 * @date 2020/03/26 12:08
 */
public class MyLogInInterceptor implements HandlerInterceptor {
//  自定义登录拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
          String userName = (String)(request.getSession().getAttribute("loginInfo"));
//        强制转型报错 -> java.lang.ClassCastException: org.springframework.validation.support.BindingAwareModelMap cannot be cast to java.lang.String
//        session.getAttribute()返回的对象要是Message类型才可以转，要不会报cast异常

//        Object userName = request.getSession().getAttribute("loginInfo");
        if(!StringUtils.isEmpty(userName)){
            return true;
        }
        request.setAttribute("message","请先登录");
        request.getRequestDispatcher("/signIn").forward(request, response);
        return false;
    }
}



