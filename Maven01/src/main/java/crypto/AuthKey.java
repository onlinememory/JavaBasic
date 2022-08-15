package crypto;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;

public class AuthKey {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "你好哦!";
        System.out.println(Arrays.toString(s.getBytes(StandardCharsets.UTF_8)));
        System.out.println(Arrays.toString(s.getBytes("GBK")));
        System.out.println(Arrays.toString(s.getBytes()));
        //因为getBytes()默认使用GBK编码， 而解析时使用UTF-8编码，肯定出错。

        //用户密钥
        byte[] keyValue = new byte[]{
                22, 25, -35, -45, 25, 98, -55, -45, 10, 35, -45, 25,
                26, -95, 25, -65, -78, -99, 85, 45, -62, 10, -0, 11,
                -35, 48, -98, 65, -32, 14, -78, 25, 36, -56, -45, -45,
                12, 15, -35, -75, 15, -14, 62, -25, 33, -45, 55, 68, -88
        };

        byte[] iv = new byte[]{
                -12, 35, -25, 65, 45, -87, 95, -22, -15, 45, 55, -66, 32, 5 - 4, 84, 55
        };
        System.out.println(iv.length);
//        System.out.println(Arrays.toString("abc".getBytes()));
//        System.out.println("Default" + new String(iv));
//        System.out.println("UFT" + new String(iv, "UTF-8"));
//        System.out.println("GBK" + new String(iv, "GBK"));
//        codingTest03(keyValue);
//        System.out.println("keyValue length : " + keyValue.length);

//        SecureRandom secureRandom = new SecureRandom(keyValue);

        String str = "A";
        byte[] bytes = str.getBytes();
//        codingTest01(bytes);
//
        str = "中国AB";
//
//        bytes = str.getBytes("GBK");
//        codingTest02(bytes);
//
        bytes = str.getBytes("GBK");
        bytes = str.getBytes("UTF-8");
//        codingTest03(bytes);
//        codingTest01(iv);
//        codingTest02(iv);
//        codingTest03(iv);
//        String str = ""
//        Date date = new Date();
//        String str = String.valueOf(date.getTime());
//        System.out.println(date.getTime() + "\t" + str.length());

//        codingTest01(keyValue);
//        test01();
    }

    private static void codingTest01(byte[] keyValue) throws UnsupportedEncodingException {
        byte[] temp = new byte[1];
        for (int i = 0; i < keyValue.length; i++) {
            temp[0] = keyValue[i];
            System.out.println("codingTest01 : " + Arrays.toString(temp) + "\t" + new String(temp) + "\t");
        }
    }

    private static void codingTest02(byte[] keyValue) throws UnsupportedEncodingException {
        byte[] temp = new byte[2];
        for (int i = 0; i < keyValue.length - 1; i++) {
            temp[0] = keyValue[i];
            temp[1] = keyValue[i + 1];
//            temp[2] = keyValue[i + 2];
            System.out.println("GBK Test : \t-" + new String(temp, "GBK"));
        }
    }

    private static void codingTest03(byte[] keyValue) throws UnsupportedEncodingException {
        byte[] temp = new byte[3];
        for (int i = 0; i < keyValue.length - 2; i++) {
            temp[0] = keyValue[i];
            temp[1] = keyValue[i + 1];
            temp[2] = keyValue[i + 2];
            System.out.println("codingTest03\t" + "-" + new String(temp, "UTF-8") + "-\t" + Arrays.toString(temp));
        }
    }

    private static void test01() throws UnsupportedEncodingException {
        System.out.println("test01: ");
        Date date = new Date();
        String time = String.valueOf(date.getTime());
        time = "中国民航信息网络股份有限公司图形化离港系统";
//        time = "";
        for (char c : time.toCharArray()) {
            System.out.println(
                    c + "\t" + (int) c + "\t" + new String() +
                            "UTF\t" + Arrays.toString(String.valueOf(c).getBytes(StandardCharsets.UTF_8)) +
                            "GBK\t" + Arrays.toString(String.valueOf(c).getBytes("GBK"))
            );
        }
    }
}
