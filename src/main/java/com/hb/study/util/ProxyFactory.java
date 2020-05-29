package com.hb.study.util;

import org.testng.annotations.Test;

import java.lang.reflect.Proxy;
import java.util.concurrent.*;

/**
 * @author huangbing
 * @date 2019/10/16 15:19
 */
public class ProxyFactory {
       //jdk 动态代理被代理对象(目标对象)必须实现接口
       public static <T> T getProxy(Class<T> target){
           CustomHandler customHandler = null;
           try {
               customHandler = new CustomHandler(target.newInstance());
           } catch (InstantiationException e) {
               e.printStackTrace();
           } catch (IllegalAccessException e) {
               e.printStackTrace();
           }
           T proxyObject = (T)Proxy.newProxyInstance(target.getClassLoader(), target.getInterfaces(),customHandler);
           return proxyObject;
       }

       @Test
      public void testCreateProxy() throws Exception {
//             ImgService imgService = ProxyFactory.getProxy(ImgServiceImpl.class);
//             imgService.checkCreateProxy();
           Callable<String> callable = () ->{
                    return "success";
           };
           ExecutorService executorService = Executors.newFixedThreadPool(2);
           Future<String> future = executorService.submit(callable);
           System.out.println(future.get());
           executorService.shutdownNow();
//           Object

       }

//       private static class CustomThreadFactory implements ThreadFactory{
//           @Override
//           public Thread newThread(Runnable r) {
//                  return null;
//           }
//       }


}
