package encoding;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Utf8Test {
    public static void main(String[] args) {
        byte[] bs = {-17, -69, -65};
        String str = new String(bs, StandardCharsets.UTF_8);
        System.err.println(0x0041);
        System.err.println(0x0042);
        System.err.println(0x0043);

        String fileStr = "D:/test/test-1.txt";
        File file = new File(fileStr);

        try {
            InputStream ips = new FileInputStream(file);
            byte[] bytes = new byte[3];
            ips.read(bytes);
            for (byte b : bytes) {
                System.out.println(b);
            }
            ips.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileStr = "D:/test/test-1.txt";
        try {
            FileOutputStream fops = new FileOutputStream(fileStr);
            fops.write('\uFEFF');
            fops.flush();
            fops.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int decimal = '\uFEFF';
        byte b1 = (byte) decimal;
        System.out.println("int -" + decimal);
        System.out.printf("Hexadecimal: %X\n", decimal);
        System.out.printf("Unicode: u%04X\n", decimal);
        System.out.printf("Latin small letter: %c\n", (char) decimal);
    }
}
