package wibo.cloud.custom.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname RegexTest
 * @Description TODO
 * @Date 2021/4/25 15:56
 * @Created by lyh
 */
public class RegexTest {
    public static void main(String[] args) {
        String a = "aaaacaaaaaaac";
        System.out.println(Pattern.matches("[a-zA-Z]+?c",a));
        // TODO 使用非贪婪模式，匹配少两字符
        // TODO aaaac
        // TODO aaaaaaac
        // TODO 当?号跟在如下限制符后面时，使用非贪婪模式（*,+,?，{n}，{n,}，{n,m}）
        Pattern p = Pattern.compile("[a-zA-Z]+?c");
        Matcher matcher = p.matcher(a);
        while(matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
