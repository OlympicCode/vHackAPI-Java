package ga.olympiccode.vhack;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class vHackAPIBuilder {

	
	protected String password;
	protected String username;
	protected String email;
	protected boolean caching = false;
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
	 
	 
	 public vHackAPI getAPI() {
	   	 registerCertificate();	
		 vHackAPI api = new vHackAPI(username, password, caching);
		 return api;
	 }
	 
	 public vHackAPIBuilder cacheStats(boolean d) {
	    	 this.caching = d;
	    	 return this;
	 }
	 
	 public void registerCertificate() {
		 try {
		 SSLContext sc = SSLContext.getInstance("SSL");
		   
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				 HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
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
