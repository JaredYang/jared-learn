package com.jared.core.io.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created with IntelliJ IDEA.
 * User: junde.yang
 * Date: 14-4-8
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class FileCopy {

    public static void copyFile(String srcFilePath,String destFilePath){
        try {
            FileInputStream fileInputStream = new FileInputStream(srcFilePath);
            FileChannel fileChannelIn = fileInputStream.getChannel();
            FileOutputStream fileOutputStream = new FileOutputStream(destFilePath);
            FileChannel fileChannelOut = fileOutputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (true){
                byteBuffer.clear();
                int r = fileChannelIn.read(byteBuffer);
                if(r == -1){
                    break;
                }
                byteBuffer.flip();
                fileChannelOut.write(byteBuffer);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
