package com.isolated.loading;

import java.io.*;
import java.util.*;

/**
 * 通过配置文件配置 需要自定义类加载器加载的jar
 */
public class ConfigLoader {

    private List<String> jarPaths;
    private List<String> classNames;

    public ConfigLoader(String configFilePath) throws IOException {
        Properties props = new Properties();
        try (InputStream in = new FileInputStream(configFilePath)) {
            props.load(in);
        }
        jarPaths = Arrays.asList(props.getProperty("jarPaths").split(";"));
        classNames = Arrays.asList(props.getProperty("classes").split(";"));
    }

    public CustomClassLoader getClassLoader() {
        return new CustomClassLoader(jarPaths);
    }

    public List<Class<?>> getClasses() throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        CustomClassLoader classLoader = getClassLoader();
        for (String className : classNames) {
            classes.add(classLoader.loadClass(className));
        }
        return classes;
    }
}
