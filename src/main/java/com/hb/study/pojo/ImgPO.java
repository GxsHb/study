package com.hb.study.pojo;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huangbing
 * @date 2019/10/06 11:51
 */
@Data
@Validated //数据校验
//@Component
@ConfigurationProperties(prefix = "hb.cs")
//指定pojo加载哪个配置文件
@PropertySource(value = {"classpath:config/application-pre.yml"})
public class ImgPO {
     private String title;

     private String url;

     private String[] testResult = {"success", "fail"};

     private String[] tester = {"hb", "zzy", "wl", "xl"};

//    private Integer width;
//
//    private Integer height;
//
//    private Integer ratio;
//
//    private String type;
     @Test
     public void reason(){
            boolean flag = true;
            String  workTime = "2020-05-22";
            Logger logger = LoggerFactory.getLogger(this.getClass());
            AtomicInteger atomicInteger = new AtomicInteger();
            new Thread(
                    () -> {
                        logger.info("听说程序员都去卖房子了,实践是检验真理的唯一标准");
                        while (flag) {
                            LocalDate localDate = LocalDate.now();
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            String getWorkTime = dtf.format(localDate);
                            boolean result = workTime.equals(getWorkTime);
                            if (result) {
                                logger.info("我卖房子回来了");
                                Thread.currentThread().interrupt();
                                logger.info(atomicInteger.incrementAndGet()+"");
                                logger.info(String.valueOf(Thread.interrupted()));
                                logger.info(String.valueOf(Thread.interrupted()));
                            }
                        }
                    },"saleHouse"
            ).start();
            while (flag) {
                Thread.getAllStackTraces().keySet().forEach((thread) -> {
                    if ("saleHouse".equals(thread.getName())) {
                        if (thread.isInterrupted()) {
                            logger.info(atomicInteger.incrementAndGet()+"");
                            logger.info("saleHouseThread isInterrupt");
                            System.exit(0);
                        }
                    }
                });
            }
     }

    public static void main(String[] args) {
        Thread.getAllStackTraces().keySet().forEach((thread) -> {
            System.out.println(thread.getName());
        });
    }
}
