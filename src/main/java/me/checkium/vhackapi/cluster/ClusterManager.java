package me.checkium.vhackapi.cluster;

import org.json.JSONObject;

import me.checkium.vhackapi.Utils;

public class ClusterManager {

	JSONObject cData;
	protected static String username, password, uhash;
	
	public ClusterManager(String username, String password, String uhash){
		
		ClusterManager.username = username;
		ClusterManager.password = password;
		ClusterManager.uhash = uhash;
		this.cData = getClusterData();
		
	}
	
	public JSONObject getClusterData(){
		
		try{
			JSONObject cData = Utils.JSONRequest("user::::pass::::uhash", username + "::::" + password + "::::" + uhash, "vh_ClusterData.php");
			return cData;
		} catch(Exception e) {
			return null;
		}
		
	}
	
	public Cluster getCluster(){
		
		return new Cluster(cData);
		
	}
	
	public Cluster createCluster(String cName, String cID){
		
		try{
			Utils.JSONRequest("user::::pass::::uhash::::cname::::cid", username + "::::" + password + "::::" + uhash + "::::" + cName + "::::" + cID, "vh_clusterAdd.php");
			return new Cluster(getClusterData());
		} catch(Exception e) {
			
			return null;
			
		}
		
	}
	
	public Cluster joinCluster(String cName){
		
		try{
			Utils.JSONRequest("user::::pass::::uhash::::cname", username + "::::" + password + "::::" + uhash + "::::" + cName, "vh_clusterJoin.php");
			return new Cluster(getClusterData());
		} catch(Exception e) {
			
			return null;
			
		}
		
	}
	
	public void leaveCluster(){
		
		try{
			Utils.JSONRequest("user::::pass::::uhash", username + "::::" + password + "::::" + uhash, "vh_clusterLeave.php");
		} catch(Exception e) {
			
		}
		
	}
}
