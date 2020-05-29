package com.hb.study.util;

import org.testng.annotations.Test;

/**
 * @author huangbing
 * @date 2019/10/14 20:16
 */
public class StudyAction {
       @Test
       public void testAutoCast(){
              byte byteVariable = 127;
              int intVariable = 1;
              System.out.println(Integer.toBinaryString(128));
              byte autoCastFail = (byte) (byteVariable + intVariable);
              System.out.println(autoCastFail);
              System.out.println(byteVariable+=intVariable);

       }
}
