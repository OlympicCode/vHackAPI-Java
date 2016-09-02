package me.checkium.vhackapi.console;

import org.json.JSONException;
import org.json.JSONObject;

public class TransferResult {

    protected boolean success;
    protected int moneyamount;
    protected int repgained;
    protected int replost;
    protected String Ip;

    public TransferResult(JSONObject result, String IP) throws JSONException {
        Ip = IP;
        success = result.getString("result").contains("0");
        if (!success) return;
        try {
            moneyamount = result.getInt("amount");
        } catch (JSONException e) {
            System.out.println(result);
            e.printStackTrace();
        }
        if (Integer.toString(result.getInt("eloch")).contains("-")) {
            replost = result.getInt("eloch");
            repgained = 0;
        } else {
            replost = 0;
            repgained = result.getInt("eloch");
        }
    }

    public boolean getSuccess() {
        return success;
    }

    public String getTarget() {
        return Ip;
    }

    public int getMoneyAmount() {
        return moneyamount;
    }

    public int getRepGained() {
        return repgained;
    }

    public int getRepLost() {
        return replost;
    }
}
