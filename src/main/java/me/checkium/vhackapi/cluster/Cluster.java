package me.checkium.vhackapi.cluster;

import org.json.JSONArray;
import org.json.JSONObject;

import me.checkium.vhackapi.Utils;

public class Cluster {

	private JSONObject cData;
	
	public Cluster(JSONObject cData){
		
		this.cData = cData;
		
	}
	
	public String getClusterName(){
		
		return cData.getString("cName");
		
	}
	
	public String getClusterTag(){
		
		return cData.getString("cTag");
		
	}
	
	public String getClustetLeader(){
		
		return cData.getString("cLeader");
		
	}
	
	public int getNumberOfMembers(){
		
		return cData.getInt("cMemeber");
		
	}
	
	public int getMaxNumberOfMemebers(){
		
		return cData.getInt("cMaxMember");
		
	}
	
	public ClusterMember[] getClusterMemebers(){
		
		JSONObject usersRaw = Utils.JSONRequest("user::::pass::::uhash", ClusterManager.username + "::::" + ClusterManager.password + "::::" + ClusterManager.uhash, "vh_getClusterMember.php");
		JSONArray usersRaw2 = usersRaw.getJSONArray("users");
		ClusterMember[] members = new ClusterMember[usersRaw2.length()];
		for(int i = 0; i<usersRaw2.length(); i++){
			
			JSONObject jo = usersRaw2.getJSONObject(i);
			members[i] = new ClusterMember(jo);
			
		}
		return members;
		
	}
	
	public ClusterMember[] getClusterRequests(){
		
		try{
			JSONObject jo = Utils.JSONRequest("user::::pass::::uhash", ClusterManager.username + "::::" + ClusterManager.password + "::::" + ClusterManager.uhash, "vh_getClusterRequests.php");
			JSONArray ja = jo.getJSONArray("requests");
			ClusterMember[] applicants = new ClusterMember[ja.length()];
			for(int i = 0; i<ja.length(); i++){
				
				JSONObject jo2 = ja.getJSONObject(i);
				applicants[i] = new ClusterMember(jo2);
			
			}
			return applicants;
		} catch(Exception e) {
			return null;
		}
		
	}
	
	public void kickClusterMember(ClusterMember m){
		
		try{
			Utils.JSONRequest("user::::pass::::uhash::::user2kick", ClusterManager.username + "::::" + ClusterManager.password + "::::" + ClusterManager.uhash + "::::" + m.getID(), "vh_clusterKick.php");
		} catch(Exception e) {
			
		}
		
	}
	
	public void promoteClusterMember(ClusterMember m){
		
		try{
			Utils.JSONRequest("user::::pass::::uhash::::uid", ClusterManager.username + "::::" + ClusterManager.password + "::::" + ClusterManager.uhash + "::::" + m.getID(), "vh_clusterPromote.php");
		} catch(Exception e){
			
		}
		
	}
	public int getClusterMoney(){
		
		return cData.getInt("cMoney");
		
	}
	
	public int getClusterScore(){
		
		return cData.getInt("cScore");
		
	}
	
	public int getClusterReps(){
		
		return cData.getInt("cElo");
		
	}
	
	public int getNumberOfRequests(){
		
		return cData.getInt("cReqs");
		
	}
	
	//Too slow, needs working
	/*public String[] getClusterMessages(){

		JSONArray messagesArray = cData.getJSONArray("messages");
		String[] messages = new String[messagesArray.length() - 1];
		for(int i = (messagesArray.length() - 1); i >= 0; i--){
			
			int i2 = 0;
			JSONObject messageRaw = messagesArray.getJSONObject(i);
			String dateAuthor = messageRaw.getString("from");
			String message = messageRaw.getString("message");
			String format = dateAuthor + ": " + message;
			messages[i2] = format;
			i++;
			
		}
		return messages;
		
	}*/
	
	public void chat(String msg){
		
		Utils.JSONRequest("user::::pass::::uhash::::msg", ClusterManager.username + "::::" + ClusterManager.password + "::::" + ClusterManager.uhash + "::::" + msg, "vh_clusterMessage.php");
		
	}
	
	public void addClusterSlot(){
		
		Utils.JSONRequest("user::::pass::::uhash", ClusterManager.username + "::::" + ClusterManager.password + "::::" + ClusterManager.uhash, "vh_addClusterSlot.php");
		
	}
	
}
