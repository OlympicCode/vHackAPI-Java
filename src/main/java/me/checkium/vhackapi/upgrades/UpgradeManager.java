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
import org.json.JSONException;
import org.json.JSONObject;

import me.checkium.vhackapi.Utils;

public class UpgradeManager {

	protected String username;
	protected String password;
	protected String userHash;
	
	public UpgradeManager(String user, String pass, String uhash) {
		username = user;
		password = pass;
		userHash = uhash;
	}
	
	public UpgradeResult addUpdate(UpgradeType type) {
		
		JSONObject json = new JSONObject();
		try {
	    TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		json = Utils.JSONRequest("user::::pass::::uhash::::utype", username + "::::" + password + "::::" + userHash + "::::" + type.toString(), "vh_addUpdate.php");
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

		  JSONArray arrayy;
		 try {
			 json = Utils.JSONRequest("user::::pass::::uhash", username + "::::" + password + "::::" + userHash, "vh_tasks.php");
	   
	     
	    	 arrayy = json.getJSONArray("data");
	     } catch (JSONException e) {
	    	 return array;
	     }
	     
	     for (int i = 0; i < arrayy.length(); i++) {
	    	 JSONObject object = arrayy.getJSONObject(i);
	    	 Task task = new Task(object);
	    	 array.add(task);
		}
	    
	    
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return array;
	}
	
	public boolean finishTask(Task task) {
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String line = Utils.StringRequest("user::::pass::::uhash::::taskid", username + "::::" + password + "::::" + userHash + "::::" + task.getTaskID(), "vh_finishTask.php");

		if (line.contains("4")) {
			return true;
		}
		else if(line.equals("")) {
			return false;
		}
		else {
			return false;
		}
	}
	
	public boolean abortTask(Task task) {
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String line = Utils.StringRequest("user::::pass::::uhash::::taskid", username + "::::" + password + "::::" + userHash + "::::" + task.getTaskID(), "vh_abortTask.php");

		if(line.contains("0"))
		{
			//succeded
			return true;
		}
		else if(line.equals(""))
		{
			//other error
			return false;
		}
		else if(line.contains("2"))
		{
			//task not running
			return false;
		}
		else
		{
			return false;
		}
	}
}
