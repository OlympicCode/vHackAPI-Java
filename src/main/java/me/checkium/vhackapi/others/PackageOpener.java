package me.checkium.vhackapi.others;

import me.checkium.vhackapi.Utils;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class PackageOpener {
    protected String username;
    protected String password;
    private String userHash;

    public PackageOpener(String user, String pass, String uhash) {
        username = user;
        password = pass;
        userHash = uhash;
    }

    public PackageResult openPackage() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject json = Utils.JSONRequest("user::::pass::::UHASH", username + "::::" + password + "::::" + userHash, "vh_openFreeBonus.php");
        if (json == null) {
            return null;
        }

        PackageResult result;
        switch (json.getInt("type")) {
            case 0: {
                //Netcoins
                result = new PackageResult(PackageResultEnum.NETCOINS, json.getInt("win"));
                return result;
            }
            case 1: {
                //Money
                result = new PackageResult(PackageResultEnum.MONEY, json.getInt("win"));
                return result;
            }
            case 2: {
                //Upgrade
                switch (json.getInt("win")) {
                    case 1: {
                        result = new PackageResult(PackageResultEnum.FW, json.getInt("lvl"));
                        return result;
                    }
                    case 2: {
                        result = new PackageResult(PackageResultEnum.AV, json.getInt("lvl"));
                        return result;
                    }
                    case 3: {
                        result = new PackageResult(PackageResultEnum.SDK, json.getInt("lvl"));
                        return result;
                    }
                    case 4: {
                        result = new PackageResult(PackageResultEnum.IPSP, json.getInt("lvl"));
                        return result;
                    }
                    case 5: {
                        result = new PackageResult(PackageResultEnum.SPAM, json.getInt("lvl"));
                        return result;
                    }
                    case 6: {
                        result = new PackageResult(PackageResultEnum.SCAN, json.getInt("lvl"));
                        return result;
                    }
                    case 7: {
                        result = new PackageResult(PackageResultEnum.ADW, json.getInt("lvl"));
                        return result;
                    }
                }
                return null;
            }
            case 3: {
                //Bot net pc
                result = new PackageResult(PackageResultEnum.BTNTPC, json.getInt("win"));
                return result;
            }
            case 4: {
                //Booster

                //you seem to get only one per package max.
                //my test had win: null and lvl: 0 in the result both times i tested it
                result = new PackageResult(PackageResultEnum.BOOST, 1);
                return result;
            }
        }
        return null;
    }

}
