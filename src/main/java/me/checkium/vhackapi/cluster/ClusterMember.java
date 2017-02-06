package me.checkium.vhackapi.cluster;

import org.json.JSONObject;

public class ClusterMember {

	String username, lastactive;
	ClusterRank rank;
	int score, reps, userid;
	
	public ClusterMember(JSONObject memberData){
		
		this.username = memberData.getString("username");
		this.lastactive = memberData.getString("userlastactive");
		this.score = memberData.getInt("userscore");
		this.reps = memberData.getInt("userelo");
		this.userid = memberData.getInt("userid");
		int rank = memberData.getInt("userposition");
		switch(rank){
		
		case 1: this.rank = ClusterRank.MEMBER; break;
		case 2: this.rank = ClusterRank.COLEADER; break;
		case 3: this.rank = ClusterRank.LEADER; break;
		
		}
		
	}
	
	public String getUsername(){
		
		return username;
		
	}
	
	public String getLastActivity(){
		
		return lastactive;
		
	}
	
	public int getID(){
		
		return userid;
		
	}
	
	public int getScore(){
		
		return score;
		
	}
	
	public int getReps(){
		
		return reps;
		
	}
	
	public ClusterRank getRank(){
		
		return rank;
		
	}
	
}
