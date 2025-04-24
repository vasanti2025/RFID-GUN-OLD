package com.loyalstring.database;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DatabaseHelper {
    private static final String DB_NAME = "loyalstring.db";
    private static final String DB_PATH = "/data/data/com.loyalstring/databases/";

    // Call this method in an appropriate place in your app (e.g., when app starts)
    public static void replaceDatabase(Context context) {
//        File externalDbFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "loyalstrings.db");
        File externalDbFile = new File("/storage/self/primary/Download/database/loyalstrings.db");

        File internalDbFile = new File(DB_PATH + DB_NAME);

        try {
            // Check if external file exists
            if (externalDbFile.exists()) {
                copyFile(externalDbFile, internalDbFile);
                System.out.println("Database replaced successfully!");
            } else {
                System.out.println("External database file does not exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }
    }
}
