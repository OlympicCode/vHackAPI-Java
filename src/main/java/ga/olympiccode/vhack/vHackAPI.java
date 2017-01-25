package ga.olympiccode.vhack;


import org.json.JSONObject;

import ga.olympiccode.vhack.console.Console;


public class vHackAPI {
	protected String password;
	protected String username;
	protected String userHash;
	private JSONObject stats = null;
	protected boolean caching;
	 public Console getConsole() {
		 Console console = new Console(username, password, userHash);
		 return console;
	 }

	public String getStats(Stats stat) {
	Utils.delay(200);
				if(caching = true) {
					if (stats.getString(stat.toString()) == null) {
						return null;
					}
					return stats.getString(stat.toString());
				} else {
					if (stats.getString(stat.toString()) == null) {
						return null;
					}
					fetchStats();
					return stats.getString(stat.toString());
				}
			
				
		
		
	}
	
	public void fetchStats() {
		Utils.delay(200);
		stats =  Utils.JSONRequest("user::::pass::::uhash", username + "::::" + password + "::::" + userHash, "vh_update.php");
	}
	
	public vHackAPI(String user, String pass, boolean cache) {
		username = user;
		password = pass;
		userHash = getStats(Stats.uhash);
	}
}
