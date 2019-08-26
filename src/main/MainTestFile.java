package main;

import java.io.File;

public class MainTestFile {
    public static void main(String[] args) {
        File file = new File("./src/");
        PrintFileDir(file, "");
    }


    private static void PrintFileDir(File file, String enter) {
        for (File FileOut : file.listFiles()) {
            if (FileOut.isFile()) {
                System.out.println(enter + "File-> " + FileOut.getName());
            } else {
                System.out.println(enter + "Directory-> " + FileOut.getName());
                PrintFileDir(FileOut, enter.concat("   "));
            }
        }
    }
}
