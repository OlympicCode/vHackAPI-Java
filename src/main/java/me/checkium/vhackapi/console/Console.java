package me.checkium.vhackapi.console;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.checkium.vhackapi.Utils;

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
	public String getIP(boolean attacked, boolean global) throws JSONException {
		String result = null;
		JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::global", username + "::::" + password + "::::" + userHash + "::::" + (global?"1":"0"), "vh_network.php");
		JSONArray jSONArray = json.getJSONArray("data");
		result = jSONArray.getJSONObject(0).getString("ip");
		return result;
	}
	
	/**
	 * 
	 * Get IPs from console
	 * 
	 * @param number How many IPs?
	 * @param global Use global?
	 * @param attacked Get already attacked IPs?
	 */
	public ArrayList<String> getIPs(int number, boolean attacked, boolean global) throws JSONException {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> temporary = new ArrayList<String>();
		if (number > 10) {
			for (int i = 10; i <= number + 9; i = i + 10) {
				JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::global", username + "::::" + password + "::::" + userHash + "::::" + (global?"1":"0"), "vh_network.php");
				JSONArray jSONArray = json.getJSONArray("data");
				for (int j = 0; j <= jSONArray.length() - 1; j++) {
					JSONObject ip = jSONArray.getJSONObject(j);
					temporary.add(ip.getString("ip"));
				}
				
			}
			for (int k = 0; k <= number; k++) {
				result.add(temporary.get(k));
			}
		} else {
			JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::global", username + "::::" + password + "::::" + userHash + "::::" + (global?"1":"0"), "vh_network.php");
			JSONArray jSONArray = json.getJSONArray("data");
			for (int k = 0; k < number; k++) {
				result.add(jSONArray.getJSONObject(k).getString("ip"));
			}
		}
		return result;
	}

	public ScannedNode scanIP(String ip) {
        ScannedNode result = null;

		System.out.println("scanning " + ip);

        try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String resultString = Utils.StringRequest("user::::pass::::target", username + "::::" + password + "::::" + ip, "vh_scan.php");
        String[] tempParsedResultString = parseScanResult(Utils.StringRequest("user::::pass::::target", username + "::::" + password + "::::" + ip, "vh_scan.php"));
        result = new ScannedNode(parseScanResult(Utils.StringRequest("user::::pass::::target", username + "::::" + password + "::::" + ip, "vh_scan.php")));
        result.setIP(ip);

        return result;
    }
	
    public ArrayList<ScannedNode> scanIPs(List<String> ips) {
        ArrayList<ScannedNode> array = new ArrayList<>();

        ips.forEach(ip -> array.add(scanIP(ip)));

        return array;
    }

    public TransferResult transferTrojanTo(ScannedNode node) throws JSONException {
		System.out.println("transfering " + node.getIP());
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::target", username + "::::" + password + "::::" + userHash + "::::" + node.getIP(), "vh_trTransfer.php");

        return new TransferResult(json, node.getIP());
    }
	
	public ArrayList<TransferResult> transferTrojansTo(ArrayList<ScannedNode> nodes) throws JSONException {
		ArrayList<TransferResult> array = new ArrayList<TransferResult>();

		nodes.forEach(node -> array.add(new TransferResult(Utils.JSONRequest("user::::pass::::uhash::::target", username + "::::" + password + "::::" + userHash + "::::" + node.getIP(), "vh_trTransfer.php"), node.getIP())));

		return array;
	}

	private String[] parseScanResult(String input) {
		return (new BufferedReader(new StringReader(input))).lines().toArray(String[]::new);
	}
	
}
