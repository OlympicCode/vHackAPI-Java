package me.checkium.vhackapi.botnet;

import me.checkium.vhackapi.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Bot {

    private int bID;
    private String username, password, userHash;
    private JSONObject botInfo;

    public Bot(String username, String password, String userHash, int bID) {

        this.bID = bID;
        this.username = username;
        this.password = password;
        this.userHash = userHash;
        getBotInfo();

    }

    protected void getBotInfo() {

        JSONArray botsInfo = BotnetManager.botnetInfo.getJSONArray("data");
        JSONObject botInfo = botsInfo.getJSONObject(bID - 1);
        this.botInfo = botInfo;

    }

    public int getBID() {

        return botInfo.getInt("bID");

    }

    public int getLevel() {

        return botInfo.getInt("bLVL");

    }

    public int getPrice() {

        return botInfo.getInt("bPRICE");

    }

    public void refreshBotnetInfo() {

        BotnetManager.getInfo();

    }

    public void update() {

        Utils.JSONRequest("user::::pass::::UHASH::::bID", username + "::::" + password + "::::" + userHash + "::::" + bID, "vh_upgradeBotnet.php");
        refreshBotnetInfo();
        getBotInfo();

    }


}
