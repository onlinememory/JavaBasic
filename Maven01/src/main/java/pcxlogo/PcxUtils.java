package pcxlogo;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PcxUtils {
    public static void createHexStringLogo(String filenameStr, String logoNumber, String mode) {
        Path filePath = Paths.get(filenameStr);
        Path outputFilePath = Paths.get(filenameStr + ".txt");
//        Path logoFilePath = Paths.get(filenameS)
        String hexStringPcx = pcxToHexStringPcx(filenameStr);

        String header = "";

        if (mode.equalsIgnoreCase("SITA")) {
            header = "AD;LT" + logoNumber + hexStringPcx.length();
        } else {
            header = "LT" + logoNumber + hexStringPcx.length();
        }

        hexStringPcx = header + hexStringPcx;

        try {
            Files.writeString(outputFilePath, hexStringPcx);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String pcxToHexStringPcx(String filenameStr) {
        String hexStringPcx = "";
        Path filePath = Paths.get(filenameStr);
        try {
            byte[] bytes = Files.readAllBytes(filePath);
            hexStringPcx = Hex.encodeHexString(bytes).toUpperCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hexStringPcx;
    }

    public static void bactchHexStringPcxToPcx(String filepathStr) throws IOException {
        Path folderPath = Paths.get(filepathStr, "PCX");
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }
        System.out.println(folderPath.toAbsolutePath());
//        Files.isDirectory()
        File folder = new File(filepathStr);
        File[] files = folder.listFiles();
        List<String> strings = new ArrayList<>();
//        byte[] bytes = null;
        String hexPCXString;
        Path newFilePath = null;
        for (File file : files) {
            if (file.isFile()) {
                System.out.println(hexStringPcxToPcx(folderPath, file));
            }
        }
    }

    private static String hexStringPcxToPcx(Path folderPath, File file) throws IOException {

        List<String> strings;
        Path newFilePath = null;
        String hexPCXString;

        strings = Files.readAllLines(file.toPath());
        hexPCXString = strings.get(0);
        if (hexPCXString.indexOf("AD;LT") == 0) {
            hexPCXString = hexPCXString.substring(11);
        } else if (hexPCXString.indexOf("LT") == 0) {
            hexPCXString = hexPCXString.substring(8);
        }

        if ((hexPCXString.length() % 2) != 0) {
            return "String length must be even." + file.getName();
        }
        if (!hexPCXString.substring(0, 4).equalsIgnoreCase("0A05")) {
            return "The first 4 letters must be 0A05";
        }

        newFilePath = Paths.get(folderPath.toString(), file.getName().substring(0, file.getName().lastIndexOf(".")) + ".pcx");
//                System.out.println(file.getCanonicalFile());
//                System.out.println(newFilePath);
//                System.out.println(file.getName() + " : " + hexPCXString);
        try {
            byte[] bytes = Hex.decodeHex(hexPCXString);
            Files.write(newFilePath, bytes);
        } catch (DecoderException e) {
//                    bytes = null;
//                e.printStackTrace();
            return "Check HEX String: " + e.getMessage() + newFilePath.toString();
        }
//                System.out.println(file.getName() + " : " + Arrays.toString(bytes));


        return "PCX file created : " + newFilePath.toString();
    }
}
