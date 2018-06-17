package ru.nikmac10x.smartservice;

import android.graphics.Bitmap;
import android.media.projection.MediaProjection;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by User on 08.05.2018.
 */
public class Sender {

    private static Socket socket;

    private static OutputStream out;


    private static final String TAG = "myLogs";

    private static Boolean openConnect(Raspberry raspberry) {
        try {
            Log.d(TAG, "create socket");
            socket = new Socket(raspberry.getRaspIp(), raspberry.getRaspPort());
            out = socket.getOutputStream();
            Log.d(TAG, "socket created");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (socket != null) {
            return true;
        }
        return false;
    }


    public static void sendScreenshot(View v) {
        try {
            Log.d(TAG, "send scrinshot");

            v.setDrawingCacheEnabled(true);
            v.buildDrawingCache(true);
            Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
            v.setDrawingCacheEnabled(false);

            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.flush();
            out.close();
            Log.d(TAG, "record");
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }



    public static void translation(final Raspberry raspberry,final View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG, "translation thread");
                if (openConnect(raspberry)) {
                    Log.d(TAG, "openConnect ok");

                    sendScreenshot(v.getRootView());
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "openConnect err");

                }
            }
        }).start();
    }



}
