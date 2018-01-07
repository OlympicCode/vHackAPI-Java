package net.olympiccode.vhack.api.entities.console.impl;

import net.olympiccode.vhack.api.entities.console.TransferResult;

public class TransferResultImpl implements TransferResult {
    private boolean success;
    private int elo;
    private int eloch;
    private long newmoney;
    private long money;

    public TransferResultImpl(int result, int elo, int eloch, int money, long newmoney) {
        success = result == 0;
        this.elo = elo;
        this.eloch = eloch;
        this.money = money;
        this.newmoney = newmoney;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getEloObtained() {
        return eloch;
    }

    public long getMoneyObtained() {
        return money;
    }

    public long getNewMoney() {
        return newmoney;
    }

    public int getNewElo() {
        return elo;
    }
}
