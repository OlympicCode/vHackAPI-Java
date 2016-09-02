package me.checkium.vhackapi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    static final boolean assertionstatus;
    private static final byte[] byt;
    public static String url;
    public static String md5s;
    public static String secret;

    static {
        assertionstatus = !Utils.class.desiredAssertionStatus();
        url = "http://api.vhackxt.com/v/9/";
        md5s = "MD5";
        secret = "aeffl";
        byt = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 45, (byte) 95};
    }

    public static String readJson(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject JSONRequest(String format, String data, String php) {
        JSONObject json = null;
        InputStream is;
        try {
            URL url = new URL(Utils.generateURL(format, data, php));
            is = url.openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = Utils.readJson(rd);
            if (jsonText.length() == 1) {
                return null;
            }
            json = new JSONObject(jsonText);

        } catch (IOException | JSONException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }

    private static byte[] m9179a(byte[] bArr, int i, int i2, byte[] bArr2, int i3, byte[] bArr3) {
        int i4 = 0;
        int i5 = (i2 > 1 ? (bArr[i + 1] << 24) >>> 16 : 0) | (i2 > 0 ? (bArr[i] << 24) >>> 8 : 0);
        if (i2 > 2) {
            i4 = (bArr[i + 2] << 24) >>> 24;
        }
        i4 |= i5;
        switch (i2) {
            case 1:
                bArr2[i3] = bArr3[i4 >>> 18];
                bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                bArr2[i3 + 2] = (byte) 61;
                bArr2[i3 + 3] = (byte) 61;
                break;
            case 2:
                bArr2[i3] = bArr3[i4 >>> 18];
                bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                bArr2[i3 + 2] = bArr3[(i4 >>> 6) & 63];
                bArr2[i3 + 3] = (byte) 61;
                break;
            case 3:
                bArr2[i3] = bArr3[i4 >>> 18];
                bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                bArr2[i3 + 2] = bArr3[(i4 >>> 6) & 63];
                bArr2[i3 + 3] = bArr3[i4 & 63];
                break;
        }
        return bArr2;
    }

    public static String generateUser(byte[] bArr, int i, int i2, byte[] bArr2, boolean z) {
        byte[] a = assertion(bArr, i, i2, bArr2, Integer.MAX_VALUE);
        int length = a.length;
        while (!z && length > 0 && a[length - 1] == 61) {
            length--;
        }
        return new String(a, 0, length);
    }

    public static final String encryptString(String str) {
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

    public static byte[] assertion(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
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


    public static String generateURL(String str, String str2, String str3) {
        String[] strArr = new String[2];
        String[] split = str.split("::::");
        String[] split2 = str2.split("::::");
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        String str4 = "{";
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
        str4 = jSONObject.toString();
        byte[] bytes = str4.getBytes();
        String a = generateUser(bytes, 0, bytes.length, byt, false);
        String a2 = encryptString(str4.length() + encryptString(currentTimeMillis + ""));
        String str5 = split2[0] + "" + encryptString(encryptString(split2[1]));
        str4 = encryptString(currentTimeMillis + "" + str4);
        byte[] bytes2 = a2.getBytes();
        byte[] bytes3 = str5.getBytes();
        byte[] bytes4 = str4.getBytes();
        a2 = encryptString(secret + encryptString(encryptString(generateUser(bytes2, 0, bytes2.length, byt, false))));
        str5 = generateUser(bytes3, 0, bytes3.length, byt, false);
        str4 = encryptString(encryptString(a2 + encryptString(encryptString(str5) + generateUser(bytes4, 0, bytes4.length, byt, false))));
        strArr[0] = a;
        strArr[1] = str4;
        return url + str3 + "?user=" + a + "&pass=" + str4;
    }

}
