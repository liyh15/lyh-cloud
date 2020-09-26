package wibo.cloud.custom.jvm;

/**
 * @Classname MemberDemo
 * @Description TODO
 * @Date 2020/9/22 15:53
 * @Created by lyh
 */
public class MemberDemo {

    public void two() {
        System.out.println("two");
    }

    public static void main(String[] args) {
       ClassOne one = new ClassOne();
       one.one();
       MemberDemo memberDemo = new MemberDemo();
       memberDemo.two();
       String a = "aaa";

    }
}
