package com.hb.study.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.assertj.core.util.Lists;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author huangbing
 * @date 2019/10/17 17:13
 */
public class Task {
   private static Logger logger = LoggerFactory.getLogger(Task.class);
//   private volatile ArrayList<Integer> successCount = new ArrayList();
   private Lock reentrantLock = new ReentrantLock();
    @Test
    public void check(){
//     ThreadLocal
        HttpResponse httpResponse;
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://nba.com");

        try {
            httpResponse = httpClient.execute(httpGet);
            System.out.println(EntityUtils.toString(httpResponse.getEntity()));
//            System.out.println(EntityUtils.toString(httpResponse.getEntity(), "utf-8"));
//            Document document = Jsoup.parse(EntityUtils.toString(httpResponse.getEntity(), "utf-8"));
//            System.out.println(document.title());
//            Elements images = document.select("img[src~=.*\\.(png|jpe?g|gif)]");
//            for (Element image : images)
//            {
//                System.out.println("src : " + image.attr("src"));
//                System.out.println("height : " + image.attr("height"));
//                System.out.println("width : " + image.attr("width"));
//                System.out.println("alt : " + image.attr("alt"));
//            }

            Connection connection = Jsoup.connect("http://nba.com");
            Document document1 = connection.get();
            System.out.println(document1.title());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  testQueryDevicesByName() {
        HttpClient httpClient = HttpClients.createDefault();
        List<Integer> successCount = Lists.newArrayList();
//       List<Integer> synSuccessCount = Collections.synchronizedList(successCount);
//       List<Integer> failCount = Lists.newArrayList();
//       CopyOnWriteArrayList<Integer> successCount = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<Integer> failCount = new CopyOnWriteArrayList<>();
        HttpGet httpGet = new HttpGet("http://localhost:8088/MyProject/user/queryByName.action?devicesName=MacPro");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(3000).build();
        httpGet.setConfig(requestConfig);
        ExecutorService executorService = Executors.newFixedThreadPool(3000);
        //
        Runnable runnable = () -> {
//          synchronized (this){
            reentrantLock.lock();
            HttpResponse httpResponse;
            try {
                httpResponse = httpClient.execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                       synSuccessCount.add(httpResponse.getStatusLine().getStatusCode());
                    successCount.add(httpResponse.getStatusLine().getStatusCode());
                    logger.info(EntityUtils.toString(httpResponse.getEntity()));
                    EntityUtils.consume(httpResponse.getEntity());
                } else {
                    EntityUtils.consume(httpResponse.getEntity());
                }
            } catch (IOException e) {
                failCount.add(0);
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
//                }
        };
        for (int i = 0; i < 3000; i++) {
            executorService.execute(runnable);
        }
            executorService.shutdownNow();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("add success size is " + successCount.size() + "");
            logger.debug("add fail size is " + failCount.size() + "");

    }
    public static void main(String[] args){
           Task task = new Task();
           task.testQueryDevicesByName();
//             testThreadState();
    }
    static Thread thread;
    static Thread threadC;
    static String lock = "";
    static boolean flag = true;
    public static void testThreadState(){
               thread = new Thread(()->{
                   synchronized (lock){
                   System.out.println("start dead looper");
                   while (flag){

                  }
                   System.out.println("end dead looper");
                   }
               },"timeWait");
               thread.start();

             System.out.println("异步执行:"+Thread.currentThread().getName());

//          synchronized (lock){
            threadC = new Thread(()->{
                synchronized (lock){
                System.out.println("update flag");
               flag = false;
                }
            },"block");
            threadC.start();
//        }
//
        System.out.println(threadC.getState().toString());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadC.getState().toString());
        }
}
