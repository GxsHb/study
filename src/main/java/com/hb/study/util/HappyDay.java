package com.hb.study.util;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import lombok.Data;
import lombok.Synchronized;
import org.assertj.core.util.Lists;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.core.task.support.ExecutorServiceAdapter;
import org.testng.annotations.Test;
import sun.misc.Queue;
import sun.misc.Unsafe;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

/**
 * @author huangbing
 * @date 2019/12/14 20:46
 */
@Data
public class HappyDay {
    private static final Lock customLock = new ReentrantLock(false);
    private static volatile boolean eat = false;
    //     private  static ExecutorService executorService = Executors.newFixedThreadPool(6);
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 7, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1), HappyDay.RejectedExecutionHandlerImpl.getInstance());
    private static Condition condition = customLock.newCondition();
    //       private static AtomicInteger counter = new AtomicInteger();
    private static volatile int counter = 0;

    private static class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            eat = true;
        }

        public static RejectedExecutionHandlerImpl getInstance() {
            return new RejectedExecutionHandlerImpl();
        }
    }

    @Synchronized
    public static void tryReduceWeight() {
        try {
            customLock.lock();
            while (!eat) {
//                   counter++;
                System.out.println("currentThread:" + Thread.currentThread().getName());
                condition.await();
            }
            System.out.println("真香");
            condition.signalAll();
            threadPoolExecutor.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            customLock.unlock();
        }
    }


    private static BlockingQueue<String> blockingQueue = new LinkedBlockingQueue(4);


    public void unsafeStudy() {
        Unsafe unsafe = Unsafe.getUnsafe();
////        try {
////            System.out.println(unsafe.objectFieldOffset(HappyDay.class.getDeclaredField("value")));
////        } catch (NoSuchFieldException e) {
////            e.printStackTrace();
////        }
        LockSupport.park();
        unsafe.park(false, 0L);
        LockSupport.unpark(Thread.currentThread());
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readWriteLock.readLock();
        readWriteLock.writeLock();
//        AbstractQueuedSynchronizer
//          ArrayBlockingQueue

    }

    static int nums[] = {2, 9, 11, 7};
    static int headIndex = 0;
    static int nextIndex = headIndex + 1;

    private static class Node<E>{
            E item;
            Node<E> prev;
            Node<E> next;
            Node(Node<E> prev, E element, Node<E> next){
                 this.item = element;
                 this.next = next;
                 this.prev = prev;
            }
    }
    static int index;
    transient static Object[] covObjectArray = {1, 2,  3, 4, 5,};
    static int size = covObjectArray.length;

    public static void main(String[] args) throws ExecutionException, InterruptedException{
            int[] intArray = {1, 2,  3, 4, 5,};
            System.arraycopy(intArray, 4, intArray, 3, 1);
            int[] newIntArray = new int[intArray.length - 1];
            System.arraycopy(intArray, 0, newIntArray, 0, newIntArray.length);
//            Arrays.copyOf();
            System.out.println(Arrays.toString(newIntArray));
            System.out.println(Arrays.toString(remove(covObjectArray, 3)));
            System.out.println(Arrays.toString(firstDayTask(new int[]{0, 1, 0, 3, 2})));
//            removeDup(test);
            int[] test = new int[]{1, 2, 3, 4, 5};
            System.out.println(Arrays.toString(rightRotate(test, 2)));






    }
//            public void gcWork(int index){
//                int size = covObjectArray.length;
//                System.arraycopy(covObjectArray, index + 1, covObjectArray, index, size - index - 1 );
//                covObjectArray[--size] = null;
//            }
      public static <T> T[] remove(T[] element, int index){
              if(index < 0 || index >= element.length || element.length == 0){
                  throw new IllegalArgumentException("element length is zero or index than element length");
              }
              List<T> covList = Arrays.asList(element);

//              covList.remove(index);
               T delElement = covList.get(index);
               delElement = null;

               element[--size] = null;

//          System.out.println(covList.toString());
//              return  (T[])covList.toArray();
          return element;

      }

      public static int[] firstDayTask(int[] nums){
        //0 1 0 3 2 移动0
                int length = nums.length;
                int temp = 0;
                if (0 == length) {
                    throw new IllegalArgumentException("length is zero");
                }
                for (int index = 0; index < length; ++index) {
                    if (nums[index] != 0) {
                        nums[temp] = nums[index];
                        if (temp != index) {
                            nums[index] = 0;
                        }
                        ++temp;
                    }

                }
                 return nums;
      }

      public static void removeDup(int[] nums){
//        1, 1 ,2, 2, 3, 4, 4, 6

//        1, 2, 3, 4, 6, 1, 2, 4
              int length = nums.length;
              int next = 0;
              for (int index =1; index < length; ++index) {
                  if (nums[index] != nums[next]) {
                      next ++;
                      nums[next] = nums[index];
                  }
              }

      }

      public static int[] rightRotate(int[] array , int k){
//          int[] test = new int[]{1, 2, 3, 4, 5};
//          51234
//           45123
//            34512
//             23451
//              12345
              int length = array.length;
              if (length == 0 || k < 0 || k > length) {
                      throw new IllegalArgumentException("please check you parameter");
              }
              int rotate= 0;
              for(int index = 0; index < k; ++index){
                  if(rotate < k){
                      array[rotate] = array[length - 1 - index];
                      ++rotate;
                  }



              }
              return array;
      }


}
