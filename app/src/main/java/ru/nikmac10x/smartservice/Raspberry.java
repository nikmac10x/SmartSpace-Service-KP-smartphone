package ru.nikmac10x.smartservice;

/**
 * Created by User on 08.05.2018.
 */
public class Raspberry {
    private int raspPort;
    private String raspIp;

    public String getRaspIp() {
        return raspIp;
    }

    public void setRaspIp(String ip) {
        this.raspIp = ip;
    }

    public int getRaspPort() {
        return raspPort;
    }

    public void setRaspPort(String port) {
        this.raspPort = Integer.parseInt(port);
    }

    public Raspberry(String adrr) {
        int index = adrr.indexOf(':');

        setRaspIp(adrr.substring(0, index-1));
        setRaspPort(adrr.substring(index+1));
    }
}
