package ru.nikmac10x.smartservice;

import java.util.ArrayList;

import sofia_kp.KPICore;

/**
 * Created by User on 08.05.2018.
 */
public class SSAgent {
    private KPICore kp;

    public void join() {

    }

    public void leave() {

    }

    public ArrayList<Raspberry> getListOfRaspberry() {
        ArrayList<Raspberry> listOfRaspberry = new ArrayList<Raspberry>();

        /* Здесь должно быть получение списка из ИП */

        listOfRaspberry.add(new Raspberry("127.0.0.1:11111"));
        listOfRaspberry.add(new Raspberry("127.0.0.1:11112"));
        listOfRaspberry.add(new Raspberry("127.0.0.1:11113"));

        return listOfRaspberry;
    }

    public void sendPersonalData() {

    }

    public void startTranslation() {

    }

    public SSAgent(String SIBHost, int SIBPort) {
        kp = new KPICore(SIBHost, SIBPort,"X");
        
    }
}
