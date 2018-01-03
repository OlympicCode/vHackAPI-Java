package me.checkium.vhackapi.console;

import me.checkium.vhackapi.Spyware.SpywareUploadResult;
import me.checkium.vhackapi.Utils;
import org.json.JSONObject;

public class Connection {
    private String IP;
    private boolean success = true;
    private String username;
    private String firewallLevel;
    private String antiVirusLevel;
    private String scanLevel;
    private String sdkLevel;
    private String spamLevel;
    private String money;
    private String anonymous;
    private String successRep;
    private String failRep;
    private String successRate;

    public Connection(String[] result, String ip) {
        this.IP = ip;

        if (result.length == 1) {
            success = false;
            return;
        }

        if (result[1] == null) {
            success = false;
        } else {
            username = result[1].substring(26);
            firewallLevel = result[2].substring(26);
            antiVirusLevel = result[3].substring(27);
            scanLevel = result[4].substring(22);
            sdkLevel = result[5].substring(21);
            spamLevel = result[6].substring(22);
            money = result[7].substring(23);
            anonymous = result[9].substring(27);
            successRep = result[11].substring(32);
            failRep = result[12].substring(29);
            successRate = result[13].substring(39);
        }
    }

    public SpywareUploadResult spywareUpload() {
        String returnString = Utils.stringRequest("user::::pass::::UHASH::::target", Console.user + "::::" + Console.pass + "::::" + Console.uHash + "::::" + IP, "vh_spywareUpload.php");
        return new SpywareUploadResult(returnString);
    }

    public TransferResult trojanTransfer() {
        JSONObject json = Utils.JSONRequest("user::::pass::::UHASH::::target", Console.user + "::::" + Console.pass + "::::" + Console.uHash + "::::" + IP, "vh_trTransfer.php");
        return new TransferResult(json, IP);
    }

    public void clearLogs() {
        Utils.JSONRequest("user::::pass::::UHASH::::target", Console.user + "::::" + Console.pass + "::::" + Console.uHash + "::::" + IP, "vh_clearAccessLog.php");
    }

    public String getUsername() {
        if (!success) {
            return null;
        }
        return username;
    }


    public Integer getFirewallLevel() {
        if (!success) {
            return null;
        }
        try {
            return Integer.parseInt(firewallLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public Integer getAntiVirusLevel() {
        if (!success) {
            return null;
        }
        try {
            return Integer.valueOf(antiVirusLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public Integer getScanLevel() {
        if (!success) {
            return null;
        }
        try {
            return Integer.valueOf(scanLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public Integer getSdkLevel() {
        if (!success) {
            return null;
        }
        try {
            return Integer.valueOf(sdkLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public Integer getSpamLevel() {
        if (!success) {
            return null;
        }
        try {
            return Integer.valueOf(spamLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public Integer getMoney() {
        if (!success) {
            return null;
        }
        try {
            return Integer.valueOf(money);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public boolean isAnonymous() {
        if (!success) {
            return false;
        }
        return "YES".equals(anonymous);
    }


    public Integer getSuccessRep() {
        if (!success) {
            return null;
        }
        try {
            return Integer.valueOf(successRep);
        } catch (NumberFormatException er) {
            return null;
        }
    }


    public Integer getFailRep() {
        if (!success) {
            return null;
        }
        try {
            return Integer.valueOf(failRep);
        } catch (NumberFormatException er) {
            return null;
        }
    }


    public Integer getSuccessRate() {
        if (!success) {
            return null;
        }
        try {
            return Integer.valueOf(successRate.replace("%", ""));
        } catch (NumberFormatException er) {
            return null;
        }
    }

    public String getIP() {
        return IP;
    }

    public boolean getSuccess() {
        return success;
    }
    /*

    private val SUCCESS = result[1] != null
    val username = result[1]?.substring(26)
    val firewallLevel = result[2]!!.substring(26).toInt()
    val antiVirusLevel = result[3]!!.substring(27).toInt()
    val scanLevel = result[4]!!.substring(22).toInt()
    val sdkLevel = result[5]!!.substring(21).toInt();
    val spamLevel = result[6]!!.substring(22).toInt();
    val MONEY = result[7]!!.substring(23).toInt();
    val anonymous = result[9]!!.substring(27) == "YES";
    val successRep = result[11]!!.substring(32).toInt();
    val failRep = result[12]!!.substring(29).toInt();
    val successRate = result[13]!!.substring(39).replace("%","").toInt();
     */
}
