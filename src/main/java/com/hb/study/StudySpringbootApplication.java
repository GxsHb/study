package com.hb.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
@MapperScan("com.hb.study.mapper")
/**
 * 快速体验缓存:
 * 1、@EnableCaching //开启基于注解的缓存
 * 2、标注缓存注解
 */
@EnableCaching //开启基于注解的缓存
@EnableRabbit //开启基于注解的rabbitmq模式
public class StudySpringbootApplication {

    public static void main(String[] args) {
        //启动springboot程序,启动spring容器,启动内嵌的tomcat
        SpringApplication.run(StudySpringbootApplication.class, args);
    }

    public void resolveProperties() throws IOException {
           InputStream inputStream = ClassLoader.getSystemResourceAsStream("");

           PropertiesLoaderUtils.loadProperties(new InputStreamResource(inputStream));
//           PropertiesUtil.getSystemProperties();
//           Properties properties = System.getProperties();
    }
}
