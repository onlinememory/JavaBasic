package crypto;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public class AESUtil {
    //用户密钥
    private static byte[] keyValue = new byte[]{
            22, 25, -35, -45, 25, 98, -55, -45, 10, 35, -45, 25,
            26, -95, 25, -65, -78, -99, 85, 45, -62, 10, -0, 11,
            -35, 48, -98, 65, -32, 14, -78, 25, 36, -56, -45, -45,
            12, 15, -35, -75, 15, -14, 62, -25, 33, -45, 55, 68, -88
    };

    //算法参数
    private static byte[] iv = new byte[]{
            -12, 35, -25, 65, 45, -87, 95, -22, -15, 45, 55, -66, 32, 5 - 4, 84, 55
    };

    //加密密钥
    private static SecretKey key;
    //算法参数
    private static AlgorithmParameterSpec paramSpec;
    //加密算法
    private static Cipher ecipher;

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
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

            str = Hex.encodeHexString(ecipher.doFinal(msg.getBytes()));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
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
        String msg = "";
        try {
            ecipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            msg = Hex.encodeHexString(ecipher.doFinal(value.getBytes()));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
