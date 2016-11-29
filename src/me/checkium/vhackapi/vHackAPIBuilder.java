package me.checkium.vhackapi;

import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
		 try {
	   		    SSLContext sc = SSLContext.getInstance("SSL");
	   		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
	   		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	   		} catch (GeneralSecurityException e) {
	   		}
	   		
		 vHackAPI api = new vHackAPI(username, password);
		 return api;
	 }
	 
	  public static TrustManager[] trustAllCerts = new TrustManager[] {
	    	    new X509TrustManager() {
	    	        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	    	            return new X509Certificate[0];
	    	        }
	    	        public void checkClientTrusted(
	    	            java.security.cert.X509Certificate[] certs, String authType) {
	    	            }
	    	        public void checkServerTrusted(
	    	            java.security.cert.X509Certificate[] certs, String authType) {
	    	        }
	    	    }
	    	};


}
