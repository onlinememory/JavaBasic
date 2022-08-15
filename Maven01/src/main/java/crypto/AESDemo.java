package crypto;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AESDemo {
    public static void main(String[] args) throws Exception {
        String inputStr = "实现ÄÖÜ";
        byte[] inputData = inputStr.getBytes();
        byte[] key = initKey();
        // Base64.encodeBase64String(key)
        System.out.println("Key :\t" + key.length + "\t" + Arrays.toString(key));
        String base64 = Base64.encodeBase64String(key);
        String hex = Hex.encodeHexString(key);
        System.out.println("Key base64:\t" + base64.length() + "\t" + base64);
        System.out.println("Key Hex:\t" + hex.length() + "\t" + hex);

        // ENCrypt
        inputData = encrypt(inputData, key);
        System.out.println("Encrypt :\t" + Hex.encodeHexString(inputData).toUpperCase());

        // DECrypt
        byte[] outputData = decrypt(inputData, key);
        System.out.println("DECrypt :\t" + new String(outputData));
    }

    public static final String KEY_ALGORITHM = "AES";

    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key密钥
     * @throws Exception
     */
    private static Key toKey(byte[] key) {
        //实例化DES密钥材料
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[]解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //还原密钥
        Key k = toKey(key);
        /*
         *实例化
         *使用PKCS7Padding填充方式，按如下方式实现
         *Cipher.getInstance（CIPHER_ALGORITHM，"BC"）；
         */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[]加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);

        return cipher.doFinal(data);
    }

    /**
     * 生成密钥
     *
     * @return byte[] 二进制密钥
     * @throws Exception
     */
    public static byte[] initKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);

        keyGenerator.init(128);

        SecretKey secretKey = keyGenerator.generateKey();

        return secretKey.getEncoded();
    }
}
