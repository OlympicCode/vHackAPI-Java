package net.olympiccode.vhack.api.entities.console.impl;

import lombok.Getter;
import lombok.Setter;
import net.olympiccode.vhack.api.entities.console.ScannedTarget;
import net.olympiccode.vhack.api.entities.console.TransferResult;
import net.olympiccode.vhack.api.entities.impl.vHackAPIImpl;
import net.olympiccode.vhack.api.requests.Response;
import net.olympiccode.vhack.api.requests.Route;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
@Setter
public class ScannedTargetImpl implements ScannedTarget {

    int firewall;
    int antivirus;
    int spam;
    int sdk;
    int ipSpoof;
    long money;
    boolean anonymous;
    int winchance;
    int spyware;
    String username;
    int eloonwin;
    String ip;
    vHackAPIImpl api;

    public ScannedTargetImpl(vHackAPIImpl api, int fw, int av, int spam, int sdk, int ipsp, long money, boolean anonymous, int winchance, int spyware, String username, int eloonwin, String ip) {
      this.firewall = fw;
      this.antivirus = av;
      this.spam = spam;
      this.sdk = sdk;
      this.ipSpoof = ipsp;
      this.money = money;
      this.anonymous = anonymous;
      this.winchance = winchance;
      this.spyware = spyware;
      this.username = username;
      this.eloonwin = eloonwin;
      this.ip = ip;
      this.api = api;
    }

    public TransferResult transfer() {
        Route.CompiledRoute r = Route.Console.TR_TRANSFER.compile(api, ip);
        Response a = api.getRequester().getResponse(r);
        int result = -1;
        int amount = 0;
        int elo = 0;
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
        if (newmoney > 0) {
           api.user.setMoney(newmoney);
        }
        if (elo > 0) {
            api.user.setReputation(elo);
        }
        return new TransferResultImpl(result, elo, eloch, amount, newmoney);
    }

    public boolean uploadSpyware() {
        Route.CompiledRoute r = Route.Console.SPYWARE_UPLOAD.compile(api, ip);
        Response a = api.getRequester().getResponse(r);
        return a.getString().equals("0");
    }





}
