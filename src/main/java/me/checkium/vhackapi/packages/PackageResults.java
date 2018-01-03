package me.checkium.vhackapi.packages;

public enum PackageResults {

    MONEY("MONEY"),
    NETCOINS("NETCOINS"),
    AV("AV"),
    FW("FW"),
    IPSP("IPSP"),
    BTNTPC("pcs"),
    SDK("SDK"),
    SPAM("SPAM"),
    SCAN("SCAN"),
    ADW("ADW"),
    BOOST("BOOST");

    String toString;

    PackageResults(String toString) {

        this.toString = toString;

    }

    public String toString() {

        return this.toString;

    }

}
