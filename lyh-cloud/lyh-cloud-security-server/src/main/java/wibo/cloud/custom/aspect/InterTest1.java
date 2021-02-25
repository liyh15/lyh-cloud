package wibo.cloud.custom.aspect;

import lombok.Data;

/**
 * @Classname InterTest1
 * @Description TODO
 * @Date 2020/11/30 14:53
 * @Created by lyh
 */
@Data
public class InterTest1 implements TestInter,Cloneable {

    private String a;

    @Override
    public void aaa() {
        System.out.println("aaaaaaaaaaaa");
    }

    public static void main(String[] args) throws CloneNotSupportedException {

        String aaa = new String("qwecccccccc");
        InterTest1 a = new InterTest1() {
            @Override
            public void aaa() {
                System.out.println(aaa);
            }
        };
        a.setA("aaaaaaaa");
        InterTest1 b = (InterTest1) a.clone();
        b.setA("czxczxczxc");
        System.out.println(a.getA());
        a.aaa();
        System.out.println(b.getA());
        b.aaa();
    }
}
