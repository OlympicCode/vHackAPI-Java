package me.checkium.vhackapi.console;

import me.checkium.vhackapi.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Console {

    protected static String pass;
    protected static String user;
    static String uHash;

    public Console(String user, String pass, String uHash) {
        Console.user = user;
        Console.pass = pass;
        Console.uHash = uHash;
    }

    public Target[] getTargets() {
        JSONObject json = Utils.JSONRequest("user::::pass::::UHASH", user + "::::" + pass + "::::" + uHash, "vh_getImg.php");
        JSONArray jarray = json.getJSONArray("data");
        Target[] targets = new Target[jarray.length()];

        for (int i = 0; i < jarray.length(); i++) {
            JSONObject tempjo = jarray.getJSONObject(i);

            try {
                targets[i] = new Target(tempjo.getString("hostname"), new NetworkImage(tempjo.getString("img")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targets;
    }
}
