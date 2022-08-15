package basic;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MDDemo {
    public static void main(String[] args) {
        byte[] input = "sha".getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(input));

        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        sha.update(input);

        byte[] output = sha.digest();
        System.out.println(Arrays.toString(output));

//        sha.update(input);
        output = sha.digest();
        System.out.println(Arrays.toString(output));

//        sha.update(input);
        output = sha.digest();
        System.out.println(Arrays.toString(output));

    }
}
