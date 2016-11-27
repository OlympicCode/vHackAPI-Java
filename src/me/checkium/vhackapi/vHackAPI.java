package me.checkium.vhackapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import me.checkium.vhackapi.chat.Chat;
import me.checkium.vhackapi.console.Console;
import me.checkium.vhackapi.others.Others;
import me.checkium.vhackapi.upgrades.UpgradeManager;

public class vHackAPI {

	protected String password;
	protected String username;
	protected String userHash;

	
	 public Console getConsole() {
		 Console console = new Console(username, password, userHash);
		 return console;
	 }
	 
	 public UpgradeManager getUpgradeManager() {
		 UpgradeManager manager = new UpgradeManager(username, password, userHash);
		 return manager;
	 }		

	public String getStats(Stats stat) {
		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		JSONObject json = null;
        InputStream is;
			try {
				is = new URL(Utils.generateURL("user::::pass::::uhash", username + "::::" + password + "::::" + userHash, "vh_update.php")).openStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				String jsonText = Utils.readJson(rd);
				json = new JSONObject(jsonText);
				if (json.getString(stat.toString()) == null) {
					return null;
				}
			}  catch (IOException e) {
				e.printStackTrace();
			}
		return json.getString(stat.toString());
	}
	
	public Others getOthers() {
		Others others = new Others(username, password, userHash);
		return others;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	public Chat getChat() {
		Chat chat = new Chat();
				return chat;
	}
	public vHackAPI(String user, String pass) {
		username = user;
		password = pass;
		userHash = getStats(Stats.uhash);
		//return this;
	}
	
	@Deprecated
	public vHackAPI getAPI() {
		return this;
	}
	
	
	
	public String humanizeString(String unumanized) {
		switch (unumanized) {
		case "fw":
		    return "Firewall";
		case "av":
			return "Antivirus";
		case "ipsp":
			return "IP-Spoofing";
		case "adw":
			return "AdWare";
		case "scan":
			return "Scan";
		case "inet":
			return "Internet";
		case "money":
			return "Money";
		case "hdd":
			return "HDD";
		case "cpu":
			return "CPU";
		case "netcoins":
			return "Netcoins";
		case "ip":
			return "IP";
		case "ram":
			return "RAM";
		case "sdk":
			return "SDK";
		case "spam":
			return "Spam";
		case "bonus":
			return "Packages";
		case "elo":
			return "Rank";
		case "hash":
			return "Hash";
		case "id":
			return "ID";
		case "btntpc":
			return "Botnet PC";
	    default:
	    	return null;
		}
	}
	
}
