package com.wibo;

import java.io.*;

/**
 * @Classname ClassLoadTest
 * @Description custom classloader class
 * @Date 2020/7/28 9:42
 * @Created by lyh
 */
public class ClassLoadTest extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoadTest classLoadTest = new ClassLoadTest();
        Class c = classLoadTest.findClass("Apple.class");
        Apple apple = (Apple) c.newInstance();
        System.out.println(apple);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte [] result = getClassFromCustomPath(name);
            if (result == null) {
                throw new FileNotFoundException();
            } else {
                return defineClass(name, result, 0, result.length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getClassFromCustomPath(String path) throws IOException {
        // load class by custom path
        // if class file is encrypted,this should by decrypted
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        FileInputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte [] b = new byte[1024];
        int a = 0;
        if ((a = inputStream.read(b)) != -1) {
            byteArrayOutputStream.write(b, 0, a);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
