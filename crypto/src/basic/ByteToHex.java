package basic;

public class ByteToHex {
    public static String bytesArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02X", b)); // 10进制转16进制，X 表示以十六进制形式输出，02 表示不足两位前面补0输出
        }

        return sb.toString();
    }

    public static byte[] hexStringToBytesArray(String hexStr) {
        if (hexStr.length() == 0 || hexStr.length() % 2 != 0) {
            System.err.println("WRONG\nYour input is \"" + hexStr + "\" with length " + hexStr.length() + "\nThe length of Input String must be EVEN and NOT ZERO.");
            return null;
        }

        byte[] bytesArrey = new byte[hexStr.length() / 2];

        for (int i = 0; i < hexStr.length(); i += 2) {
            System.out.println((char) hexStr.charAt(i) + " " + hexStr.charAt(i + 1) + "\t" + Character.digit(hexStr.charAt(i), 16) + " " + Character.digit(hexStr.charAt(i + 1), 16));
            System.out.println((byte) ((Character.digit(hexStr.charAt(i), 16) << 4) + Character.digit(hexStr.charAt(i + 1), 16)));
        }

        return null;
    }
}
