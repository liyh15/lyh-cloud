package wibo.cloud.custom.jvm;

/**
 * @Classname BigClsDemo
 * @Description TODO
 * @Date 2020/9/15 10:49
 * @Created by lyh
 */
public class BigClsDemo {
    public static void main(String[] args) {
      String a = "aaa";
      String b = a +"bbb";
      String c = "aaabbb";
        System.out.println(b == c);
    }
}
