package com.nikit.minecraftandroidlauncher.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.nikit.minecraftandroidlauncher.utils.DownloadUtils;
import com.nikit.minecraftandroidlauncher.utils.FileUtils;

import java.io.File;

public class DownloadService extends Service {
    
    private static final String TAG = "DownloadService";
    private final IBinder binder = new LocalBinder();
    
    public interface DownloadListener {
        void onDownloadStarted(String fileName);
        void onProgress(long downloaded, long total);
        void onDownloadComplete(String fileName);
        void onDownloadFailed(Exception e);
    }
    
    public class LocalBinder extends Binder {
        DownloadService getService() {
            return DownloadService.this;
        }
    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    
    public void downloadVersion(String url, String fileName, DownloadListener listener) {
        new Thread(() -> {
            try {
                if (listener != null) {
                    listener.onDownloadStarted(fileName);
                }
                
                File versionsDir = FileUtils.getVersionsDir(this);
                File outputFile = new File(versionsDir, fileName);
                
                DownloadUtils.downloadFile(url, outputFile, new DownloadUtils.DownloadProgress() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        if (listener != null) {
                            listener.onProgress(downloaded, total);
                        }
                    }
                    
                    @Override
                    public void onComplete() {
                        if (listener != null) {
                            listener.onDownloadComplete(fileName);
                        }
                    }
                    
                    @Override
                    public void onError(Exception e) {
                        if (listener != null) {
                            listener.onDownloadFailed(e);
                        }
                    }
                });
                
                Log.d(TAG, "Download complete: " + fileName);
                
            } catch (Exception e) {
                Log.e(TAG, "Download failed", e);
                if (listener != null) {
                    listener.onDownloadFailed(e);
                }
            }
        }).start();
    }
    
    public void downloadMod(String url, String fileName, DownloadListener listener) {
        new Thread(() -> {
            try {
                if (listener != null) {
                    listener.onDownloadStarted(fileName);
                }
                
                File tempFile = new File(getCacheDir(), fileName);
                
                DownloadUtils.downloadFile(url, tempFile, new DownloadUtils.DownloadProgress() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        if (listener != null) {
                            listener.onProgress(downloaded, total);
                        }
                    }
                    
                    @Override
                    public void onComplete() {
                        if (listener != null) {
                            listener.onDownloadComplete(fileName);
                        }
                    }
                    
                    @Override
                    public void onError(Exception e) {
                        if (listener != null) {
                            listener.onDownloadFailed(e);
                        }
                    }
                });
                
                Log.d(TAG, "Mod download complete: " + fileName);
                
            } catch (Exception e) {
                Log.e(TAG, "Mod download failed", e);
                if (listener != null) {
                    listener.onDownloadFailed(e);
                }
            }
        }).start();
    }
}
