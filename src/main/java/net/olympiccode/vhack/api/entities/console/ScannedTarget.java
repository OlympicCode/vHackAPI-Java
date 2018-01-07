package net.olympiccode.vhack.api.entities.console;

public interface ScannedTarget {
    int getFw();
    int getAv();
    String getUsername();
    int getEloonwin();
    int getIpsp();
    long getMoney();
    int getSdk();
    int getSpam();
    int getSpyware();
    int getWinchance();
    boolean isAnonymous();
    TransferResult transfer();
}

