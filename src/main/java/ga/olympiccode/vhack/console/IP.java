package ga.olympiccode.vhack.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.json.JSONObject;

import ga.olympiccode.vhack.Utils;

public class IP {
   String IP;
   String password;
   String username;
	public IP(String ip, String password, String username) {
		this.IP = ip;
	}
	
	public String getIP() {
		return IP;
	}
	
	public ScanResult scan() {
		
		 ScanResult result = new ScanResult(parseScanResult(Utils.RawRequest("user::::pass::::target", username + "::::" + password + "::::" + IP, "vh_scan.php")));
	     result.setIP(IP);
	     return result;
	}
	
	public TransferResult transferTrojan() {
		 JSONObject json = new JSONObject(Utils.JSONRequest("user::::pass::::target", username + "::::" + password + "::::" + IP, "vh_trTransfer.php"));
	     return new TransferResult(json, IP);
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
	
	private String[] parseScanResult(String input) {
		return (new BufferedReader(new StringReader(input))).lines().toArray(String[]::new);
	}
}
