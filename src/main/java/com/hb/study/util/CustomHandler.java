package com.hb.study.util;

import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author huangbing
 * @date 2019/10/16 15:20
 */
@Data
public class CustomHandler implements InvocationHandler {
    private Object target;
    public CustomHandler(Object target){
           this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           System.out.println("proxy create success");
           return method.invoke(getTarget(), args);
    }
}
