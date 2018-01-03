package me.checkium.vhackapi.botnet;

public enum BotnetTarget {

    KFMSolutions(1),
    VHACKSERVER(2),
    NETCOINBANK(3);

    private final int companyNumber;

    BotnetTarget(int companyNumber) {
        this.companyNumber = companyNumber;
    }

    int getCompanyNumber() {
        return companyNumber;
    }
}
