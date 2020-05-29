package com.hb.study.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author huangbing
 * @date 2019/11/04 14:50
 */
public class JvmStudy {
    public int  studyJvm(){
         int var1 = 10;
         int var2 = 6;
         int var3 = var1 + var2;
         int var4 = var3 + 4;
         int var5 = var1 * var2 * var3 * var4;
         return var5;
    }

    public static void main(String[] args) {
           JvmStudy jvmStudy = new JvmStudy();
           jvmStudy.studyJvm();
           Lock lock = new ReentrantLock();
           lock.lock();
           int compare = 8;
           if(compare > 10)
               System.out.println("start");
               System.out.println("end");
    }

}
