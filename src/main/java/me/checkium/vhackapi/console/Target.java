package me.checkium.vhackapi.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.json.JSONObject;

import me.checkium.vhackapi.Utils;

public class Target {

	private String ip;
	private NetworkImage img;
	private int firewallLevel;
	private boolean isWatched;
	private String hostname;
	
	public Target(String hostname, NetworkImage img){
		
		this.img = img;
		this.hostname = hostname;
		resolveHostname();
		firewallLevel = img.getFirewallLevel();
		isWatched = img.checkForAnonymity();
		
		
			
	}
	
	public NetworkImage getNetworkImage(){
		
		return img;
		
	}
	
	public boolean isWatchedByFBI(){
		
		return isWatched;
		
	}
	
	@Deprecated
	public int getFirewallLevel(){
		
		//Coming soon
		if(!isWatched){
			
			return firewallLevel;
			
		} else {
			
			return 0;
			
		}
		
	}
	
	public String getIP(){
		
		return ip;
		
	}
	
	public void resolveHostname(){
		
		ip = Utils.StringRequest("user::::pass::::uhash::::hostname", Console.user + "::::" + Console.pass + "::::" + Console.uHash + "::::" + this.hostname , "vh_getIpByHostname.php");
		
	}
	
	public int bruteforcePassword(){
		
		JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::target", Console.user + "::::" + Console.pass + "::::" + Console.uHash + "::::" + this.ip, "vh_vulnScan.php");
		try {
			PasswordImageHelper helper = new PasswordImageHelper(json);
			int result = helper.getIDOfRightImage();
			return result;
		} catch (IOException e) {
		}
		return 0;
		
	}
	
	public Connection connect(){
		
		int decision = bruteforcePassword();
		String s = Utils.StringRequest("user::::pass::::uhash::::decision", Console.user + "::::" + Console.pass + "::::" + Console.uHash + "::::" + decision,  "vh_createConnection.php");
		return new Connection((new BufferedReader(new StringReader(s))).lines().toArray(String[]::new),ip);
		
	}
	
	
	
}
