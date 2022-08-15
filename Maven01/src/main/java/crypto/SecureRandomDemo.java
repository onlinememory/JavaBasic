package crypto;

import java.security.SecureRandom;
import java.util.Arrays;

public class SecureRandomDemo {
    public static void main(String[] args) {
        byte[] seed = new byte[]{
                22, 25, -35, -45, 25, 98, -55, -45, 10, 35, -45, 25,
                26, -95, 25, -65, -78, -99, 85, 45, -62, 10, -0, 11,
                -35, 48, -98, 65, -32, 14, -78, 25, 36, -56, -45, -45,
                12, 15, -35, -75, 15, -14, 62, -25, 33, -45, 55, 68, -88
        };
        SecureRandom secureRandom = new SecureRandom(seed);

        byte[] out = new byte[5];
        System.out.println(secureRandom.nextInt());
        System.out.println(secureRandom.nextInt());
        System.out.println(secureRandom.nextInt());
    }
}
