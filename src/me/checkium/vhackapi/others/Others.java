package me.checkium.vhackapi.others;

import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import me.checkium.vhackapi.Utils;

public class Others {
    protected String username;
    protected String password;
    protected String userHash;
	public Others(String user, String pass, String uhash) {
		username = user;
		password = pass;
		userHash = uhash;
	}
	public PackageResult openPackage() {
		
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PackageResult result = null;
		
	
	
		JSONObject json = Utils.JSONRequest("user::::pass::::uhash", username  + "::::" + password + "::::" + userHash, "vh_openFreeBonus.php");
		if (json == null) {
			return result;
	    }

        switch(json.getInt("type")) {
         case 0:
        	//Netcoins
        	result = new PackageResult(PackageResultEnum.netcoins, json.getInt("win"));
        	 return result;
         case 1:
        	//Money
        	 result = new PackageResult(PackageResultEnum.money, json.getInt("win"));
        	 return result;
         case 2:
        	 //Upgrade
        	 switch(json.getInt("win")) {
        	 case 1:
        		 result = new PackageResult(PackageResultEnum.fw, json.getInt("lvl")); 
        		 return result;
        	 case 2:
        		 result = new PackageResult(PackageResultEnum.av, json.getInt("lvl")); 
                 return result;
        	 case 3:
        		 result = new PackageResult(PackageResultEnum.sdk, json.getInt("lvl")); 
        		 return result;
        	 case 4:
        		 result = new PackageResult(PackageResultEnum.ipsp, json.getInt("lvl")); 
        		 return result;
        	 case 5:
        		 result = new PackageResult(PackageResultEnum.spam, json.getInt("lvl")); 
        		 return result;
        	 case 6:
        		 result = new PackageResult(PackageResultEnum.scan, json.getInt("lvl")); 
        		 return result;
        	 case 7:
        		 result = new PackageResult(PackageResultEnum.adw, json.getInt("lvl")); 
        		 return result;
        	 }
         case 3:
        	  //Bot net pc
        	 result = new PackageResult(PackageResultEnum.btntpc, json.getInt("win"));
        	 return result;
         case 4:
        	 //Booster
        	 
        	 //you seem to get only one per package max.
        	 //my test had win: null and lvl: 0 in the result both times i tested it
        	 result = new PackageResult(PackageResultEnum.boost, 1);
        	 return result;
        }
	return result;
   }

}
