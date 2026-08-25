package com.nikit.minecraftandroidlauncher.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {
    
    private static final int BUFFER_SIZE = 8192;
    
    public static void extractZip(File zipFile, File extractDir) throws IOException {
        if (!extractDir.exists()) {
            extractDir.mkdirs();
        }
        
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                File entryFile = new File(extractDir, entry.getName());
                
                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    entryFile.getParentFile().mkdirs();
                    extractFile(zipIn, entryFile);
                }
                zipIn.closeEntry();
            }
        }
    }
    
    private static void extractFile(ZipInputStream zipIn, File file) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while ((len = zipIn.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
        }
    }
    
    public static boolean isValidZipFile(File file) {
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(file))) {
            return zipIn.getNextEntry() != null;
        } catch (IOException e) {
            return false;
        }
    }
}
