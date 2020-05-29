package com.hb.study.myconfig;

import com.hb.study.pojo.ImgPO;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author huangbing
 * @date 2020/02/21 14:55
 */
@Configuration
/**
 * @Configuration注解指明当前这个类是一个配置类,用来替代之前spring中的配置文件
 * spring配置文件中通过<bean id="customBean"></bean>来添加组件
 * 现在通过在配置类中使用@Bean注解来添加组件
 */
//@EnableWebMvc 取消springboot对springmvc的自动配置
@EnableConfigurationProperties(ImgPO.class)
public class MyCustomConfig implements WebMvcConfigurer {
//  配置首页
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //添加视图解析器、需要模板引擎解析 ？
        registry.addViewController("/signIn").setViewName("login");
        registry.addViewController("/manage").setViewName("dashboard");
    }

////  添加资源映射 会覆盖springmvc自动配置资源的映射
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //静态文件
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        //webjar文件
//        registry.addResourceHandler("/webjars/**").addResourceLocations("/META/resources/webjars/");
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/public/");
//
//    }

      @Bean
//    将方法返回值添加到容器中,容器中组件的默认id为方法名
      public MyLocalResolver myLocalResolver(){
             return new MyLocalResolver();
      }

     @Override
     public void addInterceptors(InterceptorRegistry registry) {
           //springboot对静态资源做了映射,所以不需要拦截
//           registry.addInterceptor(new MyLogInInterceptor()).addPathPatterns("/**").excludePathPatterns("/st/cs/**", "/signIn", "/asserts/**","/webjars/**");
    }
}
