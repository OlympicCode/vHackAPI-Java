package me.checkium.vhackapi.Spyware;

import me.checkium.vhackapi.Utils;
import me.checkium.vhackapi.console.Connection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpywareManager {

    protected String password;
    protected String username;
    protected String userHash;

    public SpywareManager(String user, String pass, String uHash) {
        username = user;
        password = pass;
        userHash = uHash;
    }

    public SpywareUploadResult uploadSpywareTo(Connection node) {
        String returnString = Utils.stringRequest("user::::pass::::UHASH::::target", username + "::::" + password + "::::" + userHash + "::::" + node.getIP(), "vh_spywareUpload.php");
        return new SpywareUploadResult(returnString);
    }

    public boolean removeSpywareFrom(Connection node) {
        String returnString = Utils.stringRequest("user::::pass::::UHASH::::target", username + "::::" + password + "::::" + userHash + "::::" + node.getIP(), "vh_removeSpywareRemote.php");
        JSONObject d = new JSONObject(returnString);
        return d.getInt("result") == 0;
    }

    public ArrayList<Spyware> getActiveSpyware() {
        ArrayList<Spyware> list = new ArrayList<>();
        JSONObject json = Utils.JSONRequest("user::::pass::::UHASH", username + "::::" + password + "::::" + userHash, "vh_spywareInfo.php");
        JSONArray jsonArray = json.getJSONArray("data");

        for (int i = 0; i < jsonArray.length(); i++) {
            Spyware spyware = new Spyware(jsonArray.getJSONObject(i));
            list.add(spyware);
        }

        return list;
    }
}
