package com.hb.study.util;


import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.assertj.core.util.Lists;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author huangbing
 * @date 2019/10/08 11:54
 */
public class ThreadDemo implements Runnable{
     private static int count = 0;
     private static List<List<Integer>> listGroup = Lists.newArrayList();
     static {
         listGroup.add(Lists.newArrayList(1,2,3));
         listGroup.add(Lists.newArrayList(4,5,6));
         listGroup.add(Lists.newArrayList(7,8,9));
     }
     public synchronized int countAdd(List<List<Integer>> listGroup){
             if(count < listGroup.size()){

                 List<Integer> getSumList = listGroup.get(count);
                 count++;
                 System.out.println("thread-"+count+"-get lock");
                 if("threadFirst".equals(Thread.currentThread().getName())){
                     int createRuntimeException;
                     createRuntimeException = 1 / 0;
                     //synchronized 方法抛出异常会释放锁
                 }
                 System.out.println("current thread name is:"+Thread.currentThread().getName());
                     try {
                            System.out.println("waiting waiting waiting");
                            Thread.sleep(2000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 return Collections.max(getSumList).intValue();
             }
             return -1;
     }

    @Override
    public void run() {
          countAdd(listGroup);
//        System.out.println(Thread.currentThread().getName()+":"+countAdd(listGroup));
//        System.out.println(Arrays.toString(listGroup.toArray()));
    }

    public static void main(String[] args) throws InterruptedException{
        ThreadDemo threadDemo = new ThreadDemo();
        Thread threadFist = new Thread(threadDemo,"threadFirst");
        Thread threadSecond = new Thread(threadDemo, "threadSecond");
        Thread threadThird = new Thread(threadDemo, "threadThird");
//        设置线程优先级
//        threadFist.setPriority(3);
//        threadSecond.setPriority(4);
//        threadThird.setPriority(5);
        //启动线程
        System.out.println("thread is running");
        threadFist.start();
        System.out.println("thread is wait");
        threadSecond.start();
//        threadThird.start();
        Thread.sleep(6000);
        while (!threadFist.isAlive()){
            System.out.println("thread is finished");
            break;
        }

//      threadFist.join();
//      createThread();

    }

    public static void createThread(){
      ThreadDemo threadDemo = new ThreadDemo();
      listGroup.forEach((list)->{
          Thread thread = new Thread(threadDemo,"thread-"+list.toString());
          System.out.println(thread.getName());
          thread.start();
      });
  }
      @Test
      public void studyJUC() throws ExecutionException, InterruptedException {
          ExecutorService es = Executors.newCachedThreadPool();
          for (int i = 0; i < 10; i++) {
              es.execute(() ->{
                  System.out.println(this.hashCode());
              });
          }
//          BlockingQueue blockingQueue = new SynchronousQueue();
           final BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue(3);
          new Thread().notify();
          Callable<Boolean> callable = () ->{
                  return blockingQueue.add(() -> {
                      System.out.println(Thread.currentThread().getName());
                  });
          };
          FutureTask<Boolean> futureTask = new FutureTask<>(callable);
          boolean result = futureTask.get().booleanValue();
      }
}
