package wibo.cloud.custom.jvm;

/**
 * @Classname StaticTest
 * @Description TODO
 * @Date 2021/2/26 10:02
 * @Created by lyh
 */
public class StaticTest {

    public static String a = "1231";

    public final String b = "acc"; //

    public static final String c = "qwe"; // 作为一个宏变量, 不会出现在字节码中


    public StaticTest() {
        System.out.println("asdasd");
        System.out.println(c);
        System.out.println(b);
    }

    public void aaa() {
        final String vb = "123123";
        System.out.println(vb);
    }
}
