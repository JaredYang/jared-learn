package com.jared.core.io;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 13-5-24
 * Time: 下午6:42
 * To change this template use File | Settings | File Templates.
 */
public class FileUtil {

    public static void main(String[] args) {

    }

    public static void readFileFolder(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println(file.getName());
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File f : files) {
                        System.out.println(f.getName());
                        readFileFolder(f.getAbsolutePath());
                    }
                }
            } else {
                printFileContent(file.getAbsolutePath());
            }
        }
    }


    private static void printFileContent(String filePath) {
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new java.io.FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeFile(String sourceFilePath, String destFilePath) {
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath);
        BufferedReader reader = null;
        BufferedWriter writer = null;
        if (sourceFile.exists() && destFile.exists()) {
            try {
                reader = new BufferedReader(new FileReader(sourceFile));
                writer = new BufferedWriter(new FileWriter(destFile, true));
                String line = null;
                if ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
                writer.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeResource(reader, writer);
            }
        }
    }


    private void closeResource(BufferedReader reader, BufferedWriter writer) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
