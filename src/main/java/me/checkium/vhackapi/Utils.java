package me.checkium.vhackapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;


public class Utils {

    /**
     * The url of the current api.<br>
     * As of now it is {@value url}.
     */
    private static final String url = "https://api.vhack.cc/v/2/";
    /**
     * The hashing algorithm that is used to hash data in requests.<br>
     * It now is {@value md5s}.
     */
    private static final String md5s = "MD5";
    /**
     * A secret salt that is used with hashing<br>
     * It now is {@value secret}.
     */
    private static final String secret = "aeffI";
    /**
     * Unknown
     */
    static final boolean assertionstatus;
    /**
     * Unknown - maybe the charset?
     */
    private static final byte[] byt;
    
    public static boolean debug = false;

    static {
        assertionstatus = !Utils.class.desiredAssertionStatus();
        byt = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 45, (byte) 95};
    }

    /**
     * Reads all data from a buffered reader and returns it as a String.
     * @param rd The buffered Reader which holds the data.
     * @return The String representation of data the buffered reader contains.
     * @throws IOException  If an I/O error occurs
     */
    public static String readJson(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
      }

    /**
     * Makes a request to the api and returns the result as a JSONObject Object.
     * Makes a requests to the vHack Api, with the params format, data data and to the file php and returns the result, which is json, as a JSONObject Object.<br>
     * Errors are thrown if user/password is wrong and (possibly) if the api url changed.<br>
     * It is similar to {@link Utils#StringRequest(String, String, String)} but differs from it in that does processing with the obtained data.<br>
     * it returns the result as json Object and performs checks for any (known) errors.
     * @param format Lists the params that will be passed to the api endpoint. The names are separated with "::::".<br>
     *               Every request, except the very first one, should include "user::::pass::::uhash".<br>
     *               Example: "user::::pass::::uhash::::global" (taken from Console.getIP)
     * @param data The data for the params that you passed in. They are also separated by "::::". You can just concatanate the parts of this.<br>
     *             Example: "vHackAPI::::123456::::aaaabbbbccccddddeeeeffffgggghhhhiiiijjjjkkkkllllmmmmnnnnoooopppp::::1"
     * @param php This is the api endpoint that the request will be sent to. In the case of the vHackAPI it are php documents.<br>
     *            Example "vh_network.php"
     * @return The resulte Json as a JSONObject. Errors are thrown if user/password is wrong and (possibly) if the api url changed. null is returned if there are other errors.
     */
    public static JSONObject JSONRequest(String format, String data, String php){
    	JSONObject json = null;
    	Future<String> jsonTextC = Request(format, data, php);
    	String jsonText = "";
    	try{
    		if(jsonTextC.isDone() /*|| jsonTextC.get() != ""*/){

    			jsonText = jsonTextC.get(2000, TimeUnit.MILLISECONDS);
    		
    		} else {
    		
    			Thread.sleep(1000);
    			jsonText = jsonTextC.get(2000, TimeUnit.MILLISECONDS);
    		
    		}
    	} catch(Exception e) {    	
    		
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				
			}
    		JSONRequest(format,data,php);
    		
    	}
    	if("".equals(jsonText))
    	{
			throw new RuntimeException("Old API URL");
		}
		else if("8".equals(jsonText))
		{
			throw new RuntimeException("Wrong Password/User");
		}
		else if (jsonText.length() == 1) {
			return null;
		}
		json = new JSONObject(jsonText);
		return json;
	}

    //it'll just do the request without any checks
    /**
     * Makes a request to the api and returns the result as a String.
     * Makes a requests to the vHack Api, with the params format, data data and to the file php and returns the result, which is json, as a String Object.<br>
     * It is similar to {@link Utils#JSONRequest(String, String, String)} but differs from it in the form that it returns and String and doesn't perform checks.
     * @param format Lists the params that will be passed to the api endpoint. The names are separated with "::::".<br>
     *               Every request, except the very first one, should include "user::::pass::::uhash".<br>
     *               Example: "user::::pass::::uhash::::global" (taken from Console.getIP)
     * @param data The data for the params that you passed in. They are also separated by "::::". You can just concatanate the parts of this.<br>
     *             Example: "vHackAPI::::123456::::aaaabbbbccccddddeeeeffffgggghhhhiiiijjjjkkkkllllmmmmnnnnoooopppp::::1"
     * @param php This is the api endpoint that the request will be sent to. In the case of the vHackAPI it are php documents.<br>
     *            Example "vh_network.php"
     * @return The resulte Json as a Future<String>.
     */
    //JDOC needs rewriting
    @Async
    public static Future<String> Request(String format, String data, String php)
    {

    	Future<String> jText;
    	System.setProperty("http.agent", "Chrome");
    	InputStream is;
    	try {
    		is = new URL(Utils.generateURL(format, data, php)).openStream();
    		if(debug == true){
   				
    			URL url = new URL(Utils.generateURL(format, data, php));
    			System.out.println(url.toString());
    			
    		}
    		Thread.sleep(1000);
    		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
    		jText = new AsyncResult<String>(Utils.readJson(rd));
    		return jText;
    	} catch (Exception e) {
    		try {
    			Thread.sleep(1000);
    		} catch (InterruptedException e1) {
    			e1.printStackTrace();
    		}
    		Request(format,data,php);
    	}
    	return null;
    }
    
    public static String StringRequest(String format, String data, String php)
    {
    	
    	Future<String> jsonTextC = Request(format, data, php);
    	String jsonText = "";
    	try{
    		if(jsonTextC.isDone()){
    		
    			jsonText = jsonTextC.get();
    			return jsonText;
    			
    		} else {

    			Thread.sleep(1000);
    			jsonText = jsonTextC.get(1000, TimeUnit.MILLISECONDS);
    			return jsonText;
    			
    		}
    	} catch(Exception e) {    	
    		
    		StringRequest(format,data,php);
    		
    	}
    	return null;
    	
    }
    
    /**
     * Sets a proxy for the system
     * @param proxyUrl  The proxy's IP/URL
     * @param proxyPort The proxy's port
     */
    public static void useProxy(String proxyUrl, int proxyPort){
    	
    	System.setProperty("http.proxyHost", proxyUrl);
    	System.setProperty("http.proxyPort", String.valueOf(proxyPort));
    	
    }

    /**
     * Sets a proxy that requires auth for the system
     * @param proxyUrl  The proxy's IP/URL
     * @param proxyPort The proxy's port
     * @param username  The proxy's username
     * @param password  The proxy's password
     */
    public static void useProxy(String proxyUrl, int proxyPort, String username, String password){
    	
    	System.setProperty("http.proxyHost", proxyUrl);
    	System.setProperty("http.proxyPort", String.valueOf(proxyPort));
    	System.setProperty("http.proxyUser", username);
    	System.setProperty("http.proxyPassword", password);
    	
    }

    private static byte[] m9179a(byte[] arrby, int n2, int n3, byte[] arrby2, int n4, byte[] arrby3) {
        int n5 = n3 > 0 ? arrby[n2] << 24 >>> 8 : 0;
        int n6 = n3 > 1 ? arrby[n2 + 1] << 24 >>> 16 : 0;
        int n7 = n6 | n5;
        int n8 = 0;
        if (n3 > 2) {
            n8 = arrby[n2 + 2] << 24 >>> 24;
        }
        int n9 = n8 | n7;
        switch (n3) {
            default: {
                return arrby2;
            }
            case 3: {
                arrby2[n4] = arrby3[n9 >>> 18];
                arrby2[n4 + 1] = arrby3[63 & n9 >>> 12];
                arrby2[n4 + 2] = arrby3[63 & n9 >>> 6];
                arrby2[n4 + 3] = arrby3[n9 & 63];
                return arrby2;
            }
            case 2: {
                arrby2[n4] = arrby3[n9 >>> 18];
                arrby2[n4 + 1] = arrby3[63 & n9 >>> 12];
                arrby2[n4 + 2] = arrby3[63 & n9 >>> 6];
                arrby2[n4 + 3] = 61;
                return arrby2;
            }
            case 1:
        }
        arrby2[n4] = arrby3[n9 >>> 18];
        arrby2[n4 + 1] = arrby3[63 & n9 >>> 12];
        arrby2[n4 + 2] = 61;
        arrby2[n4 + 3] = 61;
        return arrby2;
    }

    private static String generateUser(byte[] bArr, int i, int i2, byte[] bArr2, boolean z) {
        byte[] a = assertion(bArr, i, i2, bArr2, Integer.MAX_VALUE);
        int length = a.length;
        while (!z && length > 0 && a[length - 1] == 61) {
            length--;
        }
        return new String(a, 0, length);
    }

    /**
     * Hashes the given String with {@value md5s}.
     * The hashing alorithm is determined by {@link Utils#md5s}
     * @param str The string that should be hashed with {@value md5s}.
     * @return The parameter str hashed using {@value md5s}.
     */
    private static final String hashString(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(md5s);
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : digest) {
                String toHexString = Integer.toHexString(b & 255);
                while (toHexString.length() < 2) {
                    toHexString = "0" + toHexString;
                }
                stringBuilder.append(toHexString);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static byte[] assertion(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        int i4 = ((i2 + 2) / 3) * 4;
        byte[] bArr3 = new byte[(i4 + (i4 / i3))];
        int i5 = i2 - 2;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i8 < i5) {
            i4 = (((bArr[i8 + i] << 24) >>> 8) | ((bArr[(i8 + 1) + i] << 24) >>> 16)) | ((bArr[(i8 + 2) + i] << 24) >>> 24);
            bArr3[i7] = bArr2[i4 >>> 18];
            bArr3[i7 + 1] = bArr2[(i4 >>> 12) & 63];
            bArr3[i7 + 2] = bArr2[(i4 >>> 6) & 63];
            bArr3[i7 + 3] = bArr2[i4 & 63];
            i4 = i6 + 4;
            if (i4 == i3) {
                bArr3[i7 + 4] = (byte) 10;
                i7++;
                i4 = 0;
            }
            i8 += 3;
            i7 += 4;
            i6 = i4;
        }
        if (i8 < i2) {
            m9179a(bArr, i8 + i, i2 - i8, bArr3, i7, bArr2);
            if (i6 + 4 == i3) {
                bArr3[i7 + 4] = (byte) 10;
                i7++;
            }
            i7 += 4;
        }
        if (assertionstatus || i4 == bArr3.length) {
            return bArr3;
        }
        throw new AssertionError();
    }

    /**
     * Generates a url to where a request has to be made.
     * Generates the complete url a request has to be done to, to achieve a certain action (E.g. upgrade a Botnet Computer).<br>
     * Needed for this are the username, the password, the uHash and any additional parameters. The time is also neede but you dont need to supply it because the programm get the time by it itself.<br>
     * It is used by {@link Utils#JSONRequest(String, String, String)} and {@link Utils#StringRequest(String, String, String)}.
     * @param format Lists the params that will be passed to the api endpoint. The names are separated with "::::".<br>
     *               Every request, except the very first one, should include "user::::pass::::uhash".<br>
     *               Example: "user::::pass::::uhash::::global" (taken from Console.getIP)
     * @param data The data for the params that you passed in. They are also separated by "::::". You can just concatanate the parts of this.<br>
     *             Example: "vHackAPI::::123456::::aaaabbbbccccddddeeeeffffgggghhhhiiiijjjjkkkkllllmmmmnnnnoooopppp::::1"
     * @param php This is the api endpoint that the request will be sent to. In the case of the vHackAPI it are php documents.<br>
     *            Example "vh_network.php"
     * @return The url Url a request has to be directed to.
     */
    public  static String generateURL(String format, String data, String php) {
        String[] split = format.split("::::");
        String[] split2 = data.split("::::");
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        JSONObject jSONObject = new JSONObject();
        for (int i = 0; i < split.length; i++) {
            try {
                jSONObject.put(split[i], split2[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            jSONObject.put("time", currentTimeMillis + "");
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        String jsonString = jSONObject.toString();
        byte[] jsonStringBytes = jsonString.getBytes();
        String a = generateUser(jsonStringBytes, 0, jsonStringBytes.length, byt, false);
        String a2 = hashString(jsonString.length() + hashString(currentTimeMillis + ""));
        String str5 = split2[0] + "" + hashString(hashString(split2[1]));
        String str6 = hashString(currentTimeMillis + "" + jsonString);
        byte[] bytes2 = a2.getBytes();
        byte[] bytes3 = str5.getBytes();
        byte[] bytes4 = str6.getBytes();
        String a3 = hashString(secret + hashString(hashString(generateUser(bytes2, 0, bytes2.length, byt, false))));
        String str9 = generateUser(bytes3, 0, bytes3.length, byt, false);
        String str7 = generateUser(bytes4, 0, bytes4.length, byt, false);
        String str8 = hashString(hashString(a3 + hashString(hashString(str9) + str7)));
        return url + php + "?user=" + a + "&pass=" + str8;
    }

}
