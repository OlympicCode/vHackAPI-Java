package net.olympiccode.vhack.api.entities.console.impl;

import lombok.Getter;
import lombok.Setter;
import net.olympiccode.vhack.api.entities.console.TransferResult;

@Getter
@Setter
public class TransferResultImpl implements TransferResult {
    private boolean success;
    private int newElo;
    private int eloObtained;
    private long newMoney;
    private long moneyObtained;

    public TransferResultImpl(int result, int elo, int eloch, int money, long newmoney) {
        success = result == 0;
        this.newElo = elo;
        this.eloObtained = eloch;
        this.moneyObtained = money;
        this.newMoney = newmoney;
    }

}
