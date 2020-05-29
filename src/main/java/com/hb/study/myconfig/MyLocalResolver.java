package com.hb.study.myconfig;

import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author huangbing
 * @date 2020/03/25 17:08
 */
//在请求上带上地区信息
public class MyLocalResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
           String language = request.getParameter("language");
           Locale local = Locale.getDefault();
           if(!StringUtils.isEmpty(language)){
                  String[] languages = language.split("_");
                  local = new Locale(languages[0], languages[1]);
           }
           return local;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
