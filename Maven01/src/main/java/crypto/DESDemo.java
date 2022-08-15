package crypto;



import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
//import java.security.Key;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class DESDemo {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidKeyException, NoSuchProviderException {
        String inputStr = "DES";
        byte[] inputData = inputStr.getBytes();
        System.out.println("Input \t:" + inputStr);

        //初始化密钥
        byte[] key = initKey();
        Key k2 = initKey2();
        System.out.println(Arrays.toString(key));
        System.out.println(Arrays.toString(k2.getEncoded()));
        //Encrypt
        byte[] outputData = encrypt(inputData, key);
        System.out.println("Encrypted String : " + Base64.encodeBase64String(outputData));
        byte[] outputData2 = encrypt2(inputData, k2);
        System.out.println("Encrypted String 2: " + Base64.encodeBase64String(outputData2));

        //Decrypt
        byte[] decryptedData = decrypt(outputData, key);
        System.out.println("Decrypt : " + new String(decryptedData));
    }

    public static final String KEY_ALGORITHM = "DES";
    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        //实例化DES密钥材料
        DESKeySpec dks = new DESKeySpec(key);
        //实例化秘密密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        //生成秘密密钥
        SecretKey secretKey = keyFactory.generateSecret(dks);

//        System.out.println("toKey\t" + secretKey.getEncoded().length + "\t" + Arrays.toString(secretKey.getEncoded()));

        return secretKey;
    }

    /**
     * 生成密钥＜br＞
     * Java 6只支持56位密钥＜br＞
     * Bouncy Castle支持64位密钥＜br＞
     *
     * @return byte[]二进制密钥
     * @throws Exception
     */
    public static byte[] initKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        /*
         *实例化密钥生成器
         *若要使用64位密钥注意替换
         *将下述代码中的
         *KeyGenerator.getInstance（CIPHER_ALGORITHM）；
         *替换为
         *KeyGenerator.getInstance（CIPHER_ALGORITHM，"BC"）；
         */
//        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        /*
         *初始化密钥生成器
         *若要使用64位密钥注意替换
         *将下述代码kg.init（56）；
         *替换为kg.init（64）；
         */
        kg.init(56);
        //生成秘密密钥
        SecretKey secretKey = kg.generateKey();
        SecretKey secretKey2 = kg.generateKey();
        System.err.println(Arrays.toString(secretKey.getEncoded()));
//        System.err.println(Arrays.toString(secretKey2.getEncoded()));
        //获得密钥的二进制编码形式
//        System.out.println(secretKey.getAlgorithm() + "\t" + secretKey.getFormat()+ "\t" + secretKey.getEncoded().length);
//        System.out.println(Arrays.toString(secretKey.getEncoded()));
        return secretKey.getEncoded();
    }

    public static Key initKey2() throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        kg.init(56);
        return kg.generateKey();
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException {
        //还原密钥
        Key k = toKey(key);
        //实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行Decrypt操作
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
    public static byte[] encrypt(byte[] data, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //还原密钥
        Key k = toKey(key);
        //实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //执行Encrypt操作
        return cipher.doFinal(data);
    }

    public static byte[] encrypt2(byte[] data, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        //实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //执行Encrypt操作
        return cipher.doFinal(data);
    }


}
