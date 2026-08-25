package com.nikit.minecraftandroidlauncher.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DownloadUtils {
    
    private static final OkHttpClient client = new OkHttpClient();
    private static final int BUFFER_SIZE = 8192;
    
    public interface DownloadProgress {
        void onProgress(long downloaded, long total);
        void onComplete();
        void onError(Exception e);
    }
    
    public static void downloadFile(String url, File outputFile, DownloadProgress listener) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }
            
            if (response.body() == null) {
                throw new IOException("Response body is null");
            }
            
            long total = response.body().contentLength();
            long downloaded = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            
            try (InputStream in = response.body().byteStream();
                 FileOutputStream out = new FileOutputStream(outputFile)) {
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                    downloaded += len;
                    if (listener != null) {
                        listener.onProgress(downloaded, total);
                    }
                }
            }
            
            if (listener != null) {
                listener.onComplete();
            }
        } catch (Exception e) {
            if (listener != null) {
                listener.onError(e);
            }
            throw new IOException("Download failed", e);
        }
    }
}
