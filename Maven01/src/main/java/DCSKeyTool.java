import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public class DCSKeyTool {


    private static byte[] keyValue = new byte[]{       //用户密钥
            22, 25, -35, -45, 25, 98, -55, -45, 10, 35, -45, 25,
            26, -95, 25, -65, -78, -99, 85, 45, -62, 10, -0, 11,
            -35, 48, -98, 65, -32, 14, -78, 25, 36, -56, -45, -45,
            12, 15, -35, -75, 15, -14, 62, -25, 33, -45, 55, 68, -88
    };
    private static byte[] iv = new byte[]{
            -12, 35, -25, 65, 45, -87, 95, -22, -15, 45, 55, -66, 32, 5 - 4, 84, 55
    };
    private static SecretKey key;                       //加密密钥
    private static AlgorithmParameterSpec paramSpec;    //算法参数
    private static Cipher ecipher;                      //加密算法

    static {
        KeyGenerator kgen;
        try {
            //为指定算法生成一个密钥生成器对象。
            kgen = KeyGenerator.getInstance("AES");
            //使用用户提供的随机源初始化此密钥生成器，使其具有确定的密钥长度。
            kgen.init(128, new SecureRandom(keyValue));
            //使用KeyGenerator生成（对称）密钥。
            key = kgen.generateKey();
            //使用iv中的字节作为IV来构造一个 算法参数。
            paramSpec = new IvParameterSpec(iv);
            //生成一个实现指定转换的 Cipher 对象
            ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密，使用指定数据源生成密钥，使用用户数据作为算法参数进行AES加密
     *
     * @param msg 加密的数据
     * @return
     */
    public static String encrypt(String msg) {
        String str = "";
        try {
            //用密钥和一组算法参数初始化此 cipher
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            //加密并转换成16进制字符串
            str = asHex(ecipher.doFinal(msg.getBytes()));
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 解密，对生成的16进制的字符串进行解密
     *
     * @param value 解密的数据
     * @return
     */
    public static String decrypt(String value) {
        try {
            ecipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            return new String(ecipher.doFinal(asBin(value)));
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param buf
     * @return
     */
    private static String asHex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10)//小于十前面补零
                strbuf.append("0");
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    /**
     * 将16进制字符串转换成字节数组
     *
     * @param src
     * @return
     */
    private static byte[] asBin(String src) {
        if (src.length() < 1)
            return null;
        byte[] encrypted = new byte[src.length() / 2];
        for (int i = 0; i < src.length() / 2; i++) {
            String msg = src.substring(i * 2, i * 2 + 1);
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);//取高位字节
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);//取低位字节
            encrypted[i] = (byte) (high * 16 + low);
        }
        return encrypted;
    }

    public static void main(String[] args) {
        String airport = "FRA";
        String functions = "[CKI];[Gate]";
        String date = "2050-12-31";

        String msgToCompare = "{AirportCode:FRA,Products:[CKI];[Gate],TimeLimited:2050-12-31}";
        msgToCompare = "{AirportCode:FRA,Products:[PRIV.COM.TRAVELSKY.ANGEL.CLIENT.CKI.PERSPECTIVE];[PRIV.COM.TRAVELSKY.ANGEL.CLIENT.GATE.PERSPECTIVE],TimeLimited:2050-12-31}";
        msgToCompare = msgToCompare.toUpperCase();
        String msg = "{AirportCode:" + airport.toUpperCase() + ",Products:" + functions + ",TimeLimited:" + date + "}";


//        msg = "{AirportCode:FRA,Products:[PRIV.COM.TRAVELSKY.ANGEL.CLIENT.CKI.PERSPECTIVE];[PRIV.COM.TRAVELSKY.ANGEL.CLIENT.GATE.PERSPECTIVE],TimeLimited:2050-12-31}";
        msg = msg.toUpperCase();
        System.err.println(msg);

        msg = msg.replace("[DOP]", "[PRIV.COM.TRAVELSKY.ANGEL.CLIENT.ANGELDOP.PERSPECTIVE]");
        msg = msg.replace("[CKI]", "[PRIV.COM.TRAVELSKY.ANGEL.CLIENT.CKI.PERSPECTIVE]");
        msg = msg.replace("[GATE]", "[PRIV.COM.TRAVELSKY.ANGEL.CLIENT.GATE.PERSPECTIVE]");
        msg = msg.replace("[FDC]", "[PRIV.COM.TRAVELSKY.ANGEL.CLIENT.FDC.PERSPECTIVE];[PRIV.COM.TRAVELSKY.ANGEL.CLIENT.FDC.PERSPECTIVE.FLTPLAN];[PRIV.COM.TRAVELSKY.ANGEL.CLIENT.FDC.PERSPECTIVE.MONITER];[PRIV.COM.TRAVELSKY.ANGEL.CLIENT.FDC.PERSPECTIVE.STATIC];[PRIV.COM.TRAVELSKY.ANGEL.CLIENT.FDC.PERSPECTIVE4]");


        msg = msg.replace("[VAS]", "[priv.vas]");
        msg = msg.replace("[SEAT]", "[priv.payseat]");
        msg = msg.replace("[EXBG]", "[priv.exbg_integration]");
        msg = msg.replace("[REPORT]", "[priv.vasReport]");
        msg = msg.replace("[REFUND]", "[priv.vasRefund]");


        System.out.println("Original Message:\t" + msg.equals(msgToCompare) + "\nMSGo\t" + msg + "\nMSGc\t" + msgToCompare);

        String encryptedStr = encrypt(msg);
        System.out.println(encryptedStr);

        String decryptedStr = decrypt(encryptedStr);

        System.out.println(decryptedStr.equals(msg));
    }
}
