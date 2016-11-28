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
	
	public String getIP(boolean attacked, boolean global){
		String result = null;
		int globali = 0;
		if (global) {
			globali = 1;
		}
		try {
			JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::global", username + "::::" + password + "::::" + userHash + "::::" + Integer.toString(globali), "vh_network.php");
			JSONArray jSONArray = json.getJSONArray("data");
			result = jSONArray.getJSONObject(0).getString("ip");
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public ArrayList<String> getIPs(int number, boolean attacked, boolean global){
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> temporary = new ArrayList<String>();
		int globali = 0;
		if (global) {
			globali = 1;
		}
		if (number > 10) {
			for (int i = 10; i <= number + 9; i = i + 10) {
				try {
					JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::global", username + "::::" + password + "::::" + userHash + "::::" + Integer.toString(globali), "vh_network.php");
					JSONArray jSONArray = json.getJSONArray("data");
					for (int j = 0; j <= jSONArray.length() - 1; j++) {
						JSONObject ip = jSONArray.getJSONObject(j);
						temporary.add(ip.getString("ip"));
					}
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (int k = 0; k <= number; k++) {
				result.add(temporary.get(k));
			}
		} else {
			try {
				JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::global", username + "::::" + password + "::::" + userHash + "::::" + Integer.toString(globali), "vh_network.php");
				JSONArray jSONArray = json.getJSONArray("data");
				for (int k = 0; k < number; k++) {
					result.add(jSONArray.getJSONObject(k).getString("ip"));
				}
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public ScannedNode scanIP(String ip) {
        InputStream is;
        BufferedReader rd;
        ScannedNode result = null;
        try{
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
            try{
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
			try {
				JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::target", username + "::::" + password + "::::" + userHash + "::::" + nodes.get(j).getIP(), "vh_trTransfer.php");
				result = new TransferResult(json, nodes.get(j).getIP());
				array.add(result);
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return array;
    }

    public TransferResult trTransferIP(String ip) {
		TransferResult result = null;
		try {
			JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::target", username + "::::" + password + "::::" + userHash + "::::" + ip, "vh_trTransfer.php");
			result = new TransferResult(json, ip);
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
    
	
	public ArrayList<TransferResult> trTransferIPs(ArrayList<String> ips) {
		ArrayList<TransferResult> array = new ArrayList<TransferResult>();
		TransferResult result;
		for (int j = 0; j < ips.size(); j++) {
			try {
				JSONObject json = Utils.JSONRequest("user::::pass::::uhash::::target", username + "::::" + password + "::::" + userHash + "::::" + ips.get(j), "vh_trTransfer.php");
				result = new TransferResult(json, ips.get(j));
				array.add(result);
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
