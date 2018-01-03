package me.checkium.vhackapi;

import me.checkium.vhackapi.Spyware.SpywareManager;
import me.checkium.vhackapi.botnet.BotnetManager;
import me.checkium.vhackapi.chat.Chat;
import me.checkium.vhackapi.cluster.ClusterManager;
import me.checkium.vhackapi.console.Console;
import me.checkium.vhackapi.packages.PackageManager;
import me.checkium.vhackapi.upgrades.UpgradeManager;
import org.json.JSONObject;

public class VHackAPI {

    protected String password;
    protected String username;
    private String userHash;
    private JSONObject stats = null;


    public VHackAPI(String user, String pass) {
        username = user;
        password = pass;
        userHash = getStats(Stats.UHASH);
        //return this;
    }

    public Console getConsole() {
        return new Console(username, password, userHash);
    }

    public UpgradeManager getUpgradeManager() {
        return new UpgradeManager(username, password, userHash);
    }

    public SpywareManager getSpywareManager() {
        return new SpywareManager(username, password, userHash);
    }

    public BotnetManager getBotnetManager() {
        BotnetManager.setupBotnetManager(username, password, userHash);
        return BotnetManager.INSTANCE;
    }

    public ClusterManager getClusterManager() {
        return new ClusterManager(username, password, userHash);
    }

    public PackageManager getPackageManager() {
        return new PackageManager(username, password, userHash, this);
    }

    public String getStats(Stats stat) {
        fetchStats();
        return stats.getString(stat.toString());
    }

    public String getCachedStats(Stats stat) {
        if (stats == null) {
            fetchStats();
        }
        return stats.getString(stat.toString());
    }

    private void fetchStats() {
        JSONObject stats1 = Utils.JSONRequest("user::::pass::::UHASH", username + "::::" + password + "::::" + userHash, "vh_update.php");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Utils.JSONRequest("user::::pass::::UHASH", username + "::::" + password + "::::" + userHash, "vh_update.php");
        stats = stats1;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Chat getChat() {
        return new Chat();
    }

    @Deprecated
    public VHackAPI getAPI() {
        return this;
    }


    public String humanizeString(String unumanized) {
        switch (unumanized) {
            case "FW":
                return "Firewall";
            case "AV":
                return "Antivirus";
            case "IPSP":
                return "IP-Spoofing";
            case "ADW":
                return "AdWare";
            case "SCAN":
                return "Scan";
            case "INET":
                return "Internet";
            case "MONEY":
                return "Money";
            case "HDD":
                return "HDD";
            case "CPU":
                return "CPU";
            case "NETCOINS":
                return "Netcoins";
            case "IP":
                return "IP";
            case "RAM":
                return "RAM";
            case "SDK":
                return "SDK";
            case "SPAM":
                return "Spam";
            case "BONUS":
                return "Packages";
            case "ELO":
                return "Rank";
            case "HASH":
                return "Hash";
            case "ID":
                return "ID";
            case "BTNTPC":
                return "Botnet PC";
            case "BOOST":
                return "Booster";
            default:
                return null;
        }
    }

}
