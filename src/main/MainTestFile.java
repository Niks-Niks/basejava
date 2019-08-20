package main;

import java.io.File;

public class MainTestFile {
    public static void main(String[] args) {
        File file = new File("./src/main");

        for (File out : file.listFiles()) {
            if (out.isFile()) {
                System.out.println("File-> " + out.getName());
                System.out.println("------------------------");
            } else {
                System.out.println("Name directory-> " + out.getName());
                System.out.println("----------");
                File[] files = out.listFiles();
                for (File FileOut : files) {
                    System.out.println("Directory-> " + FileOut.getName());
                }
                System.out.println("------------------------");
            }
        }
    }
}
