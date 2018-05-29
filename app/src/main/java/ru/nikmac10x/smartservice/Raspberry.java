package ru.nikmac10x.smartservice;

/**
 * Created by User on 08.05.2018.
 */
public class Raspberry {
    private String raspIp;
    private int raspPort;


    private void setRaspIp(String ip) {
        this.raspIp = ip;
    }
    private void setRaspPort(String port) {
        this.raspPort = Integer.parseInt(port);
    }

    public String getRaspIp() {
        return raspIp;
    }
    public int getRaspPort() {
        return raspPort;
    }


    public Raspberry(String adrr) {
        // Получение значений ip и port из строки
        int pos = adrr.indexOf(':');
        if (pos != -1) {
            setRaspIp(adrr.substring(0, pos));
            setRaspPort(adrr.substring(pos + 1));
        }
    }

    @Override
    public String toString() {
        return raspIp + ":" + raspPort;
    }
}
