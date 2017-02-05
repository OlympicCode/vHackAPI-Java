package me.checkium.vhackapi.botnet;

import org.json.JSONArray;
import org.json.JSONObject;

import me.checkium.vhackapi.Utils;

public class Bot {

	protected int bID;
	protected String username, password, userHash;
	private JSONObject botInfo;
	
	public Bot(String username, String password, String userHash, int bID){
		
		this.bID = bID;
		this.username = username;
		this.password = password;
		this.userHash = userHash;
		BotnetManager.getInfo();
		getBotInfo();
		
	}
	protected void getBotInfo(){
		
		JSONArray botsInfo = BotnetManager.botnetInfo.getJSONArray("data");
		JSONObject botInfo = botsInfo.getJSONObject(--bID);
		this.botInfo = botInfo;
		
	}
	
	public int getBID(){
		
		return botInfo.getInt("bID");
		
	}
	
	public int getLevel(){
		
		return botInfo.getInt("bLVL");
		
	}
	
	public int getPrice(){
		
		return botInfo.getInt("bPRICE");
		
	}
	
	public void update(){
		
		Utils.JSONRequest("user::::pass::::uhash::::bID", username + "::::" + password + "::::" + userHash + "::::" + bID, "vh_upgradeBotnet.php");
		getBotInfo();
		
	}
	
	
	
}
