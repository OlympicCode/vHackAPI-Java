package ga.olympiccode.vhack.console;

import org.json.JSONException;
import org.json.JSONObject;

public class TransferResult {

    protected boolean success;
    protected int moneyamount;
    protected int newmoney; /* Cash Balance */
    protected int rep;
    protected String Ip;

    public TransferResult(JSONObject result, String IP) throws JSONException {
        Ip = IP;
        try {
        	@SuppressWarnings("unused")
			int d = result.getInt("eloch");
        	success = true;
        } catch (Exception e) {
        	success = false;
        }
        if (!success) return;
        try {
            moneyamount = result.getInt("amount");
            } catch (JSONException e) {
            System.out.println(result);
            e.printStackTrace();
        }
        /* Player Cash Balance */
        if (!success) return;
        try {
            newmoney = result.getInt("newmoney");
        } catch (JSONException e) {
            System.out.println(result);
            e.printStackTrace();
        }
       
            rep = result.getInt("eloch");
         
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
    
    /* getNewMoney returns Player Cash Balance */
    public int getNewMoney() { 
        return newmoney;
    }

    public int getRep() {
        return rep;
    }

}
