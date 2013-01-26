package com.jared.core.common.util;

import java.io.*;
import java.util.ArrayList;

/**
 * @author
 * @desc 用于在deploy时获取全部assets以下资源的SVNrevision
 */
public class SvnUtil {
    public static void dump(String path, PrintWriter writer) throws IOException {

        File file = new File(path);

        if (file.isDirectory()) {
            String[] children = file.list();
            for (String aChildren : children) {
                if (file.getName().indexOf(".svn") != -1) {
                    continue;
                }
                dump(path + "/" + aChildren, writer);
            }
        } else {
            Runtime rt = Runtime.getRuntime();
            Process p;
            try {
                p = rt.exec("svn info " + file.getAbsolutePath());
                StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), writer);
                outputGobbler.start();
                p.waitFor();
            } catch (Exception e) {
                //System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FileWriter outFile = new FileWriter("revisions.properties");
        PrintWriter writer = new PrintWriter(outFile);
        for (String arg : args) {
            dump(arg, writer);
        }
        writer.close();
    }
}

class StreamGobbler extends Thread {
    InputStream is;
    String type;
    PrintWriter out;

    StreamGobbler(InputStream is, PrintWriter writer) {
        this.is = is;
        this.out = writer;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            ArrayList<String> result = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            out.println(result.get(0).substring(result.get(0).lastIndexOf("assets") + 7) + "=" + result.get(5).split(":")[1].trim());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}