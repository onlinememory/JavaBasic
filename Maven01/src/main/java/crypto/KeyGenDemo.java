package crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class KeyGenDemo {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        String keyAlgo = "DES";

        KeyGenerator kg = KeyGenerator.getInstance(keyAlgo);

        kg.init(56);

        SecretKey secretKey1 = kg.generateKey();


        byte[] k1 = secretKey1.getEncoded();
        k1 = new byte[]{-113, 67, 67, -53, 59, 109, -48, 9};
        System.out.println("secretKey1 : length " + k1.length + "\t" + Arrays.toString(k1));
        ////////
        DESKeySpec dks = new DESKeySpec(k1);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(keyAlgo);
        SecretKey secretKey2 = secretKeyFactory.generateSecret(dks);
        byte[] k2 = secretKey2.getEncoded();

        System.out.println("secretKey2 : length " + k2.length + "\t" + Arrays.toString(k2));
        System.out.println(secretKey1.equals(secretKey2));
        System.out.println(Arrays.equals(k1, k2));

        SecretKey secretKey3 = new SecretKeySpec(k1, keyAlgo);
        byte[] k3 = secretKey3.getEncoded();
        System.out.println("secretKey3 : length " + k3.length + "\t" + Arrays.toString(k3));

        System.out.println(secretKey1 == secretKey3);
        System.out.println(secretKey1.equals(secretKey3));
        System.out.println(Arrays.equals(k1, k3));

    }
}
