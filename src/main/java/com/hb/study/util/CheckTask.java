package com.hb.study.util;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author huangbing
 * @date 2020/01/01 15:15
 */
public class CheckTask {
    private static Lock lock = new ReentrantLock(false);
    private static Condition checkTask = lock.newCondition();
    private volatile static int[] taskCapacity = new int[0];
    private volatile static int size = taskCapacity.length;
    private volatile static boolean checkMySelfTask = true;
    private volatile static boolean expansion = false;

    public static void main(String[] args) {
//        new Thread(() ->{
//            updateTask();
//        }).start();
//        new Thread(() ->{
//            checkTask();
//        }).start();
         Condition a = lock.newCondition();
         Condition b = lock.newCondition();
         Condition c = lock.newCondition();
        for (int i = 0; i < 10; i++) {
             new Thread(()->{
                 lock.lock();
                 try {
                     System.out.println(Thread.currentThread().getName());
                     b.await();
                     c.await();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 } finally {
                     lock.unlock();
                     b.signalAll();
                 }
             },"A").start();
            new Thread(()->{
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName());
                    a.wait();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                    c.signalAll();
                }

            },"B").start();
            new Thread(()->{
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName());
                } finally {
                    lock.unlock();
                    a.signalAll();
                }
            },"C").start();
        }

    }
    public static void checkTask(){
        lock.lock();
        try {
            if (expansion){
//              System.arraycopy(taskCapacity, 0, newTaskCapacity, 0, size);
                taskCapacity = Arrays.copyOf(taskCapacity, ++size);
                checkTask.signalAll();
                checkMySelfTask = false;
           }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public static boolean updateTask(){
        lock.lock();
        try {
            while (checkMySelfTask) {
                System.out.println("start updateTask");
                expansion = true;
                checkTask.await();
                if (0 == size) {
                    System.out.println("end updateTask");
                    return true;
                }
            }
            System.out.println("updateTask fail");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return !checkMySelfTask;
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
           lock.lock();
            int var = 10;
            var = var++;
            System.out.println(var);
            lock.unlock();
            Future<Boolean> futureTask = new FutureTask(()->{
                return false;
            });
            boolean result = futureTask.get();
            ExecutorService service = new ScheduledThreadPoolExecutor(3);
                     service.submit(()->{}).get();
        }
    }

