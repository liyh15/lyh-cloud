package wibo.cloud.custom.jvm;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname SoftReferenceTest
 * @Description TODO
 * @Date 2020/10/28 10:59
 * @Created by lyh
 */
public class SoftReferenceTest {


    public static void main(String[] args) {

        Object obj = new Object();
        SoftReference<Object> objectSoftReference = new SoftReference<>(obj);
        obj = null;
        byte [] b = new byte[1024 * 1024 + 1024 * (4096-2311 + 15) + 4400];
        System.out.println(objectSoftReference.get());
    }
}
