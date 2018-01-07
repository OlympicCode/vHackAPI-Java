package net.olympiccode.vhack.api.entities.console;

public interface TransferResult {
    boolean isSuccess();
    int getEloObtained();
    long getMoneyObtained();
    long getNewMoney();
    int getNewElo();
}
