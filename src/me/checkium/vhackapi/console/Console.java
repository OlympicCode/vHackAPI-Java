package me.checkium.vhackapi.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import me.checkium.vhackapi.Utils;

public class Console {

	protected String password;
	protected String username;
	
	public Console(String user, String pass) {
		username = user;
		password = pass;
		//return this;
	}
	
	/**
	 * 
	 * Get IPs from console
	 * 
	 * @param number How many IPs?
	 * @param global Use global?
	 * @param attacked Get already attacked IPs?
	 */
	public ArrayList<String> getIPs(int number, boolean attacked, boolean global){
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> temporary = new ArrayList<String>();
		int globali = 0;
		if (global) {
			globali = 1;
		}
		if (number > 10) {
			for (int i = 10; i <= number + 9; i = i + 10) {
				JSONObject json = Utils.JSONRequest("user::::pass::::global", username + "::::" + password + "::::" + Integer.toString(globali), "vh_network.php");
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
			JSONObject json = Utils.JSONRequest("user::::pass::::global", username + "::::" + password + "::::" + Integer.toString(globali), "vh_network.php");
			JSONArray jSONArray = json.getJSONArray("data");
			for (int k = 0; k < number; k++) {
				result.add(jSONArray.getJSONObject(k).getString("ip"));
			}
		}
		return result;
	}

	
	
	public ArrayList<ScanResult> scanIPs(ArrayList<String> ips){
		InputStream is;
		BufferedReader rd;
		ArrayList<ScanResult> array = new ArrayList<ScanResult>();
		ScanResult result;
		try {
			for (int j = 0; j < ips.size(); j++) {
		
				is = new URL(Utils.generateURL("user::::pass::::target",username + "::::" + password + "::::" + ips.get(j), "vh_scan.php")).openStream();
				rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			    result = new ScanResult(ReadBigStringIn(rd));
			    array.add(result);
		    	try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return array;
	}
	
	

	
	public ArrayList<TransferResult> trTransferIPs(ArrayList<String> ips) {
		ArrayList<TransferResult> array = new ArrayList<TransferResult>();
		TransferResult result;
		for (int j = 0; j < ips.size(); j++) {

			JSONObject json = Utils.JSONRequest("user::::pass::::target", username + "::::" + password + "::::" + ips.get(j), "vh_trTransfer.php");
			result = new TransferResult(json, ips.get(j));
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
