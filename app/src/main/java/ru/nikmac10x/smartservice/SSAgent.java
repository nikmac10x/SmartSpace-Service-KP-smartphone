package ru.nikmac10x.smartservice;

import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import sofia_kp.KPICore;
import sofia_kp.SIBResponse;
import sofia_kp.SSAP_XMLTools;
import sofia_kp.SSAP_sparql_response;
import sofia_kp.iKPIC_subscribeHandler2;

/**
 * Created by User on 08.05.2018.
 */
public class SSAgent {

    private static final String TAG = "myLogs";
    private static final String NS = "http://translation#";
    private static String SMARTPHONE_URL = null;
    private static String TRANSLATION_URL = null;
    private static String CHOICE_URL = NS + "choice";
    private static String STARTED_URL = NS + "start";
    private static String DISPLAYED_URL = NS + "displayed";
    private static String USE_URL = NS + "use";
    private static String HAS_URL = NS + "has";

    private static KPICore kp;
    private static SIBResponse resp;

    private static Boolean translationON = false;

    private  static ArrayList<Raspberry> listOfRaspberry;


    public static ArrayList<Raspberry> getListOfRaspberry() {
        return listOfRaspberry;
    }

    public static void ssInit(String ssip, int ssport) {
        kp = new KPICore(ssip, ssport, "X");
        SMARTPHONE_URL = NS + "smartphone1";
        TRANSLATION_URL = NS + "translation1";
    }

    public static boolean ssJoin() {

        // Поток для сетевых операций
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "in thread: kp join..");
                resp = kp.join();

                initListOfRaspberry();
                sendPersonalData();
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

    public static void ssLeave() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ArrayList<String>> r_triples = new ArrayList<ArrayList<String>>();

                //r_triples.add(createTriple(null, USE_URL, null, "url", "literal"));
                //r_triples.add(createTriple(null, DISPLAYED_URL, null, "url", "url"));
                r_triples.add(createTriple(SMARTPHONE_URL, null, null, "url", "url"));

                resp = kp.remove(r_triples);
                if (resp.isConfirmed()) {
                    Log.d(TAG, "remove triples ok");
                } else {
                    Log.d(TAG, "remove triples err");
                }

                resp = kp.leave();
                if (resp.isConfirmed()) {
                    Log.d(TAG, "kp leave..");
                } else {
                    Log.d(TAG, "kp error leave..");
                }
            }
        }).start();
    }



    /*
    Вызывать только в функции ssJoin
     */
    private static void initListOfRaspberry() {
        listOfRaspberry = new ArrayList<Raspberry>();

        /* Получение списка из ИП */
        Log.d(TAG, "in thread: query..");
        resp = kp.queryRDF(null, USE_URL, null, "uri", "literal");

        if (resp.isConfirmed()) {

            Log.d(TAG, "in thread: queryRDF isConfirmed");
            if (resp.query_results != null) {

                Log.d(TAG, "in thread: list not null");
                /* Заполнение списка */
                ArrayList<ArrayList<String>> triples = resp.query_results;

                if (!triples.isEmpty()) {
                    for(int i = 0; i < triples.size(); i++) {
                        String url = triples.get(i).get(0);
                        String adrr = triples.get(i).get(2);
                        listOfRaspberry.add(new Raspberry(url, adrr));
                    }
                }
            }
        }
    }



    public static void sendPersonalData() {

        if (UserData.isSet()) {
            ArrayList<ArrayList<String>> triples = UserData.getPersonTriples();
            triples.add(createTriple(UserData.getUserURL(), HAS_URL, SMARTPHONE_URL, "url", "url"));

            resp = kp.insert(triples);
            if (resp.isConfirmed()) {
                Log.d(TAG, "insert user triples");
            } else {
                Log.d(TAG, "insert user err triples");
            }
        }
    }

    public static void startTranslation(final Raspberry raspberry, View v) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ArrayList<String>> triplesIns = new ArrayList<ArrayList<String>>();

                triplesIns.add(createTriple(SMARTPHONE_URL, STARTED_URL, TRANSLATION_URL, "url", "url"));
                triplesIns.add(createTriple(SMARTPHONE_URL, CHOICE_URL, raspberry.getRaspUrl(), "url", "url"));

                resp = kp.insert(triplesIns);
                if (resp.isConfirmed()) {
                    Log.d(TAG, "goog ins");
                }

                ArrayList<ArrayList<String>> subTriples = new ArrayList<ArrayList<String>>();
                subTriples.add(createTriple(TRANSLATION_URL, DISPLAYED_URL, raspberry.getRaspUrl(), "url", "url"));

                //Ожидание ответа от raspberry
                int i = 10;
                while (i > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    resp = kp.queryRDF(subTriples);
                    if (resp.isConfirmed()) {
                        Log.d(TAG, "start OK");

                        translationON = true;
                        break;
                    } else {
                        Log.d(TAG, "start Err");
                    }

                    --i;
                }


            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (translationON) {
            Log.d(TAG, "begin");

            Sender.translation(raspberry, v);
        } else {
            Log.d(TAG, "end");
        }
    }

    public static ArrayList<String> createTriple(String s, String p, String o, String t_s, String t_o) {
        ArrayList<String> triple = new ArrayList<String>();

        triple.add(s);
        triple.add(p);
        triple.add(o);
        triple.add(t_s);
        triple.add(t_o);

        return triple;
    }

}
