package me.checkium.vhackapi.upgrades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import me.checkium.vhackapi.Utils;

public class UpgradeManager {

	protected String username;
	protected String password;
	
	public UpgradeManager(String user, String pass) {
		username = user;
		password = pass;
	}
	
	public UpgradeResult addUpdate(UpgradeType type) {
		
		JSONObject json = new JSONObject();
		try {
	    TimeUnit.MILLISECONDS.sleep(100);
		InputStream is = new URL(Utils.generateURL("user::::pass::::utype", username + "::::" + password + "::::" + type.toString(), "vh_addUpdate.php")).openStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		String jsonText;
		
			jsonText = Utils.readJson(rd);
	     json = new JSONObject(jsonText);
	    
	    
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int result = json.getInt("result");
		if (result == 1) {
			return UpgradeResult.NoMoney;
		} else if (result == 2) {
			return UpgradeResult.Invalid;
		} else if (result == 3) {
			return UpgradeResult.NoMemory;
		} else if (result == 0) {
			return UpgradeResult.Success;
		} else {
			return UpgradeResult.Invalid;
		}
	}
	
	public ArrayList<Task> getTasks() {
		ArrayList<Task> array = new ArrayList<Task>();
		JSONObject json = new JSONObject();
		try {
	    TimeUnit.MILLISECONDS.sleep(100);
		InputStream is = new URL(Utils.generateURL("user::::pass", username + "::::" + password, "vh_tasks.php")).openStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		String jsonText;
		
		 jsonText = Utils.readJson(rd);
		  JSONArray arrayy;
		 try {
	    json = new JSONObject(jsonText);
	   
	     
	    	 arrayy = json.getJSONArray("data");
	     } catch (Exception e) {
	    	 return array;
	     }
	     
	     for (int i = 0; i < arrayy.length(); i++) {
	    	 JSONObject object = arrayy.getJSONObject(i);
	    	 Task task = new Task(object);
	    	 array.add(task);
		}
	    
	    
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
	
	public boolean finishTask(Task task) {
		URLConnection in;
		try {
			TimeUnit.MILLISECONDS.sleep(100);
			in = new URL(Utils.generateURL("user::::pass::::taskid", username + "::::" + password + "::::" + task.getTaskID(), "vh_finishTask.php")).openConnection();
		
		BufferedReader br = new BufferedReader(new InputStreamReader((in.getInputStream())));
		String line = br.readLine();
		if (line == null) {
			return false;
		}
		if (line.contains("4")) {
			return true;
		} else {
			return false;
		}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return false;
		
	}
	
	public boolean abortTask(Task task) {
		URLConnection in;
		try {
			TimeUnit.MILLISECONDS.sleep(100);
			in = new URL(Utils.generateURL("user::::pass::::taskid", username + "::::" + password + "::::" + task.getTaskID(), "vh_abortTask.php")).openConnection();
		
		BufferedReader br = new BufferedReader(new InputStreamReader((in.getInputStream())));
		String line = br.readLine();
		if (line == null) {
			return false;
		}
		if (line.contains("0")) {
			return true;
		} else {
			return false;
		}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return false;
		
	}
}
