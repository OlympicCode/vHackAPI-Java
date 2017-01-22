package me.checkium.vhackapi.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        InputStream is;
        BufferedReader rd;
        ScannedNode result = null;
        try {
            is = new URL(Utils.generateURL("user::::pass::::target", username + "::::" + password + "::::" + ip, "vh_scan.php")).openStream();
            rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String[] temp = ReadBigStringIn(rd);
            result = new ScannedNode(temp);
            result.setIP(ip);
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException|IOException e) {
            e.printStackTrace();
        }
        return result;
    }
	
    public List<ScannedNode> scanIPs(List<String> ips) {
        InputStream is;
        BufferedReader rd;
        List<ScannedNode> array = new ArrayList<>();
        ScannedNode result;

        for (String ip : ips) {
            try {
                is = new URL(Utils.generateURL("user::::pass::::target", username + "::::" + password + "::::" + ip, "vh_scan.php")).openStream();
                rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String[] temp = ReadBigStringIn(rd);
                result = new ScannedNode(temp);
                result.setIP(ip);
                array.add(result);
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException|IOException e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    public TransferResult transferTrojanTo(ScannedNode node) throws JSONException {
        JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::target", username + "::::" + password + "::::" + userHash + "::::" + node.getIP(), "vh_trTransfer.php");
        return new TransferResult(json, node.getIP());
    }
	
	public ArrayList<TransferResult> transferTrojansTo(ArrayList<ScannedNode> nodes) throws JSONException {
		ArrayList<TransferResult> array = new ArrayList<TransferResult>();
		TransferResult result;
		for (int j = 0; j < nodes.size(); j++) {
			JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::target", username + "::::" + password + "::::" + userHash + "::::" + nodes.get(j).getIP(), "vh_trTransfer.php");
			result = new TransferResult(json, nodes.get(j).getIP());
			array.add(result);
		}
		return array;
	}
	
	public String[] ReadBigStringIn(BufferedReader buffIn) throws IOException {
	    String[] string = new String[14];
	    int i = 0;
	    String line;
	    while( (line = buffIn.readLine()) != null) {
	      string[i] = line;
	      i++;
	    }
	    return string;
	}
	
}
