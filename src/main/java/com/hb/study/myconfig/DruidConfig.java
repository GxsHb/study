package com.hb.study.myconfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.assertj.core.util.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testng.collections.Maps;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author huangbing
 * @date 2020/04/01 15:00
 */
@Configuration
public class DruidConfig {
    @Bean
    @ConfigurationProperties("spring.datasource")
    //配置数据源
    public DataSource druid(){
        return new DruidDataSource();
    }
//   配置Druid监控,配置一个管理后台的Servlet,配置一个监控的filter
    @Bean
    public ServletRegistrationBean<StatViewServlet> registrationBean(){
           ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>();
           registrationBean.setServlet(new StatViewServlet());
           registrationBean.setUrlMappings(Lists.newArrayList("/druid/*"));
           //设置初始化参数
           Map<String, String> initParams = Maps.newHashMap();
           initParams.put("loginUsername", "BlackTiger-Ab");
           initParams.put("loginPassword", "huangbing");
           //如果value不写或者为null,默认允许所有访问者,白名单
           initParams.put("allow", "");
           //拒绝访问、黑名单
//          initParams.put("deny", "localhost");
           registrationBean.setInitParameters(initParams);
           return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean(){
           FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>();
           filterRegistrationBean.setFilter(new WebStatFilter());
            //设置初始化参数
            Map<String, String> initParams = Maps.newHashMap();
            //排除拦截的请求
            initParams.put("exclusions","*.js, *.css, /druid/*");

            //拦截所有请求
            filterRegistrationBean.setUrlPatterns(Lists.newArrayList("/*"));
            filterRegistrationBean.setInitParameters(initParams);
            return filterRegistrationBean;
    }






}
