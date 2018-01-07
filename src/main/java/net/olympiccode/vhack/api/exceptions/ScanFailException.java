package net.olympiccode.vhack.api.exceptions;

public class ScanFailException extends Exception {
    private String ip;
    private boolean watched;

    public ScanFailException(String ip, boolean watched) {
        super("Failed to scan " + ip + ".");
        this.watched = watched;
        this.ip = ip;
    }


    public boolean isWatched() {
        return watched;
    }

    public String getIp() {
        return ip;
    }
}
