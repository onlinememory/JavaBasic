package channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * FileChannel
 */
public class FileChannelDemo {
    public static void main(String[] args) {
        // create FileChannel
        String filename = "c:/temp/test.txt";
        String mode = "rw";
        fileChannelReadDemo(filename, mode);


//        System.out.print("\n");
    }

    private static void fileChannelWriteDemo(String filename, String mode) {
        try {
            // 创建 FileChannel 需要输入输出流
            // 这里是输入流的例子，从文件中读取数据
            RandomAccessFile aFile = new RandomAccessFile(filename, mode);
            // 通过getChannel得到实例

            FileChannel aChannel = aFile.getChannel();
            // create buffer
            ByteBuffer buffer = ByteBuffer.allocate(10);
            // read from buffer
            int bytesRead = aChannel.read(buffer);
            int c = 1;
            int n = 1;

//            buffer.clear();
//            System.out.println(c++ + "\t" + bytesRead);
//
//            bytesRead = aChannel.read(buffer);
//            System.out.println(c++ + "\t" + bytesRead);
//
//            buffer.clear();
//            bytesRead = aChannel.read(buffer);
//            System.out.println(c++ + "\t" + bytesRead);
//
//            buffer.clear();
//            bytesRead = aChannel.read(buffer);
//            System.out.println(c++ + "\t" + bytesRead);
//
//            buffer.clear();
//            bytesRead = aChannel.read(buffer);
//            System.out.println(c++ + "\t" + bytesRead);

            while (bytesRead != -1) {
                System.err.println("read : " + bytesRead);
                // 反转buffer以便读取buffer中的数据
                buffer.flip();
                while (buffer.hasRemaining()) {
                    int b = buffer.get();
                    System.out.println(c++ + "\t" + b + "\t" + (char) b);
                }
                // 必须清空buffer，否则buffer满了之后，虽然channel读取到了最后，但是返回bytesRead是0，而不是-1，导致死循环
                buffer.clear();
                System.out.println("buffer used times : " + "\t" + n++);
                bytesRead = aChannel.read(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void fileChannelReadDemo(String filename, String mode) {
        try {
            // 创建 FileChannel 需要输入输出流
            // 这里是输入流的例子，从文件中读取数据
            RandomAccessFile aFile = new RandomAccessFile(filename, mode);
            // 通过getChannel得到实例
            FileChannel aChannel = aFile.getChannel();
            // create buffer
            ByteBuffer buffer = ByteBuffer.allocate(10);
            // read from buffer
            int bytesRead = aChannel.read(buffer);
            int c = 1;
            int n = 1;

//            buffer.clear();
//            System.out.println(c++ + "\t" + bytesRead);
//
//            bytesRead = aChannel.read(buffer);
//            System.out.println(c++ + "\t" + bytesRead);
//
//            buffer.clear();
//            bytesRead = aChannel.read(buffer);
//            System.out.println(c++ + "\t" + bytesRead);
//
//            buffer.clear();
//            bytesRead = aChannel.read(buffer);
//            System.out.println(c++ + "\t" + bytesRead);
//
//            buffer.clear();
//            bytesRead = aChannel.read(buffer);
//            System.out.println(c++ + "\t" + bytesRead);

            while (bytesRead != -1) {
                System.err.println("read : " + bytesRead);
                // 反转buffer以便读取buffer中的数据
                buffer.flip();
                while (buffer.hasRemaining()) {
                    int b = buffer.get();
                    System.out.println(c++ + "\t" + b + "\t" + (char) b);
                }
                // 必须清空buffer，否则buffer满了之后，虽然channel读取到了最后，但是返回bytesRead是0，而不是-1，导致死循环
                buffer.clear();
                System.out.println("buffer used times : " + "\t" + n++);
                bytesRead = aChannel.read(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}