package huayin;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static void moveScannedFileToFolder(String scannedFilesFolderStr) {
        File origFolderFile = new File(scannedFilesFolderStr);
        File[] fileList = origFolderFile.listFiles();
////        System.out.println(origFolderFile.getParent());
//
        String filename = "";
//        int memberID = 0;
        Path originalPath;
        Path targetPath;
        for (File file : fileList) {
            filename = file.getName();
            if (file.isFile()) {
                originalPath = file.toPath();
                targetPath = Paths.get(origFolderFile.getParent(), filename.substring(0, filename.indexOf("-")), filename);
//                System.out.println(originalPath + "\n\t" + targetPath);
                try {
                    Files.move(originalPath, targetPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static boolean checkScannedFiles(String scannedFilesFolderStr, String dataFolderStr) {
        boolean fileStatus = false;
        List<String> memberIdList = getMemberIdList(dataFolderStr);


        File scannedFilesFile = new File(scannedFilesFolderStr);
        File[] scannedFiles = scannedFilesFile.listFiles();

        if (scannedFiles.length == 0) {
            System.err.println("No scanned files are found.");
            return fileStatus;
        }

        String memberId;
        for (File file : scannedFiles) {
            fileStatus = true;
            if (file.isFile()) {
                if ((file.getName().indexOf("-")) >= 0) {
                    memberId = file.getName().substring(0, file.getName().indexOf("-"));
                    if (!memberIdList.contains(memberId)) {
                        fileStatus = false;
                        System.err.println("Please Check : " + file);
                    }
                } else {
                    fileStatus = false;
                    System.err.println("Please Check : " + file);
                }
            }
        }
        return fileStatus;
    }

    private static List<String> getMemberIdList(String dataFolderStr) {
        File dataFolderFile = new File(dataFolderStr);
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return !file.getName().equals("0000");
            }
        };

        File[] dataFolderArray = dataFolderFile.listFiles(filter);

        List<String> dataFolder = new ArrayList<>();

        for (File file : dataFolderArray) {
            dataFolder.add(file.getName());
        }

        return dataFolder;
    }

    public static void checkOldFiles(String originalFolderStr) {

        File origFolderFile = new File(originalFolderStr);

        File[] folderList = origFolderFile.listFiles();
//        System.out.println(origFolderFile.getParent());

        String folderName = "";
        String filename = "";
        String idFile = "";
        int memberID = 0;
        for (File folder : folderList) {
            if (folder.isDirectory() && !folder.getName().equals("0000")) {
                folderName = folder.getName();

                File[] fileList = folder.listFiles();
                for (File file : fileList) {
                    filename = file.getName();
                    if (!filename.startsWith(folderName)) {
                        Path source = file.toPath();
                        try {
                            Files.move(source, source.resolveSibling(folderName + "-" + filename));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
