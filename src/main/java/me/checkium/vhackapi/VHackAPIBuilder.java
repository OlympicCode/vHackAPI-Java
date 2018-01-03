package me.checkium.vhackapi;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

public class VHackAPIBuilder {

    private static TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
    };
    protected String password;
    protected String username;
    protected String email;

    /**
     * Sets a proxy for the system
     *
     * @param proxyUrl  The proxy's IP/URL
     * @param proxyPort The proxy's port
     */
    public VHackAPIBuilder useProxy(String proxyUrl, int proxyPort) {
        System.setProperty("http.proxyHost", proxyUrl);
        System.setProperty("http.proxyPort", String.valueOf(proxyPort));
        return this;
    }

    /**
     * Sets a proxy that requires auth for the system
     *
     * @param proxyUrl  The proxy's IP/URL
     * @param proxyPort The proxy's port
     * @param username  The proxy's username
     * @param password  The proxy's password
     */
    public VHackAPIBuilder useProxy(String proxyUrl, int proxyPort, String username, String password) {
        System.setProperty("http.proxyHost", proxyUrl);
        System.setProperty("http.proxyPort", String.valueOf(proxyPort));
        System.setProperty("http.proxyUser", username);
        System.setProperty("http.proxyPassword", password);
        return this;
    }

    public VHackAPIBuilder username(String username) {
        this.username = username;
        return this;
    }

    public VHackAPIBuilder password(String password) {
        this.password = password;
        return this;
    }

    public VHackAPIBuilder email(String email) {
        this.email = email;
        return this;
    }

    public VHackAPIBuilder register() {
        JSONObject json = Utils.JSONRequest("user::::pass::::email", username + "::::" + password + "::::" + email, "vh_register.php");

        if (!"0".equals(json.getString("result"))) {
            return null;
        }

        return this;
    }


    public VHackAPI getAPI() {
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        VHackAPI api = new VHackAPI(username, password);
        return api;
    }


}
