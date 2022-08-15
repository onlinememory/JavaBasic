package crypto;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MACDemo {
    public static void main(String[] args) throws Exception {
        String originalText;
        originalText = "HmacMD5消息摘要" ;

        byte[] input = originalText.getBytes(StandardCharsets.UTF_8);

        //初始化密钥
        byte[] key;
        byte[] output;
        String hexOutput;


        System.out.println("#################### " + "HmacMD5" + " #########################");

        System.out.println("originalText : " + originalText);

        key = initHmacMD5Key();
        System.out.println("Key : length : " + key.length + "\t" + Arrays.toString(key));
        //获得摘要信息
        output = encodeHmacMD5(input, key);

        System.out.println("MSG in Byte : " + output.length + "\t" + Arrays.toString(output));

        hexOutput = Hex.encodeHexString(output);
        System.out.println("MSG in Hex : " + hexOutput.length() + "\t" + hexOutput.toUpperCase());


        System.out.println("#################### " + "SHA" + " #########################");

        System.out.println("originalText : " + originalText);

        key = initHmacSHA512Key();
        System.out.println("Key : length : " + key.length + "\t" + Arrays.toString(key));
        //获得摘要信息
        output = encodeHmacSHA512(input, key);

        System.out.println("MSG in Byte : " + output.length + "\t" + Arrays.toString(output));

        hexOutput = Hex.encodeHexString(output);
        System.out.println("MSG in Hex : " + hexOutput.length() + "\t" + hexOutput.toUpperCase());

        System.out.println("#############################################");
    }

    public static byte[] initHmacMD5Key() {
        try {
            // 初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
            // 产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            // 获得密钥
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] encodeHmacMD5(byte[] data, byte[] key) {
        // 还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacMD5");
        Mac mac = null;
        try {
            // 实例化Mac
            mac = Mac.getInstance(secretKey.getAlgorithm());
            // 初始化Mac
            mac.init(secretKey);
            // 执行消息摘要
            return mac.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化HmacSHA1密钥
     *
     * @return byte[]密钥
     */
    public static byte[] initHmacSHAKey() {
        // 初始化KeyGenerator
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("HmacSHA1");
            // 产生密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //获得密钥
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * HmacSHA1消息摘要
     *
     * @param data 待做摘要处理的数据
     * @param key  密钥
     * @return byte[]消息摘要
     * @throws Exception
     */
    public static byte[] encodeHmacSHA(byte[] data, byte[] key) throws Exception {
        //还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA1");
        //实例化Mac
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        //初始化Mac
        mac.init(secretKey);
        //执行消息摘要
        return mac.doFinal(data);
    }

    /**
     * 初始化HmacSHA256密钥
     *
     * @return byte[]密钥
     * @throws Exception
     */
    public static byte[] initHmacSHA256Key() throws Exception {
        //初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        //产生密钥
        SecretKey secretKey = keyGenerator.generateKey();
        //获得密钥
        return secretKey.getEncoded();
    }

    /**
     * HmacSHA256消息摘要
     *
     * @param data 待做摘要处理的数据
     * @param key  密钥
     * @return byte[]消息摘要
     * @throws Exception
     */
    public static byte[] encodeHmacSHA256(byte[] data, byte[] key)
            throws Exception {
        //还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA256");
        //实例化Mac
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        //初始化Mac
        mac.init(secretKey);
        //执行消息摘要
        return mac.doFinal(data);
    }


    /**
     * 初始化HmacSHA384密钥
     *
     * @return byte[]密钥
     * @throws Exception
     */
    public static byte[] initHmacSHA384Key() throws Exception {
        //初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA384");
        //产生密钥
        SecretKey secretKey = keyGenerator.generateKey();
        //获得密钥
        return secretKey.getEncoded();
    }

    /**
     * HmacSHA384消息摘要
     *
     * @param data 待做摘要处理的数据
     * @param key  密钥
     * @return byte[]消息摘要
     * @throws Exception
     */
    public static byte[] encodeHmacSHA384(byte[] data, byte[] key) throws Exception {
        //还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA384");
        //实例化Mac
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        //初始化Mac
        mac.init(secretKey);
        //执行消息摘要
        return mac.doFinal(data);
    }

    /**
     * 初始化HmacSHA512密钥
     *
     * @return byte[]密钥
     * @throws Exception
     */
    public static byte[] initHmacSHA512Key() throws Exception {
        //初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512");
        //产生密钥
        SecretKey secretKey = keyGenerator.generateKey();
        //获得密钥
        return secretKey.getEncoded();
    }

    /**
     * HmacSHA512消息摘要
     *
     * @param data 待做摘要处理的数据
     * @param key  密钥
     * @return byte[]消息摘要
     * @throws Exception
     */
    public static byte[] encodeHmacSHA512(byte[] data, byte[] key) throws Exception {
        //还原密钥
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA512");
        //实例化Mac
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        //初始化Mac
        mac.init(secretKey);
        //执行消息摘要
        return mac.doFinal(data);
    }
}
