package com.isolated.loading.demo;

import com.isolated.loading.ConfigLoader;
import com.isolated.loading.CustomClassLoader;

public class Main {

    public static void main(String[] args) throws Exception {
        String className = "com.example.MyClass";
        ConfigLoader configLoader = new ConfigLoader("");
        CustomClassLoader classLoader = configLoader.getClassLoader();
        Object obj = classLoader.newInstance(className);
        MyClassWrapper wrapper = new MyClassWrapper(obj);
        wrapper.myMethod(123, "hello");

    }
}
