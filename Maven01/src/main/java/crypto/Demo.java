package crypto;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static crypto.ByteToHex.hexStringToBytesArray;

public class Demo {
    public static void main(String[] args) {
        String input = "密";
        input = "ABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABC";
        System.out.println(input.length());
        String encoding = "UTF-8";
        System.out.println(base64Encode(input, encoding));
        System.err.println(base64Decode("5a+G", encoding));
//        try {
//            byte[] decoded = Hex.decodeHex("00A0BF");
//            System.out.println(Arrays.toString(decoded));
//            System.out.println(hexStringToBytesArray("00A0BF"));
//        } catch (DecoderException e) {
//            e.printStackTrace();
//        }

    }

    public static String base64Encode(String input, String encoding) {
        byte[] b = new byte[0];
        try {
//            b = Base64.encode(input.getBytes(encoding));
            b = org.apache.commons.codec.binary.Base64.encodeBase64(input.getBytes(encoding));
            b = org.apache.commons.codec.binary.Base64.encodeBase64(input.getBytes(encoding), true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            String out;
            out = new String(b, encoding);
            System.out.println("out : " + out);
            return out;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String base64Decode(String input, String encoding) {
        byte[] bs = new byte[0];
        String text = "";
        try {
            bs = Base64.decode(input.getBytes(encoding));
            text = new String(bs, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return text;
    }
}
