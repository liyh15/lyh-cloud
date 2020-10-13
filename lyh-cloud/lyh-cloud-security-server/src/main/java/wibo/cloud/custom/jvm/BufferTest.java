package wibo.cloud.custom.jvm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferTest {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 2014 * 1024);
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = new FileInputStream("D:\\框架自我理解\\springBoot.doc").getChannel();
            outChannel = new FileOutputStream("D:\\bbb.doc").getChannel();
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inChannel.close();
            outChannel.close();
        }
    }
}
