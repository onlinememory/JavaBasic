package pec;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Itinerary {
    public static void main(String[] args) {
        String testData = "C:/temp/iti.pec";
        testData = "C:/temp/test.txt";
//        testData = "C:/temp/iti_ca_cute.pec";
        String content = "";

        try {
            FileInputStream fis = new FileInputStream(testData);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bf = new BufferedReader(isr);
            String line = null;
            int c = 0;
            while ((line = bf.readLine()) != null) {
                System.out.println(++c + "\t" + line.getBytes(StandardCharsets.UTF_8).length + "\t" + line + "\t" + line.length());
                content += line;
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (content.indexOf("AD;") == 0) {
            content = content.substring(3);
        }

        content = content.substring(content.indexOf("#02"));
        System.out.println("Content -" + content);

        String[] lines = content.split("#");
        for (String str : lines) {
            System.out.println("- " + str);
        }
    }
}
