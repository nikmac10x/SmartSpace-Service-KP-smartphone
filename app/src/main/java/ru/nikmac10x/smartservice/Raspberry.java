package ru.nikmac10x.smartservice;

/**
 * Created by User on 08.05.2018.
 */
public class Raspberry {
    private int raspPort; //Порт raspberry
    private String raspIp; //IP-адресс raspberry

    public String getRaspIp() {
        return raspIp;
    }

    private void setRaspIp(String ip) {
        this.raspIp = ip;
    }

    public int getRaspPort() {
        return raspPort;
    }

    private void setRaspPort(String port) {
        this.raspPort = Integer.parseInt(port);
    }

    public Raspberry(String adrr) {
        int index = adrr.indexOf(':');

        setRaspIp(adrr.substring(0, index));
        setRaspPort(adrr.substring(index+1));
    }


    //Переопределение метода toString для отображения списка
    @Override
    public String toString() {
        return "ip: " + raspIp + ":" + "port: " + raspPort;
    }
}
