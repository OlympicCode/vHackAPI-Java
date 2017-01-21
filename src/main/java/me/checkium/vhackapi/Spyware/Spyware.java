package me.checkium.vhackapi.Spyware;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class Spyware {

	protected final Pattern pattern = Pattern.compile("(\\d\\d)m, (\\d\\d)s.");
	
	protected int av;
	protected int fw;
	protected int money;
	protected int spam;
	protected int next;
	protected String ip;
	protected String username;
	
	public Spyware (JSONObject json) {
		av = json.getInt("av");
		fw = json.getInt("fw");
		money = json.getInt("money");
		spam = json.getInt("spam");
		ip = json.getString("ip");
		username = json.getString("user");
		
		String nextString = json.getString("next");
		if ("now.".equals(next))
		{
			next = 0;
		}
		else
		{
			Matcher matcher = pattern.matcher(nextString);
			if(matcher.matches())
			{
				next = Integer.valueOf(matcher.group(1)) * 60 + Integer.valueOf(matcher.group(2));
			}
			else
			{
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
