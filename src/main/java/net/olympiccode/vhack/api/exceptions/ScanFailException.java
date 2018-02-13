package net.olympiccode.vhack.api.exceptions;

public class ScanFailException extends Exception {
    private String hostname;
    private Reason reason;

    public ScanFailException(String hostname, Reason reason) {
        super("Failed to scan \"" + hostname + "\" (" + reason.toString() + ").");
        this.reason = reason;
        this.hostname = hostname;
    }


    public Reason getReason() {
        return reason;
    }

    public String getHostname() {
        return hostname;
    }

    public enum Reason {
        WATCHED_BY_FBI, UPGRADE_SDK;
    }
}
