package crypto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Key {
    public static void outPutFile(String str) throws IOException {
        Date date = new Date();

        String fileName = "D:" + File.separator + "key-" + date.getTime() + ".txt";
        BufferedWriter write = new BufferedWriter(new FileWriter(fileName));

        write.write(str);
        write.close();
    }

    public static List<String> inPutFile(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            List<String> temp = new ArrayList<String>();

            while ((tempString = reader.readLine()) != null) {
                temp.add(tempString);
            }

            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e1) {
            }
        }
        return null;
    }
}
