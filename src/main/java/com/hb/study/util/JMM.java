package com.hb.study.util;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author huangbing
 * @date 2019/10/12 14:10
 */
public class JMM {
    private static String name = "not atomicString";


    public static void main(String[] args){
            new Thread(() -> {
                name = "success";
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"-tryGetNo.2-updateAfterVariableValue:"+name);
            }, "No.1").start();
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"-tryGetNo.1-updateBeforeVariableValue:"+name);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //将name = "not atomicString"从主存中读到当前线程工作内存中
                //给当前线程中name变量赋值
                name = "fail";
                //写回主存中,更新类中name变量值
                System.out.println(Thread.currentThread().getName()+"-update:"+name);
            }, "No.2").start();

    }

}
