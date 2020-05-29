package com.hb.study;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * @author huangbing
 * @date 2020/03/29 16:51
 */
public class ArrayListOriginalCodeStudy {

    private static Logger logger = LoggerFactory.getLogger(ArrayListOriginalCodeStudy.class);

       @Test
       public void originalCodeStudy(){
           ArrayList<String> arrayList = Lists.newArrayList("status", "success", "fail");
//           arrayList.iterator().next();
//           arrayList.iterator().remove();
//           因为这里调用了两次arrayList.iterator(),获得了两个iterator对象,所以remove的时候会报错
//           在调用remove的时候会去检查lastRet是否小于0,默认值是-1,所以第一次调用的时候抛出IllegalStateException
           Iterator<String> iterator = arrayList.iterator();
//         arrayList.add("exception");
           iterator.next();
           iterator.remove();
           //这里是同一个iterator对象,并且在remove之前调用了next方法,这里会将cursor(默认值为0)值赋给lastRet,所以在remove的时候不会抛出IllegalStateException
           arrayList.forEach(testResult -> {
                  logger.info(testResult);
           });
//             foreach底层就是使用的迭代器,所以这里在给数组列表中添加元素的时候会抛出并发修改异常,
//             是因为ArrayList -> add方法在扩容的时候会导致modCount++,从而使获得的Itr对象中的modCount != expectedModCount
//             for(String testResult : arrayList){
//                  arrayList.add("empty");
//             }
//             解决并发异常的两种方法:1、使用普通for循环,不使用迭代器 2、使用ListItr类中的add方法添加元素
              for(int index = 0; index < arrayList.size(); ++index){
                   logger.info("remove:"+ arrayList.remove(index));
              }
//            ps:尽量不使用迭代器,切记不要在获得迭代器对象后,在添加元素.列如下面已经获得了迭代器,还调用了add方法,也会抛出并发修改异常
              ListIterator<String> listIterator = arrayList.listIterator();
//            arrayList.add("exception");
              while(listIterator.hasNext()){
                  if("exception".equals(listIterator.next())){
                      listIterator.add("add success");
                  }
             }
//              这里抛出并发修改异常是因为我上面调用了ArrayList的remove方法,导致modCount++了,而此时我已经获取了迭代器,expectedModCount值
//              变成了1,而modCount的值已经变成了2.(因为我还调用了一次Itr中的remove方法,会再次调用ArrayList中的remove方法,导致modeCount++,
//              但是这里会将modeCount++后的值在赋给expectedModCount,所以modeCount和expectedModCount值都为1,这也是迭代器提供解决在遍历的
//              时候对数组列表或者集合中元素修改产生的并发修改异常,但是ArrayList的remove的调用导致modCount再次++,变成了2,而expectedModCount还是
//              1
             while (iterator.hasNext()){
                   if("status".equals(iterator.next())){
//                     这里在调用Itr中的remove方法会去调用checkForComodification方法,因为modCount!=expectedModCount,所以抛出异常
                       iterator.remove();
                   }
             }
//             抛出并发修改异常的主要原因就是modCount!=expectedModCount
             ArrayList<String> arrayList1 = Lists.newArrayList("exception");
             arrayList1.forEach(testResult ->{
//                   依然会抛出并发修改异常,详情查看ArrayList的forEach方法
                     arrayList1.remove("string");
             });

       }

}
