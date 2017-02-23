package me.checkium.vhackapi.console;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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

		System.out.println("Scanning [" + ip + "]");

        /*try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        //IDK Why it's here.... it makes the scan slower
		String resultString = Utils.StringRequest("user::::pass::::target", username + "::::" + password + "::::" + ip, "vh_scan.php");
        String[] tempParsedResultString = parseScanResult(Utils.StringRequest("user::::pass::::target", username + "::::" + password + "::::" + ip, "vh_scan.php")); */
        result = new ScannedNode(parseScanResult(Utils.StringRequest("user::::pass::::target", username + "::::" + password + "::::" + ip, "vh_vulnScan.php")));
        result.setIP(ip);

        return result;
    }
	

	
	public String requestPassword(String ip) {
		String result = Utils.StringRequest("user::::pass::::target", username + "::::" + password + "::::" + ip, "vh_vulnScan.php");
		/*
		Example Output:
		{"img_0":"iVBORw0KGgoAAAANSUhEUgAAAG0AAAAoCAYAAAD0bXSJAAABaElEQVR4nO2YWxKDIAxFodONyP7XpEuxX8wwTAIBou2193xZHwQ5GELjGQgar293gIxDaYBQGiCUBgilAUJpgFAaIJQGCKUBQmmAUBoglAYIpQFCaYC8LTcd+36GEMKWUrScX8ESKx9L5OdG2um9lxZPeu\/emNRtzYydSdqv4jlZNFpij30\/y\/OtySS1JbVhwZQepRl3xVeGFGtLKa4I0+JbMH9pOZ2UAaROW1JoL0X0YnniFWtWwAxDhYg0S0YpJbZe1BIrD3Y96Np92vVWrDqVWeJdjduaVs5YrRjQUsRsbr9jTdPizPbZgyFpde5f\/druiPVEzNIslY91fegJ8KqyyrbqY2RMa5pX5aM9U4r2rLLK51eygodsz6rYfZ\/W2rSW1z02mb0Cpv7d25hLaP2t25YmohZ\/tVKNj8gXfwb\/ewSE0gChNEAoDRBKA4TSAKE0QCgNEEoDhNIAoTRAKA0QSgOE0gD5ACuzfgg4aLQjAAAAAElFTkSuQmCC","img_1":"iVBORw0KGgoAAAANSUhEUgAAAG0AAAAoCAYAAAD0bXSJAAABTklEQVR4nO2ZURKCMAxExeEicv8zlaPgFzM1E2iWQuvCvj8kQ0NfIkGH5SXYePdOQOBIGiGSRoikESJphEgaIZJGiKQRImmESBohkkaIpBEiaYRIGiEjEjyn9PNPzmeahlJMKc47F43J14rElGJrieTj5YTmE+60fAPXReziNqYU551DYpCbtTldQfTatXmEpHkVb4VsdYU9PiLfi3kyTZ5pduNR+VeJm1Na8nXz41JMzyIKSfM27U6VP6e0bHW1V3C9uz\/caXmi601el1Zbol1t43rtATQ9tkzyTp18NvD0eAT7nPK+Zr0BpsXE15KzChEaRPY2OzLe27jIu1oLIkV09lo16wyI+hYv18jntfnk19x7MY5uMJrP0cKApN2Nf+l0FP32SMijO40VdRohkkaIpBEiaYRIGiGSRoikESJphEgaIZJGiKQRImmEfAHb4zLEq0mqFAAAAABJRU5ErkJggg==","img_2":"iVBORw0KGgoAAAANSUhEUgAAAG0AAAAoCAYAAAD0bXSJAAABWklEQVR4nO2aQQ7DIAwEoepHwv\/fRJ5CT0gWAgcMqFm0I\/VCAxiGYB\/ikyNofP4dABmH0gChNEAoDRBKA4TSAKE0QCgNEEoDhNIAoTRAKA0QSgOE0gD5WjrdMSbnnLtC8GvDsc2Vn6mR+7XGke0j6yrnbPXZsVdHvWlXCF7+nNOFWjdU9tPmkW1aHKMcJW2EFcJyW01cr1gLputRIgORC+m5jlpjPPWt\/WeJedf13hJ7x5juGNPsvFPSZADWgMoFtk5jz1y1vj2HBo2p6\/HpisjkTa61t8axziWfQRajcVRO68kfq\/OLRj6srUNr5ShpmbeIq1WzK9guTSt7taqrZxxtI3aJe6oUR8aysk1amYNaeau3kJHVVznGbIyluPJa0w7bPwob\/8aPVU+o8HZyZE47nVe+aUSHbxoglAYIpQFCaYBQGiCUBgilAUJpgFAaIJQGCKUBQmmA\/AACDi4u8Rb+QgAAAABJRU5ErkJggg==","img_3":"iVBORw0KGgoAAAANSUhEUgAAAG0AAAAoCAYAAAD0bXSJAAABgUlEQVR4nO2ZUQ7CIBBEwXiRcv8z1aPoFwmpsMxSWhkzL\/FDu5JdBnZoG99BsPH4dQLCj0QjRKIRItEIkWiESDRCJBohEo0QiUaIRCNEohEi0QiRaIRINEKev06gxmvfv94YbSlFT0ztemssby7WOMd4JCdPPiGEEFd7n5YLqxWbf0Ni0LFH8kHGsq6P5FGyXHvcUorHYmpi9WL+Gag9elYb0h5mrtoZY5Q5bynFszvhaqZ6Wq2Nvfb9PVr8cTLPxiCtqrVTEZ+9i2ntsTYxLQ8qJ8Ca0LINWiZuxXi9BRGi1p7vZLqn5d2VPzPGRPzKuyBGcljFN6cf+XsTlD0DaWtnuMqXyvyPfn3X7oN2mrWKrZj83bMya\/Etr7RiEJC60P+V368+yLju05BTFmrY6GmuNcbMm2urrtGTM1pXK9YCEq219Vc\/Go+yel2Qp5UrrnZtbkqix3KPsUSf5R5jiT4SjRCJRohEI0SiESLRCJFohEg0QiQaIRKNEIlGiEQj5ANfcHAufpnI1gAAAABJRU5ErkJggg==","secret":"*e*3**q***"}
		 */
		return result;
    }
	
	public String enterPassword(String passwd) {
		String result = Utils.StringRequest("user::::pass::::decision", username + "::::" + password + "::::" + passwd, "vh_createConnection.php");
		/*
		Result:
		15 = Wrong Password
		14 = Already tried
		0 = Right Password
		 */
		return result;
    }
	
	public String vulnScan() {
        	String result = null;
        	result = Utils.StringRequest("user::::pass::::", username + "::::" + password + "::::", "vh_loadRemoteData.php");
        	//Output: {"fw":1,"av":1,"spam":1,"sdk":1,"ipsp":1,"money":154396,"savings":"0","anonymous":"YES","username":"Captain_Snacky","winelo":19,"winchance":90,"spyware":"1","ipaddress":"242.126.137.253"}
    	    return result;
    }
	
	public String transferMoney(String ip) {
		String result = Utils.StringRequest("user::::pass::::target", username + "::::" + password + "::::" + ip, "vh_trTransfer.php");
		/*
		Example Output:
		{"result":"0","amount":32523,"elo":1084,"eloch":12,"newmoney":29056310}
		*/
		return result;
    }
	
	public String clearLog(String ip) {
		String result = Utils.StringRequest("user::::pass::::target", username + "::::" + password + "::::" + ip, "vh_clearAccessLogs.php");
		/*
		Output:
		0 = Success
		1 = Failed
		*/
		return result;
    }
	
	public String uploadSpyware(String ip) {
		String result = Utils.StringRequest("user::::pass::::target", username + "::::" + password + "::::" + ip, "vh_spywareUpload.php");
		/*
		Output:
		0 = Success
		7 = Error
		11 = Already Uploaded
		*/
		return result;
    }
	

	
    public ArrayList<ScannedNode> scanIPs(List<String> ips) {
        ArrayList<ScannedNode> array = new ArrayList<>();

        ips.forEach(ip -> array.add(scanIP(ip)));

        return array;
    }

    public TransferResult transferTrojanTo(ScannedNode node) throws JSONException {
		System.out.println("Transfering trojan [" + node.getIP() + "]");
		/*try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/

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
