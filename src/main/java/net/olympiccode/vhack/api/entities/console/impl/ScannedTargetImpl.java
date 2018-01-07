package net.olympiccode.vhack.api.entities.console.impl;

import net.olympiccode.vhack.api.entities.console.ScannedTarget;
import net.olympiccode.vhack.api.entities.console.TransferResult;
import net.olympiccode.vhack.api.entities.impl.vHackAPIImpl;
import net.olympiccode.vhack.api.requests.Response;
import net.olympiccode.vhack.api.requests.Route;
import org.json.JSONException;
import org.json.JSONObject;

public class ScannedTargetImpl implements ScannedTarget {

    int fw;
    int av;
    int spam;
    int sdk;
    int ipsp;
    long money;
    boolean anonymous;
    int winchance;
    int spyware;
    String username;
    int eloonwin;
    String ip;
    vHackAPIImpl api;

    public ScannedTargetImpl(vHackAPIImpl api, int fw, int av, int spam, int sdk, int ipsp, long money, boolean anonymous, int winchance, int spyware, String username, int eloonwin, String ip) {
      this.fw = fw;
      this.av = av;
      this.spam = spam;
      this.sdk = sdk;
      this.ipsp = ipsp;
      this.money = money;
      this.anonymous = anonymous;
      this.winchance = winchance;
      this.spyware = spyware;
      this.username = username;
      this.eloonwin = eloonwin;
      this.ip = ip;
      this.api = api;
    }

    public int getFw() {
        return fw;
    }

    public int getAv() {
        return av;
    }

    public String getUsername() {
        return username;
    }

    public int getEloonwin() {
        return eloonwin;
    }

    public int getIpsp() {
        return ipsp;
    }

    public long getMoney() {
        return money;
    }

    public int getSdk() {
        return sdk;
    }

    public int getSpam() {
        return spam;
    }

    public int getSpyware() {
        return spyware;
    }

    public int getWinchance() {
        return winchance;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public TransferResult transfer() {
        Route.CompiledRoute r = Route.Console.TR_TRANSFER.compile(api, ip);
        Response a = api.getRequester().getResponse(r);
        System.out.println(a.getString());
        int result = -1;
        int amount = -1;
        int elo = -1;
        int eloch = -1;
        long newmoney = -1;
        try {
            JSONObject object = new JSONObject(a.getString());
            result = Integer.parseInt(object.getString("result"));
            try {
                newmoney = object.getLong("newmoney");
            } catch (JSONException e) {
                newmoney = 0;
            }
            try {
                amount = object.getInt("amount");
                elo = object.getInt("elo");
                eloch = object.getInt("eloch");
            } catch (JSONException ignored) {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new TransferResultImpl(result, elo, eloch, amount, newmoney);
    }






}
