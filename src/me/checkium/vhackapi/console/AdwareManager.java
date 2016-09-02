package me.checkium.vhackapi.console;

import me.checkium.vhackapi.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class AdwareManager {

    protected String password;
    protected String username;

    public AdwareManager(String pass, String user) {
        password = pass;
        username = user;
    }

    public boolean uploadAdware(String target) {

        URLConnection in;
        try {
            in = new URL(Utils.generateURL("user::::pass::::target", username + "::::" + password + "::::" + target, "vh_adwareUpload.php")).openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader((in.getInputStream())));
            String line = br.readLine();
            return line.contains("0");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeAdware(String target) {
        @SuppressWarnings("unused")
        URLConnection in;
        try {
            in = new URL(Utils.generateURL("user::::pass::::target", username + "::::" + password + "::::" + target, "vh_removeAdwareRemote.php")).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader((in.getInputStream())));
            String line = br.readLine();
            JSONObject json = new JSONObject(line);
            return json.getInt("result") == 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }


    public HashMap<String, Integer> getAdwares() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HashMap<String, Integer> map = new HashMap<>();
        JSONObject json = Utils.JSONRequest("user::::pass", username + "::::" + password, "vh_adwareInfo.php");
        if (json.getInt("remote") <= 0) {
            return null;
        }
        JSONArray array = json.getJSONArray("data");
        for (int i = 0; i <= array.length() - 1; i++) {
            String IP = array.getJSONObject(i).getString("ip");
            Integer earn = array.getJSONObject(i).getInt("earn");
            map.put(IP, earn);
        }

        return map;
    }
}
