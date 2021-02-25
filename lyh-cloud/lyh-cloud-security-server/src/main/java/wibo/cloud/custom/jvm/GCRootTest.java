package wibo.cloud.custom.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname GCRootTest
 * @Description TODO
 * @Date 2020/10/23 16:52
 * @Created by lyh
 */
public class GCRootTest {

   private static final int _1MB = 1024 * 1024;

    public static void testAllocation() {
        byte [] a1,a2,a3,a4;
        a1 = new byte[2 * _1MB];
        a2 = new byte[2 * _1MB];
        a3 = new byte[2 * _1MB];
        a4 = new byte[4 * _1MB];
    }

    public static void main(String[] args) throws InterruptedException {
        GCRootTest.testAllocation();

    }
}
