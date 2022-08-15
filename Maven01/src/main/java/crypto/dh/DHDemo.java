package crypto.dh;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jcajce.spec.DHDomainParameterSpec;

import javax.crypto.*;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DHDemo {

    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException {
        Map<String, Object> keyMap1 = null;
        byte[] publicKey1 = new byte[0];
        byte[] privateKey1;

        try {
            keyMap1 = initKey();
            publicKey1 = getPublicKey(keyMap1);
            privateKey1 = getPrivateKey(keyMap1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String,Object> keyMap2=initKey(publicKey1);
//        System.out.print("Enter to Continue:");
//        char i = 0;
//        try {
//            i = (char)System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Yout Enter Char is:" + i);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        Map<String, Object> keyMap2 = null;
//        byte[] publicKey2 = new byte[0];
//        byte[] privateKey2;
//
//        try {
//            keyMap1 = initKey();
//            publicKey2 = getPublicKey(keyMap1);
//            privateKey2 = getPrivateKey(keyMap1);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("publicKey1 : " + Arrays.toString(publicKey1));
//        System.out.println("publicKey2 : " + Arrays.toString(publicKey2));
//
//        System.out.println(Arrays.equals(publicKey1, publicKey2));
//
//        System.out.println("privateKey1 : " + Arrays.toString(privateKey1));
//        System.out.println(Base64.encodeBase64String(publicKey1));
//        System.out.println(Base64.encodeBase64String(privateKey1));
    }

    //非对称加密密钥算法
    public static final String KEY_ALGORITHM = "DH";

    /**
     * 本地密钥算法，即对称加密密钥算法，
     * 可选DES、DESede和AES算法
     */
    public static final String SECRET_ALGORITHM = "AES";

    /**
     * 密钥长度
     * DH算法默认密钥长度为1024
     * 密钥长度必须是64的倍数，其范围在512位到1024位之间。
     */
    private static final int KEY_SIZE = 512;

    //公钥
    private static final String PUBLIC_KEY = "DHPublicKey";
    //私钥
    private static final String PRIVATE_KEY = "DHPrivateKey";

    /**
     * 初始化甲方密钥
     *
     * @return Map甲方密钥Map
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws NoSuchAlgorithmException {
        //实例化密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥对生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //甲方公钥
        DHPublicKey dhPublicKey = (DHPublicKey) keyPair.getPublic();
        System.out.println("DHPublicKey : " + Arrays.toString(dhPublicKey.getEncoded()));

        //甲方私钥
        DHPrivateKey dhPrivateKey = (DHPrivateKey) keyPair.getPrivate();
        System.out.println("DHPrivateKey : " + Arrays.toString(dhPrivateKey.getEncoded()));

        //将密钥对存储在Map中
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, dhPublicKey);
        keyMap.put(PRIVATE_KEY, dhPrivateKey);
//        System.out.println(keyMap.get(PUBLIC_KEY));
//        System.out.println(keyMap.get(PRIVATE_KEY));
        return keyMap;
    }

    /**
     * 初始化乙方密钥
     *
     * @param key 甲方公钥
     * @return Map 乙方密钥Map
     * @throws Exception
     */
    public static Map<String, Object> initKey(byte[] key) throws
            NoSuchAlgorithmException,
            InvalidKeySpecException,
            InvalidAlgorithmParameterException {
        //解析甲方公钥
        //转换公钥材料
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //产生公钥
//        DHParameterSpec dhParameterSpec = DHParameterSpec;
        DHPublicKey publicKey = (DHPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);

        System.out.println("New DHPublicKey : " + Arrays.toString(publicKey.getEncoded()));

        //由甲方公钥构建乙方密钥
        DHParameterSpec dhParameterSpec = publicKey.getParams();
        //实例化密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
        //初始化密钥对生成器
        keyPairGenerator.initialize(dhParameterSpec);
        //产生密钥对
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        //乙方公钥
        DHPublicKey dhPublicKey = (DHPublicKey) keyPair.getPublic();
        //乙方私钥
        DHPrivateKey dhPrivateKey = (DHPrivateKey) keyPair.getPrivate();
        //将密钥对存储在Map中
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, dhPublicKey);
        keyMap.put(PRIVATE_KEY, dhPrivateKey);
        return keyMap;
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[]加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        //生成本地密钥
        SecretKey secretKey = new SecretKeySpec(key, SECRET_ALGORITHM);
        //数据加密
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密＜br＞
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[]解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        //生成本地密钥
        SecretKey secretKey = new SecretKeySpec(key, SECRET_ALGORITHM);
        //数据解密
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    /**
     * 构建密钥
     *
     * @param publicKey  公钥
     * @param privateKey 私钥
     * @return byte[]本地密钥
     * @throws Exception
     */
    public static byte[] getSecretKey(byte[] publicKey, byte[] privateKey) throws Exception {
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(privateKey);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509EncodedKeySpec);
        //初始化私钥
        //密钥材料转换
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        //产生私钥
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //实例化
        KeyAgreement keyAgreement = KeyAgreement.getInstance(keyFactory.getAlgorithm());
        //初始化
        keyAgreement.init(priKey);
        keyAgreement.doPhase(pubKey, true);
        //生成本地密钥
        SecretKey secretKey = keyAgreement.generateSecret(SECRET_ALGORITHM);

        return secretKey.getEncoded();
    }

    /**
     * 取得私钥
     *
     * @param keyMap 密钥Map
     * @return byte[]私钥
     * @throws Exception
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * 取得公钥
     *
     * @param keyMap 密钥Map
     * @return byte[] 公钥
     * @throws Exception
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

}
