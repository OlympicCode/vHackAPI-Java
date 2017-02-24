package me.checkium.vhackapi.console;

/**
 * Created by ric on 31/08/16.
 * Updated by flattern 23/02/17
 */
public class ScannedNode {
    private String IP;
    private boolean success = true;
    private String username,
            firewallLevel,
            antiVirusLevel,
            scanLevel,
            sdkLevel,
            spamLevel,
            money,
            anonymous,
            successRep,
            savings,
            failRep,
            ipspLevel,
            spywareLevel,
            successRate;

    public ScannedNode(String[] result) {
    	
        if (result.length == 1) {
            success = false;
            return;
        }
        if (result[1] == null) {
            success = false;
        } else {
        	
        	/*
        	 * Example
        	fw:1
        	av:1
        	spam:1
        	sdk:1
        	ipsp:1
        	money:154396
        	savings:0
        	anonymous:YES
        	username:Captain_Snacky
        	winelo:19
        	winchance:90
        	spyware:1
        	ipaddress:242.126.137.253*/
        	
        	for (String i1:result)
        	{
        		switch (i1.split(":")[0])
        		{
        		case "fw":
        			firewallLevel = i1.split(":")[1];
        			break;
        		case "av":
        			antiVirusLevel = i1.split(":")[1];
        			break;
        		case "spam":
        			spamLevel = i1.split(":")[1];
        			break;
        		case "sdk":
        			sdkLevel = i1.split(":")[1];
        			break;
        		case "ipsp":
        			ipspLevel = i1.split(":")[1];
        			break;
        		case "money":
        			money= i1.split(":")[1];
        			break;
        		case "savings":
        			savings = i1.split(":")[1];
        			break;
        		case "anonymous":
        			anonymous = i1.split(":")[1];
        			break;
        		case "username":
        			username = i1.split(":")[1];
        			break;
        		case "winelo":
        			successRep = i1.split(":")[1];
        			break;
        		case "winchance":
        			successRate = i1.split(":")[1];
        			break;
        		case "spyware":
        			spywareLevel = i1.split(":")[1];
        			break;
        		case "ipaddress":
        			IP = i1.split(":")[1];
        			break;
        			
        		}
        	}
        }

    }


    public String getUsername() {
        if (!success) return null;
        return username;
    }


    public Integer getFirewallLevel() {
        if (!success) return null;
        try {
            return Integer.parseInt(firewallLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer getSavings() {
        if (!success) return null;
        try {
            return Integer.valueOf(savings);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public Integer getIpSpoofingLevel() {
        if (!success) return null;
        try {
            return Integer.valueOf(ipspLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer getAntiVirusLevel() {
        if (!success) return null;
        try {
            return Integer.valueOf(antiVirusLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public Integer getScanLevel() {
        if (!success) return null;
        try {
            return Integer.valueOf(scanLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer getSpywareLevel() {
        if (!success) return null;
        try {
            return Integer.valueOf(spywareLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer getSdkLevel() {
        if (!success) return null;
        try {
            return Integer.valueOf(sdkLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public Integer getSpamLevel() {
        if (!success) return null;
        try {
            return Integer.valueOf(spamLevel);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public Integer getMoney() {
        if (!success) return null;
        try {
            return Integer.valueOf(money);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public Boolean isAnonymous() {
        if (!success) return null;
        return "YES".equals(anonymous);
    }


    public Integer getSuccessRep() {
        if (!success) return null;
        try {
            return Integer.valueOf(successRep);
        } catch (NumberFormatException er) {
            return null;
        }
    }


    public Integer getFailRep() {
        if (!success) return null;
        try {
            return Integer.valueOf(failRep);
        } catch (NumberFormatException er) {
            return null;
        }
    }


    public Integer getSuccessRate() {
        if (!success) return 0;
        try {
            return Integer.valueOf(successRate.replace("%", ""));
        } catch (NumberFormatException er) {
            return null;
        }
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
}
