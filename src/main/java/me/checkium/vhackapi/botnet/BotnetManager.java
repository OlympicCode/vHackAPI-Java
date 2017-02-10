package me.checkium.vhackapi.botnet;

import org.json.JSONObject;

import me.checkium.vhackapi.Utils;

public class BotnetManager {

	protected static String username, password, userHash;
	protected static JSONObject botnetInfo;
	public BotnetManager(String username, String password, String userHash){
		
		BotnetManager.username = username;
		BotnetManager.password = password;
		BotnetManager.userHash = userHash;
		getInfo();
		
	}
	
	protected static void getInfo(){
		
		botnetInfo = Utils.JSONRequest("user::::pass::::uhash", username + "::::" + password + "::::" + userHash, "vh_botnetInfo.php");
		
	}
	public Bot getBot(int bID){
		
		Bot bot = new Bot(username, password, userHash, bID);
		return bot;
	}
	
	public Bot[] getBots(){
		
		Bot[] bots = new Bot[getBotCount()];
		for(int i = 0; i < getBotCount(); i++){
			
			bots[i] = getBot(i + 1);
			
		}
		return bots;
		
	}
	
	public int getBotCount(){
		
		return botnetInfo.getInt("count");
		
	}
	
	public int getStrength(){
		
		return botnetInfo.getInt("strength");
		
	}
	
	public boolean attack(BotnetTarget target){
		
		boolean success;
		int cN = target.getCompanyN();
		if(botnetInfo.getInt("canAtt" + cN) == 1){
			
			if(cN == 1){
				
				try{
					Utils.JSONRequest("user::::pass::::uhash::::cID", username + "::::" + password + "::::" + userHash + "::::" + cN, "vh_attackCompany.php");
					success = true;
				} catch(Exception e) {
					success = false;
				}
				
			} else {
			
				try{
					Utils.JSONRequest("user::::pass::::uhash::::cID", username + "::::" + password + "::::" + userHash + "::::" + cN, "vh_attackCompany" + cN + ".php");
					success = true;
				} catch(Exception e) {
					success = false;
				}
				
			}
			
		} else {
			
			success = false;
			
		}
		
		return success;
		
	}
	
}
