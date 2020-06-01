package com.hb.study.util;

/**
 * @author huangbing
 * @date 2019/10/30 14:48
 */
public class TestStaticMethodSynchronized {
       static int count = 0;
       public static void add(){
           //这里while循环条件不能使用共享数据，会导致当一个线程获取锁后，执行完后add方法后，count值为51，导致它线程无法执行
               count++;
       }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
               TestStaticMethodSynchronized.add();
                System.out.println(Thread.currentThread().getName()+"---->"+count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }


}
