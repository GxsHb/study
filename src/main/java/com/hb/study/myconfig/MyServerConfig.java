package com.hb.study.myconfig;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author huangbing
 * @date 2020/03/31 10:03
 */
@Configuration
public class MyServerConfig {
      private static Logger logger = LoggerFactory.getLogger(MyServerConfig.class);
//    由于Springboot默认是以jar包的方式启动嵌入式的Servlet容器来启动springboot的web应用,没有web.xml文件
//    注册三大组件可以通过在容器中添加对应的Bean
//    1、ServletRegistrationBean 注册Servlet
//    2、ServletListenerRegistrationBean 注册监听器
//    3、FilterRegistrationBean 注册过滤器

    @Bean
    @ConditionalOnMissingClass("ServletRegistrationBean.class")
    public ServletRegistrationBean<MyServlet> registrationMyServlet(){
             ServletRegistrationBean<MyServlet> registrationBean = new ServletRegistrationBean<>();
             registrationBean.setLoadOnStartup(1);
             registrationBean.setServlet(new MyServlet());
             registrationBean.setUrlMappings(Lists.newArrayList("/myServlet"));
             return registrationBean;
      }
    @Test(description = "check myServlet can  correct connection")
    public void checkRegisteredServlet() throws IOException {
        //模拟客户端像服务端发送请求,后期可以用OkHttp
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
//      HttpGet httpGet = new HttpGet("http://localhost:24/hbpre/myServlet");
        HttpGet httpGet = new HttpGet("http://localhost:24/hbpre/myServlet");
        CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpGet);
        logger.info(httpResponse.getStatusLine().getStatusCode()+" ");
        logger.info(EntityUtils.toString(httpResponse.getEntity()));
    }



//  web服务工厂定制器
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
//          ConfigurableWebServerFactor可配置的web服务工厂,配置所有web服务工厂
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
//            可配置哪些web服务工厂,可以查看有哪些服务工厂接口继承了ConfigurableWebServerFactory接口,有如下三个
//                    1、ConfigurableTomcatWebServerFactory
//                    2、ConfigurableJettyWebServerFactory
//                    3、ConfigurableReactiveWebServerFactory
//                    4、ConfigurableUndertowWebServerFactory
//                    5、ConfigurableServletWebServerFactory
//              指定使用Tomcat Servlet Web服务器工厂
                TomcatServletWebServerFactory tomcatServletWebServerFactory = (TomcatServletWebServerFactory) factory;
                //设置当前web应用上下文路径
                tomcatServletWebServerFactory.setContextPath("/hbpre");
                //springboot默认使用tomcat是因为spring-boot-starter-web依赖引用了spring-boot-starter-tomcat
            }
        };
    }
}
