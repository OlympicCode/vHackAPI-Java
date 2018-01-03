package me.checkium.vhackapi.Spyware;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spyware {

    protected final Pattern pattern = Pattern.compile("(\\d\\d)m, (\\d\\d)s.");

    protected int av;
    protected int fw;
    protected int money;
    protected int spam;
    protected int next;
    protected String ip;
    protected String username;

    public Spyware(JSONObject json) {
        av = json.getInt("AV");
        fw = json.getInt("FW");
        money = json.getInt("MONEY");
        spam = json.getInt("SPAM");
        ip = json.getString("IP");
        username = json.getString("user");

        String nextString = json.getString("next");
        if ("now.".equals(nextString)) {
            next = 0;
        } else {
            Matcher matcher = pattern.matcher(nextString);
            if (matcher.matches()) {
                next = Integer.parseInt(matcher.group(1)) * 60 + Integer.parseInt(matcher.group(2));
            } else {
                next = 3600;
            }
        }
    }

    public int getAv() {
        return av;
    }

    public int getFw() {
        return fw;
    }

    public int getMoney() {
        return money;
    }

    public int getSpam() {
        return spam;
    }

    public int getNext() {
        return next;
    }

    public String getIp() {
        return ip;
    }

    public String getUsername() {
        return username;
    }

}
