package wibo.cloud.custom.jvm;

/**
 * @Classname GCRootTest
 * @Description TODO
 * @Date 2020/10/23 16:52
 * @Created by lyh
 */
public class GCRootTest {
    public static void main(String[] args) throws InterruptedException {
        String a = "aaaaaaaaaaaaaa";
        String b = "bbbbbbbbbbbbbbb";
        GCRootTest test = new GCRootTest();
        Thread.sleep(10000000);
    }
}
