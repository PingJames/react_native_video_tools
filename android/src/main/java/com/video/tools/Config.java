package com.video.tools;

/*
* By Jorge E. Hernandez (@lalongooo) 2015
* */

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/*
* Static class to define general configuration values of the application
* */
public class Config {

    /**
     * Application root directory. All media files wi'll be stored here.
     */
    public static final String VIDEO_COMPRESSOR_APPLICATION_DIR_NAME = "BNQ";

    /**
     * Application folder for video files
     */
    public static final String VIDEO_COMPRESSOR_COMPRESSED_VIDEOS_DIR = "/Compressed Videos/";

    /**
     * Application folder for video files
     */
    public static final String VIDEO_COMPRESSOR_TEMP_DIR = "/Temp/";

    public static class FileUtils {

        private static final String TAG = "FileUtils";

        public static void createApplicationFolder() {
            File f = new File(Environment.getExternalStorageDirectory(), File.separator + VIDEO_COMPRESSOR_APPLICATION_DIR_NAME);
            f.mkdirs();
            f = new File(Environment.getExternalStorageDirectory(), File.separator + VIDEO_COMPRESSOR_APPLICATION_DIR_NAME + VIDEO_COMPRESSOR_COMPRESSED_VIDEOS_DIR);
            f.mkdirs();
            f = new File(Environment.getExternalStorageDirectory(), File.separator + VIDEO_COMPRESSOR_APPLICATION_DIR_NAME + VIDEO_COMPRESSOR_TEMP_DIR);
            f.mkdirs();
        }

        public static File saveTempFile(String fileName, Context context, Uri uri) {

            File mFile = null;
            ContentResolver resolver = context.getContentResolver();
            InputStream in = null;
            FileOutputStream out = null;

            try {
                in = resolver.openInputStream(uri);

                mFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + VIDEO_COMPRESSOR_APPLICATION_DIR_NAME + VIDEO_COMPRESSOR_TEMP_DIR, fileName);
                out = new FileOutputStream(mFile, false);
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                out.flush();
            } catch (IOException e) {
                Log.e(TAG, "", e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        Log.e(TAG, "", e);
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.e(TAG, "", e);
                    }
                }
            }
            return mFile;
        }

        public static String getCompressDir() {
            return Environment.getExternalStorageDirectory().getPath() + File.separator + VIDEO_COMPRESSOR_APPLICATION_DIR_NAME + VIDEO_COMPRESSOR_TEMP_DIR;
        }

        public static File saveTempFile(String fileName, Bitmap bitmap) {
            File mFile = null;
            FileOutputStream out = null;
            try {
                mFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + VIDEO_COMPRESSOR_APPLICATION_DIR_NAME + VIDEO_COMPRESSOR_TEMP_DIR, fileName);
                out = new FileOutputStream(mFile, false);

                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
            } catch (IOException e) {
                Log.e(TAG, "", e);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.e(TAG, "", e);
                    }
                }
            }
            return mFile;
        }
    }
}
