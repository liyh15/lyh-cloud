package com.wibo;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Classname ClassLoadTest
 * @Description custom classloader class
 * @Date 2020/7/28 9:42
 * @Created by lyh
 */
public class ClassLoadTest extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] result = getClassFromCustomPath(name);
            if (result == null) {
                throw new FileNotFoundException(name);
            } else {
                // TODO 第一个参数要是加载类的包全路径，要和package对应
                return defineClass("lyh." + name, result, 0, result.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ClassNotFoundException(name);
    }

    private byte[] getClassFromCustomPath(String name) {
        // 从自定义路径中加载指定类，返回类的字节码文件
        InputStream in = null;
        ByteArrayOutputStream out = null;
        String path = "C:\\lyh\\" + name + ".class";
        try {
            in = new FileInputStream(path);
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        /**
         * TODO 类加载器其实是根据传入的类包全路径然后到自己对应的路径下加载指定的类
         * TODO 由于双亲委派机制，会先从最顶层的类加载器开始加载，就是BootStrapClassLoader,如果父类没有则会委托给子类加载
         */ 
        ClassLoadTest classLoadTest = new ClassLoadTest();
        Class<?> clazz = classLoadTest.loadClass("Apple");
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("test");
        method.invoke(obj);
        System.out.println(obj.getClass().getClassLoader());
    }
}
