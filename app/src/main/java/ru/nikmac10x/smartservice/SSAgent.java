package ru.nikmac10x.smartservice;

import android.graphics.drawable.AnimationDrawable;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import sofia_kp.KPICore;
import sofia_kp.SIBResponse;
import sofia_kp.SSAP_XMLTools;

/**
 * Created by User on 08.05.2018.
 */
public class SSAgent extends Thread {

    private static final String TAG = "myLogs";
    private static final String ns = "http://translation#";

    private static KPICore kp;
    private static SIBResponse resp;

    private  static ArrayList<Raspberry> listOfRaspberry;


    public static ArrayList<Raspberry> getListOfRaspberry() {
        return listOfRaspberry;
    }

    public static void ssInit(String ssip, int ssport) {
        kp = new KPICore(ssip, ssport, "X");
    }

    public static boolean ssJoin() {

        // Поток для сетевых операций
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "in thread: kp join..");
                resp = kp.join();
            }
        });


        try {
            Log.d(TAG, "thread start..");
            thread.start();

            Log.d(TAG, "thread join..");
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "isConfirmed..");
        if (!resp.isConfirmed()) {
            return false;
        }
        Log.d(TAG, "kp join isConfirmed..");
        return true;
    }

    public static void leave() {
        resp = kp.leave();
        if (resp.isConfirmed()) {
            Log.d(TAG, "kp leave..");
        } else {
            Log.d(TAG, "kp error leave..");
        }
    }

    public static void setListOfRaspberry() {
        final ArrayList<Raspberry> listOfRaspberry = new ArrayList<Raspberry>();

        /* Получение списка из ИП */

        // Поток для работы с сетью
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "in thread: query..");
                resp = kp.queryRDF(ns + "subject", ns + "predicate", null, "uri", "literal");
                if (resp.isConfirmed()) {
                    Log.d(TAG, "in thread: queryRDF isConfirmed");
                    if (resp.query_results != null) {
                        Log.d(TAG, "in thread: list not null");
                        /* Заполнение списка */


                    }
                }
            }
        });

        try {
            Log.d(TAG, "thread start..");
            thread.start();
            Log.d(TAG, "thread join..");
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "return..");


    }

    public static void sendPersonalData() {

    }

    public static void startTranslation() {

    }
}
