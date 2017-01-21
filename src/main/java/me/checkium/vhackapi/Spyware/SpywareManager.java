package me.checkium.vhackapi.Spyware;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import me.checkium.vhackapi.Utils;
import me.checkium.vhackapi.console.ScannedNode;

public class SpywareManager {

	protected String password;
	protected String username;
	protected String userHash;
	
	public SpywareManager(String user, String pass, String uHash) {
		username = user;
		password = pass;
		userHash = uHash;
	}
	
	public SpywareUploadResult uploadSpywareTo(ScannedNode node)
	{
		String returnString = Utils.StringRequest("user::::pass::::uhash::::target", username + "::::" + password + "::::" + userHash + "::::" + node.getIP(), "vh_spywareUpload.php");
		return new SpywareUploadResult(returnString);
	}
	
	public boolean removeSpywareFrom(ScannedNode node)
	{
		String returnString = Utils.StringRequest("user::::pass::::uhash::::target", username + "::::" + password + "::::" + userHash + "::::" + node.getIP(), "vh_removeSpywareRemote.php");
		JSONObject d = new JSONObject(returnString);
		if (d.getInt("result") == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<Spyware> getActiveSpyware()
	{
		ArrayList<Spyware> list = new ArrayList<>();
		JSONObject json = Utils.JSONRequest("user::::pass::::uhash", username + "::::" + password + "::::" + userHash, "vh_spywareInfo.php");
		JSONArray jsonArray = json.getJSONArray("data");
		for (int i = 0; i < jsonArray.length(); i++)
		{
			Spyware spyware = new Spyware(jsonArray.getJSONObject(i));
			list.add(spyware);
		}
		return list;
	}
}
