package huayin;

import java.io.File;

public class MoveScannedFiles {
    public static void main(String[] args) {


        String dataFolderStr = "C:/MyFiles/HuaYin/学校管理系统/学生档案/";
//        FileUtils.checkOldFiles(dataFolderStr);

        String scannedFilesFolderStr = "C:/MyFiles/HuaYin/学校管理系统/学生档案/0000";
        System.out.println(FileUtils.checkScannedFiles(scannedFilesFolderStr, dataFolderStr));

//        FileUtils.moveScannedFileToFolder(scannedFilesFolderStr);

    }
}
