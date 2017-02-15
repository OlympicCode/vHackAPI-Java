package me.checkium.vhackapi.packages;

import org.json.JSONObject;

import me.checkium.vhackapi.Stats;
import me.checkium.vhackapi.Utils;
import me.checkium.vhackapi.vHackAPI;

public class PackageManager {
    protected String username;
    protected String password;
    protected String userHash;
    private int packageNumber;
    private vHackAPI api;
    
	public PackageManager(String user, String pass, String uhash, vHackAPI api) {
		username = user;
		password = pass;
		userHash = uhash;
		this.api = api;
		packageNumber = Integer.valueOf(api.getStats(Stats.bonus));
	}
	
	public boolean buyPackage(Packs number){
		
		JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::pack", username + "::::" + password + "::::" + userHash + "::::" + number.getPackID(), "vh_buyBonusBox.php");
		packageNumber = json.getInt("bleft");
		if(json.getInt("success") == 0){
			
			return false;
			
		} else {
			return true;
		}
		
	}
	
	public int getPackageNumber(){
		
		return packageNumber;
		
	}
	
	public MultiplePackageResult openPackages(){
		
		String s = Utils.StringRequest("user::::pass::::uhash", username  + "::::" + password + "::::" + userHash, "vh_openAllBonus.php");
		if (s == "1"){
			
			return null;
			
		}
			
	    JSONObject rJson = new JSONObject(s);
		PackageResults[] rEnum = PackageResults.values();
		PackageResult[] results = new PackageResult[rEnum.length];
		for(int i = 0; i < rEnum.length; i++){
				
			results[i] = new PackageResult(rEnum[i],rJson.getInt(rEnum[i].toString()));
				
		}
		return new MultiplePackageResult(results);
		
	}
	
	public PackageResult openPackage() {
		
		PackageResult result = null;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		JSONObject json = Utils.JSONRequest("user::::pass::::uhash", username  + "::::" + password + "::::" + userHash, "vh_openFreeBonus.php");
		if (json == null) {
			return result;
	    }

        switch(json.getInt("type")) {
         case 0:
        	//Netcoins
        	result = new PackageResult(PackageResults.netcoins, json.getInt("win"));
        	 return result;
         case 1:
        	//Money
        	 result = new PackageResult(PackageResults.money, json.getInt("win"));
        	 return result;
         case 2:
        	 //Upgrade
        	 switch(json.getInt("win")) {
        	 case 1:
        		 result = new PackageResult(PackageResults.fw, json.getInt("lvl")); 
        		 return result;
        	 case 2:
        		 result = new PackageResult(PackageResults.av, json.getInt("lvl")); 
                 return result;
        	 case 3:
        		 result = new PackageResult(PackageResults.sdk, json.getInt("lvl")); 
        		 return result;
        	 case 4:
        		 result = new PackageResult(PackageResults.ipsp, json.getInt("lvl")); 
        		 return result;
        	 case 5:
        		 result = new PackageResult(PackageResults.spam, json.getInt("lvl")); 
        		 return result;
        	 case 6:
        		 result = new PackageResult(PackageResults.scan, json.getInt("lvl")); 
        		 return result;
        	 case 7:
        		 result = new PackageResult(PackageResults.adw, json.getInt("lvl")); 
        		 return result;
        	 }
         case 3:
        	  //Bot net pc
        	 result = new PackageResult(PackageResults.btntpc, json.getInt("win"));
        	 return result;
         case 4:
        	 //Booster
        	 
        	 //you seem to get only one per package max.
        	 //my test had win: null and lvl: 0 in the result both times i tested it
        	 result = new PackageResult(PackageResults.boost, 1);
        	 return result;
        }
		packageNumber = Integer.valueOf(api.getStats(Stats.bonus));
        return result;
   }

}
