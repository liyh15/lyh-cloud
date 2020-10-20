package wibo.cloud.custom.jvm;

import java.io.*;
import java.util.Random;

/**
 * @Classname StringTableTest
 * @Description TODO
 * @Date 2020/10/19 15:33
 * @Created by lyh
 */
public class StringTableTest {

    public static void write() throws IOException {
        FileOutputStream outputStream = null;
        try {
            File file = new File("test11.txt");
            file.createNewFile();
            System.out.println(file.getAbsolutePath());
            outputStream = new FileOutputStream(file);
            String str = "";
            for (int i = 0; i < 1000000; i ++) {
                int b = new Random().nextInt(5) + 5;
                str = "";
                for (int t = 0; t < b; t ++) {
                    char a = (char)(Math.random() * 25 + ((new Random().nextInt(2) == 1) ? 65 : 97));
                    str += a;
                }
                str += "\n";
                outputStream.write(str.getBytes());
            }
;        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public static void read() throws IOException {
        BufferedReader reader = null;
        long a = System.currentTimeMillis();
        try {
            File file = new File("test11.txt");
            reader = new BufferedReader(new FileReader(file));
            String data = null;
            while ((data = reader.readLine()) != null) {
                data.intern();
            }
        } catch (Exception e) {

        } finally {
            reader.close();
        }
        System.out.println(System.currentTimeMillis() - a);
    }


    public static void main(String[] args) throws IOException {
        //write();
      read();
    }
}
