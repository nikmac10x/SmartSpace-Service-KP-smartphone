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
public class SSAgent {

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

                sendPersonalData();
                initListOfRaspberry();
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

    /*
    Вызывать только в функции ssJoin
     */
    private static void initListOfRaspberry() {
        listOfRaspberry = new ArrayList<Raspberry>();

        /* Получение списка из ИП */
        Log.d(TAG, "in thread: query..");
        resp = kp.queryRDF(ns+"raspberry"+1, ns + "use", null, "uri", "literal");
        if (resp.isConfirmed()) {
            Log.d(TAG, "in thread: queryRDF isConfirmed");
            if (resp.query_results != null) {
                Log.d(TAG, "in thread: list not null");
                /* Заполнение списка */
                ArrayList<ArrayList<String>> triples = resp.query_results;
                if (!triples.isEmpty()) {
                    for(int i = 0; i < triples.size(); i++) {
                        String s = triples.get(i).get(2);
                        listOfRaspberry.add(new Raspberry(s));
                    }
                }
            }
        }
    }

    public static void sendPersonalData() {
        ArrayList<String> triple = new ArrayList<String>();
        ArrayList<ArrayList<String>> triples = new ArrayList<ArrayList<String>>();

        triple.add(ns+"raspberry"+1);
        triple.add(ns + "use");
        triple.add("127.34.32.57:900");
        triple.add("uri");
        triple.add("literal");

        triples.add(triple);
        kp.insert(triples);
        if (resp.isConfirmed()) {
            Log.d(TAG, "insert triples");
        }
    }

    public static void startTranslation() {

    }
}
