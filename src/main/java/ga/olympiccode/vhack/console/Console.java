package ga.olympiccode.vhack.console;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ga.olympiccode.vhack.Utils;

public class Console {

	protected String password;
	protected String username;
	protected String userHash;
	
	public Console(String user, String pass, String uHash) {
		username = user;
		password = pass;
		userHash = uHash;
		//return this;
	}
	
	/**
	 * 
	 * Get IP from console
	 * 
	 * @param global Use global?
	 * @param attacked Get already attacked IPs?
	 */
	public IP getIP(boolean global) throws JSONException {
		Utils.delay(200);
		String result = null;
		JSONObject json = new JSONObject(Utils.JSONRequest("user::::pass::::uhash::::global", username + "::::" + password + "::::" + userHash + "::::" + (global?"1":"0"), "vh_network.php"));
		JSONArray JSONArray = json.getJSONArray("data");
		result = JSONArray.getJSONObject(0).getString("ip");
		return new IP(result, password, username);
	}
	
	/**
	 * 
	 * Get IPs from console
	 * 
	 * @param number How many IPs?
	 * @param global Use global?
	 * @param attacked Get already attacked IPs?
	 */
	public List<IP> getIPs(int number, boolean global) throws JSONException {
		List<IP> result = new ArrayList<IP>();
		JSONObject json = new JSONObject(Utils.JSONRequest("user::::pass::::uhash::::global", username + "::::" + password + "::::" + userHash + "::::" + (global?"1":"0"), "vh_network.php"));
		JSONArray JSONArray = json.getJSONArray("data");
		for(int i = 0; 0 <= number; i++) {
			result.add(new IP(JSONArray.getJSONObject(i).getString("ip"), password, username));
		}
		return result;
	}

}
