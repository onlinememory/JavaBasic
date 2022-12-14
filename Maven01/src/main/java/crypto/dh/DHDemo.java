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

    //???????????????????????????
    public static final String KEY_ALGORITHM = "DH";

    /**
     * ???????????????????????????????????????????????????
     * ??????DES???DESede???AES??????
     */
    public static final String SECRET_ALGORITHM = "AES";

    /**
     * ????????????
     * DH???????????????????????????1024
     * ?????????????????????64????????????????????????512??????1024????????????
     */
    private static final int KEY_SIZE = 512;

    //??????
    private static final String PUBLIC_KEY = "DHPublicKey";
    //??????
    private static final String PRIVATE_KEY = "DHPrivateKey";

    /**
     * ?????????????????????
     *
     * @return Map????????????Map
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws NoSuchAlgorithmException {
        //???????????????????????????
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //???????????????????????????
        keyPairGenerator.initialize(KEY_SIZE);
        //???????????????
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //????????????
        DHPublicKey dhPublicKey = (DHPublicKey) keyPair.getPublic();
        System.out.println("DHPublicKey : " + Arrays.toString(dhPublicKey.getEncoded()));

        //????????????
        DHPrivateKey dhPrivateKey = (DHPrivateKey) keyPair.getPrivate();
        System.out.println("DHPrivateKey : " + Arrays.toString(dhPrivateKey.getEncoded()));

        //?????????????????????Map???
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, dhPublicKey);
        keyMap.put(PRIVATE_KEY, dhPrivateKey);
//        System.out.println(keyMap.get(PUBLIC_KEY));
//        System.out.println(keyMap.get(PRIVATE_KEY));
        return keyMap;
    }

    /**
     * ?????????????????????
     *
     * @param key ????????????
     * @return Map ????????????Map
     * @throws Exception
     */
    public static Map<String, Object> initKey(byte[] key) throws
            NoSuchAlgorithmException,
            InvalidKeySpecException,
            InvalidAlgorithmParameterException {
        //??????????????????
        //??????????????????
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
        //?????????????????????
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //????????????
//        DHParameterSpec dhParameterSpec = DHParameterSpec;
        DHPublicKey publicKey = (DHPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);

        System.out.println("New DHPublicKey : " + Arrays.toString(publicKey.getEncoded()));

        //?????????????????????????????????
        DHParameterSpec dhParameterSpec = publicKey.getParams();
        //???????????????????????????
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
        //???????????????????????????
        keyPairGenerator.initialize(dhParameterSpec);
        //???????????????
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        //????????????
        DHPublicKey dhPublicKey = (DHPublicKey) keyPair.getPublic();
        //????????????
        DHPrivateKey dhPrivateKey = (DHPrivateKey) keyPair.getPrivate();
        //?????????????????????Map???
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, dhPublicKey);
        keyMap.put(PRIVATE_KEY, dhPrivateKey);
        return keyMap;
    }

    /**
     * ??????
     *
     * @param data ???????????????
     * @param key  ??????
     * @return byte[]????????????
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        //??????????????????
        SecretKey secretKey = new SecretKeySpec(key, SECRET_ALGORITHM);
        //????????????
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    /**
     * ?????????br???
     *
     * @param data ???????????????
     * @param key  ??????
     * @return byte[]????????????
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        //??????????????????
        SecretKey secretKey = new SecretKeySpec(key, SECRET_ALGORITHM);
        //????????????
        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    /**
     * ????????????
     *
     * @param publicKey  ??????
     * @param privateKey ??????
     * @return byte[]????????????
     * @throws Exception
     */
    public static byte[] getSecretKey(byte[] publicKey, byte[] privateKey) throws Exception {
        //?????????????????????
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //???????????????
        //??????????????????
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(privateKey);
        //????????????
        PublicKey pubKey = keyFactory.generatePublic(x509EncodedKeySpec);
        //???????????????
        //??????????????????
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        //????????????
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //?????????
        KeyAgreement keyAgreement = KeyAgreement.getInstance(keyFactory.getAlgorithm());
        //?????????
        keyAgreement.init(priKey);
        keyAgreement.doPhase(pubKey, true);
        //??????????????????
        SecretKey secretKey = keyAgreement.generateSecret(SECRET_ALGORITHM);

        return secretKey.getEncoded();
    }

    /**
     * ????????????
     *
     * @param keyMap ??????Map
     * @return byte[]??????
     * @throws Exception
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * ????????????
     *
     * @param keyMap ??????Map
     * @return byte[] ??????
     * @throws Exception
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

}
