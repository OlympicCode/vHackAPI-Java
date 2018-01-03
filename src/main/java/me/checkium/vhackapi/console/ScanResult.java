package me.checkium.vhackapi.console;

public class ScanResult {

    protected boolean success = true;
    protected String fw = "1";
    protected String username = "???";
    protected String av = "1";
    protected String scan = "1";
    protected String sdk = "1";
    protected String spam = "1";
    protected String money = "350000";
    private String anonymous = "NO";
    private String repsuccess = "1";
    private String repfail = "1";
    private String successrate = "90";


    public ScanResult(String[] result) {
        if (result[1] == null) {
            success = false;
        } else {
            username = result[1].substring(26);
            fw = result[2].substring(26);
            av = result[3].substring(27);
            scan = result[4].substring(22);
            sdk = result[5].substring(21);
            spam = result[6].substring(22);
            money = result[7].substring(23);
            anonymous = result[9].substring(27);
            repsuccess = result[11].substring(32);
            repfail = result[12].substring(29);
            successrate = result[13].substring(39).replace("%", "");
        }
    }

    /**
     * Get some stat about the SCAN
     *
     * @param stat The stat to get, only AV, FW, MONEY, SCAN, SDK and SPAM.
     * @return String The stat.
     */
    public String getStat(ScanStats stat) {
        if (!success) {
            return null;
        }
        switch (stat) {
            case AV: {
                return av;
            }
            case FW:
                return fw;
            case MONEY:
                return money;
            case SCAN:
                return scan;
            case SDK:
                return sdk;
            case SPAM:
                return spam;
            default:
                return null;
        }
    }

    public String getTransferedIP() {
        return "";
    }


    public String getUsername() {
        if (!success) {
            return null;
        }
        return username;
    }

    public String getRepOnSuccess() {
        if (!success) {
            return null;
        }

        return repsuccess;
    }

    public String getRepOnFail() {
        if (!success) {
            return null;
        }
        return repfail;
    }

    public boolean anonymous() {
        if (!success) {
            return false;
        }
        return anonymous.contains("YES");
    }

    public int getMoney() {
        if (!success) {
            return 0;
        }
        if (money.contains("?")) {
            money = "0";
        }
        return Integer.parseInt(money);
    }

    public int getSuccessRate() {
        if (!success) {
            return 0;
        }
        if (successrate.contains("?")) {
            successrate = "0";
        }
        return Integer.parseInt(successrate);

    }
}
