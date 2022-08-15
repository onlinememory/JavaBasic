package dcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PectabUtils {
    public static void dataCheck(String filenameStr) {
        Path filePath = Paths.get(filenameStr);

        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
            StringBuilder stringBuilder = new StringBuilder();
            for (String line : lines) {
                stringBuilder.append(line);
            }
            String oneLine = stringBuilder.toString();
            oneLine = oneLine.toUpperCase();
            if (oneLine.indexOf("AD;") == 0) {
                System.out.println("Start with \"AD;\".");
                if (oneLine.indexOf("PT") == 0 || oneLine.indexOf("PT") == 3) {

                    System.out.println("This is a SITA CUTE ATB PECtab file.");
                    atbPecTabAnalyze(oneLine);

                }
                return;
            } else if (oneLine.indexOf("CP") == 1) {
                System.out.println("This is ATB data file.");
            } else if (oneLine.indexOf("PT") == 0 || oneLine.indexOf("PT") == 3) {
                System.out.println("This is ATB PECtab file.");
            }

        } catch (IOException e) {
            if (Files.isReadable(filePath)) {
                System.out.println("File \"" + filenameStr + "\" is readable.");
            }
            System.out.println("Please change PEC file to UTF-8 encoding.");
            return;
        }
    }

    public static void atbPecTabAnalyze(String dataLine) {
        String headerSITA = "AD;";
        dataLine = dataLine.replace(headerSITA, "");
        String headerLine = dataLine.substring(0, dataLine.indexOf("#01"));
        String bodyLine = dataLine.substring(dataLine.indexOf("#01"));
        System.out.println(headerSITA);
        System.out.println(headerLine);
        System.out.println(bodyLine);
        System.out.println(dataLine.equals(headerLine + bodyLine));
        String[] items = bodyLine.split("#");
        for (String item : items) {
            System.out.println("#" + item);
        }
    }
}
