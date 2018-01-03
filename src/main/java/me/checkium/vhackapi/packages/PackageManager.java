package me.checkium.vhackapi.packages;

import me.checkium.vhackapi.Stats;
import me.checkium.vhackapi.Utils;
import me.checkium.vhackapi.VHackAPI;
import org.json.JSONObject;

public class PackageManager {
    protected String username;
    protected String password;
    protected String userHash;
    private int packageNumber;
    private VHackAPI api;

    public PackageManager(String user, String pass, String uhash, VHackAPI api) {
        username = user;
        password = pass;
        userHash = uhash;
        this.api = api;
        packageNumber = Integer.parseInt(api.getStats(Stats.BONUS));
    }

    public boolean buyPackage(Packs number) {
        JSONObject json = Utils.JSONRequest("user::::pass::::UHASH::::pack", username + "::::" + password + "::::" + userHash + "::::" + number.getPackID(), "vh_buyBonusBox.php");
        packageNumber = json.getInt("bleft");
        return json.getInt("SUCCESS") == 0;
    }

    public int getPackageNumber() {
        return packageNumber;
    }

    public MultiplePackageResult openPackages() {
        String s = Utils.stringRequest("user::::pass::::UHASH", username + "::::" + password + "::::" + userHash, "vh_openAllBonus.php");
        if ("1".equals(s)) {
            return null;
        }

        JSONObject rJson = new JSONObject(s);
        PackageResults[] rEnum = PackageResults.values();
        PackageResult[] results = new PackageResult[rEnum.length];

        for (int i = 0; i < rEnum.length; i++) {
            results[i] = new PackageResult(rEnum[i], rJson.getInt(rEnum[i].toString()));
        }

        return new MultiplePackageResult(results);
    }

    public PackageResult openPackage() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject json = Utils.JSONRequest("user::::pass::::UHASH", username + "::::" + password + "::::" + userHash, "vh_openFreeBonus.php");

        if (json == null) {
            return null;
        }

        switch (json.getInt("type")) {
            case 0: {
                //Netcoins
                return new PackageResult(PackageResults.NETCOINS, json.getInt("win"));
            }
            case 1:{
                //Money
                return new PackageResult(PackageResults.MONEY, json.getInt("win"));
            }
            case 2: {
                //Upgrade
                switch (json.getInt("win")) {
                    case 1: {
                        return new PackageResult(PackageResults.FW, json.getInt("lvl"));
                    }
                    case 2: {
                        return new PackageResult(PackageResults.AV, json.getInt("lvl"));
                    }
                    case 3: {
                        return new PackageResult(PackageResults.SDK, json.getInt("lvl"));
                    }
                    case 4: {
                        return new PackageResult(PackageResults.IPSP, json.getInt("lvl"));
                    }
                    case 5: {
                        return new PackageResult(PackageResults.SPAM, json.getInt("lvl"));
                    }
                    case 6: {
                        return new PackageResult(PackageResults.SCAN, json.getInt("lvl"));
                    }
                    case 7: {
                        return new PackageResult(PackageResults.ADW, json.getInt("lvl"));
                    }
                }
            }
            case 3: {
                //Bot net pc
                return new PackageResult(PackageResults.BTNTPC, json.getInt("win"));
            }
            case 4: {
                //Booster

                //you seem to get only one per package max.
                //my test had win: null and lvl: 0 in the result both times i tested it
                return new PackageResult(PackageResults.BOOST, 1);
            }
        }
        packageNumber = Integer.parseInt(api.getStats(Stats.BONUS));
        return null;
    }

}
