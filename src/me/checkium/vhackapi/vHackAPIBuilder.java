package me.checkium.vhackapi;

import org.json.JSONObject;

public class vHackAPIBuilder {

	protected String password;
	protected String username;
	protected String email;
	
	 public vHackAPIBuilder username(String username) {
	        this.username = username;
	        return this;
	 }
	 
	 public vHackAPIBuilder password(String password) {
	        this.password = password;
	        return this;
	 }
	 
	 public vHackAPIBuilder email(String email) {
		 this.email = email;
		 return this;
	 }
	 
	 public vHackAPIBuilder register() {
		
		 JSONObject json = Utils.JSONRequest("user::::pass::::email", username + "::::" + password + "::::" + email, "vh_register.php");
		 
		 if (json.getString("result") != "0") {
			 return null;
		 } else {
		 return this;
		 }
	 }
	 
	
	 
	 public vHackAPI getAPI() {
		
		 vHackAPI api = new vHackAPI(username, password);
		 return api;
	 }
}
