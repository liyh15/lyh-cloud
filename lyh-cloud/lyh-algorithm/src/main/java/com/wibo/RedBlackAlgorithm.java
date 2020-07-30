package com.wibo;

import sun.misc.Launcher;

import java.net.URL;
import java.util.Arrays;
/**
 * @Classname RedBlackAlgorithm
 * @Description RedBlackAlgorithm test class
 * @Date 2020/7/27 16:46
 * @Created by lyh
 */
public class RedBlackAlgorithm{

    private static int num = 1;
    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(classLoader);

        ClassLoader classLoader2 = classLoader.getParent();
        System.out.println(classLoader2);

        ClassLoader classLoader3 = classLoader2.getParent();
        System.out.println(classLoader3);

        System.out.println(RedBlackAlgorithm.class.getClassLoader());

        System.out.println(String.class.getClassLoader());

        URL [] urls = Launcher.getBootstrapClassPath().getURLs();

        System.out.println(Arrays.toString(urls));

        String dirs = System.getProperty("java.ext.dirs");
        System.out.println(dirs);

        System.out.println((char)3);
    }
}
