package com.jared.core.test;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-4-1
 * Time: 下午3:03
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {
        File file = new File("D:/huawei");
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    readFile(f.getAbsolutePath());
                }
            }
        }

    }

    private static void readFile(String filePath) {
        System.out.println(filePath);
        File dest = new File("D:/huawei.sql");
        File file = new File(filePath);
        if (file.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dest, true));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(line);
                    System.out.println(line);
                    bufferedWriter.flush();
                    bufferedWriter.newLine();
                }

                bufferedReader.close();
                bufferedWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
