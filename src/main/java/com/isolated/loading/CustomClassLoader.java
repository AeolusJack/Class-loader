package com.isolated.loading;

import java.io.*;
import java.lang.reflect.*;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 自定义类加载器
 */
public class CustomClassLoader extends ClassLoader {

    private List<String> jarPaths;

    public CustomClassLoader(List<String> jarPaths) {
        this.jarPaths = jarPaths;
    }

    /**
     * 使用自定义类加载器加载jar和class，未找到则用父加载器
     * @param className
     *         The <a href="#binary-name">binary name</a> of the class
     *
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        for (String jarPath : jarPaths) {
            try {
                JarFile jarFile = new JarFile(jarPath);
                JarEntry entry = jarFile.getJarEntry(className.replace('.', '/') + ".class");
                if (entry != null) {
                    byte[] classData = loadClassData(jarFile, entry);
                    return defineClass(className, classData, 0, classData.length);
                }
            } catch (IOException e) {
                // ignore and continue to next jar file
            }
        }
        throw new ClassNotFoundException(className);
    }

    private byte[] loadClassData(JarFile jarFile, JarEntry entry) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        InputStream input = jarFile.getInputStream(entry);
        byte[] data = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = input.read(data)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return buffer.toByteArray();
    }

    public Object newInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = loadClass(className);
        return clazz.newInstance();
    }

    public Object invokeMethod(Object obj, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?>[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        Method method = obj.getClass().getMethod(methodName, parameterTypes);
        return method.invoke(obj, args);
    }
}
