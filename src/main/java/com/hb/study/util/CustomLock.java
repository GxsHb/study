package com.hb.study.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author huangbing
 * @date 2019/12/02 20:32
 */
public class CustomLock<E>{

    private static  Object[] elements = new Object[6];
    private static  int addNotifyAllCount = 0;
//    private static BlockingQueue blockingQueue = new ArrayBlockingQueue(0);

    private int count  = 0;
    private Lock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
                  CustomLock<String> customLock = new CustomLock<>();
                    for (int i = 0; i < 6; i++) {
                         new Thread(() -> {
                             customLock.add("success");
                         }).start();
                         new Thread(() -> {
                             System.out.println(customLock.get(0));
                         }).start();
                    }
    }

    public void add(E e){
           synchronized (this){
                 checkNotNull(e);
                 if(count == elements.length){
                     try {
                         this.wait();
                     } catch (InterruptedException e1) {
                         e1.printStackTrace();
                     }
                 }
                 elements[count] = e;
                 count ++ ;
               System.out.println("add have lock:"+countNotifyAll(this));

           }
    }
    public E get(int index){
            synchronized (this){
                if(0 == elements.length){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                    elements[index] = null;
                    Object[] newArray = {};
                    System.arraycopy(elements,1, newArray,0,1);
                    this.notifyAll();
                    return (E)newArray[index];
            }
    }
    private static void checkNotNull(Object o){
           if(null == o){
               throw new NullPointerException();
           }
    }
    private static int countNotifyAll(Object o){
        addNotifyAllCount ++;
        o.notifyAll();
        return addNotifyAllCount;
    }

}
