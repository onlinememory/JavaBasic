package crypto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MessageDigestDemo {

    public static void main(String[] args) {
        String alg = "MD5";
        String input = "A";

        stream(alg, input);
    }

    public static void stream(String algorithmus, String originStr) {
        try {
            MessageDigest md=MessageDigest.getInstance(algorithmus);

            byte[] origBytes = originStr.getBytes(StandardCharsets.UTF_8);

            md.update(origBytes);
            System.out.println(Arrays.toString(md.digest()));

            DigestInputStream dis = new DigestInputStream(new ByteArrayInputStream(origBytes),md);
            dis.read(origBytes,0,origBytes.length);

            System.out.println(Arrays.toString(dis.getMessageDigest().digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


