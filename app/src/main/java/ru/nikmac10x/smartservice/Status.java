package ru.nikmac10x.smartservice;

public class Status {
    private static int currentStatus = R.string.ss_not_connection_status;

    public static int getCurrentStatus() {
        return currentStatus;
    }

    public static void setStatus(int status) {
        currentStatus = status;
    }
}
