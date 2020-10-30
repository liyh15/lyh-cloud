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
    public static void main(String[] args) throws InterruptedException {
        List<GCRootTest> tests = new ArrayList<>();
        while (true) {
            tests.add(new GCRootTest());
        }
    }
}
