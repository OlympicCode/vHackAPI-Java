package net.olympiccode.vhack.api.entities.console;

import net.olympiccode.vhack.api.vHackAPI;

public interface ScannedTarget {
    int getFirewall();
    int getAntivirus();
    String getUsername();
    int getEloonwin();
    int getIpSpoof();
    long getMoney();
    int getSdk();
    int getSpam();
    int getSpyware();
    int getWinchance();
    boolean isAnonymous();
    TransferResult transfer();
    boolean uploadSpyware();
    String getIp();
    vHackAPI getApi();
}

