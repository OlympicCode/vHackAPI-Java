package me.checkium.vhackapi.botnet;

import me.checkium.vhackapi.Utils;
import org.json.JSONObject;

public enum BotnetManager {

    INSTANCE;

    private static String username, password, userHash;
    static JSONObject botnetInfo;

    public static void setupBotnetManager(String username, String password, String userHash) {

        BotnetManager.username = username;
        BotnetManager.password = password;
        BotnetManager.userHash = userHash;
        getInfo();

    }

    protected static void getInfo() {
        botnetInfo = Utils.JSONRequest("user::::pass::::UHASH", username + "::::" + password + "::::" + userHash, "vh_botnetInfo.php");
    }

    public Bot getBot(int bID) {
        Bot bot = new Bot(username, password, userHash, bID);
        return bot;
    }

    public Bot[] getBots() {
        Bot[] bots = new Bot[getBotCount()];

        for (int i = 0; i < getBotCount(); i++) {
            bots[i] = getBot(i + 1);
        }

        return bots;
    }

    public int getBotCount() {
        return botnetInfo.getInt("count");
    }

    public int getStrength() {
        return botnetInfo.getInt("strength");
    }

    public boolean attack(BotnetTarget target) {
        boolean success;
        int companyNumber = target.getCompanyNumber();

        if (botnetInfo.getInt("canAtt" + companyNumber) == 1) {
            if (companyNumber == 1) {
                try {
                    Utils.JSONRequest("user::::pass::::UHASH::::cID", username + "::::" + password + "::::" + userHash + "::::" + companyNumber, "vh_attackCompany.php");
                    success = true;
                } catch (Exception e) {
                    success = false;
                }
            } else {
                try {
                    Utils.JSONRequest("user::::pass::::UHASH::::cID", username + "::::" + password + "::::" + userHash + "::::" + companyNumber, "vh_attackCompany" + companyNumber + ".php");
                    success = true;
                } catch (Exception e) {
                    success = false;
                }
            }
        } else {
            success = false;
        }

        return success;
    }

}
