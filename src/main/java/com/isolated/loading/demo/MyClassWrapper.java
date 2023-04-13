package com.isolated.loading.demo;

import java.lang.reflect.*;

/**
 * 包装类，使用自定义类加载器的时候使用包装类包装使用的类
 *
 */
public class MyClassWrapper {

    private Object instance;

    public MyClassWrapper(Object instance) {
        this.instance = instance;
    }

    public void myMethod(int arg1, String arg2) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = instance.getClass().getMethod("myMethod", int.class, String.class);
        Object invoke = method.invoke(instance, arg1, arg2);

    }
}
