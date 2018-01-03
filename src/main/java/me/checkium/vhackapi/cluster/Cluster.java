package me.checkium.vhackapi.cluster;

import me.checkium.vhackapi.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Cluster {

    private JSONObject cData;

    public Cluster(JSONObject cData) {
        this.cData = cData;
    }

    public String getClusterName() {
        return cData.getString("cName");
    }

    public String getClusterTag() {
        return cData.getString("cTag");
    }

    public String getClustetLeader() {
        return cData.getString("cLeader");
    }

    public int getNumberOfMembers() {
        return cData.getInt("cMemeber");
    }

    public int getMaxNumberOfMemebers() {
        return cData.getInt("cMaxMember");
    }

    public ClusterMember[] getClusterMemebers() {
        JSONObject usersRaw = Utils.JSONRequest("user::::pass::::UHASH", ClusterManager.username + "::::" + ClusterManager.password + "::::" + ClusterManager.uhash, "vh_getClusterMember.php");
        JSONArray usersRaw2 = usersRaw.getJSONArray("users");
        ClusterMember[] members = new ClusterMember[usersRaw2.length()];

        for (int i = 0; i < usersRaw2.length(); i++) {
            JSONObject jo = usersRaw2.getJSONObject(i);
            members[i] = new ClusterMember(jo);
        }

        return members;
    }

    public ClusterMember[] getClusterRequests() {
        JSONObject jo = Utils.JSONRequest("user::::pass::::UHASH", ClusterManager.username + "::::" + ClusterManager.password + "::::" + ClusterManager.uhash, "vh_getClusterRequests.php");
        JSONArray ja = jo.getJSONArray("requests");
        ClusterMember[] applicants = new ClusterMember[ja.length()];

        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo2 = ja.getJSONObject(i);
            applicants[i] = new ClusterMember(jo2);
        }

        return applicants;
    }

    public void manageClusterRequest(int decision, ClusterMember applicant) {
        Utils.stringRequest("user::::pass::::UHASH::::decision::::uid", ClusterManager.username + "::::" + ClusterManager.password + "::::" + ClusterManager.uhash + "::::" + decision + "::::" + applicant.getID(), "vh_clusterDecision");
        //Seems to return 0,1,2,5,10,12
    }

    public void kickClusterMember(ClusterMember m) {
        Utils.JSONRequest("user::::pass::::UHASH::::user2kick", ClusterManager.username + "::::" + ClusterManager.password + "::::" + ClusterManager.uhash + "::::" + m.getID(), "vh_clusterKick.php");
    }

    public void promoteClusterMember(ClusterMember m) {
        Utils.JSONRequest("user::::pass::::UHASH::::uid", ClusterManager.username + "::::" + ClusterManager.password + "::::" + ClusterManager.uhash + "::::" + m.getID(), "vh_clusterPromote.php");
    }

    public int getClusterMoney() {
        return cData.getInt("cMoney");
    }

    public int getClusterScore() {
        return cData.getInt("cScore");
    }

    public int getClusterReps() {
        return cData.getInt("cElo");
    }

    public int getNumberOfRequests() {
        return cData.getInt("cReqs");
    }

    public JSONObject[] getClusterMessages() {
        JSONArray messagesArray = cData.getJSONArray("messages");
        JSONObject[] messages = new JSONObject[messagesArray.length()];

        for (int i = (messagesArray.length() - 1); i >= 0; i--) {
            JSONObject messageRaw = messagesArray.getJSONObject(i);
            messages[i] = messageRaw;
        }

        return messages;
    }

    public void chat(String msg) {
        Utils.JSONRequest("user::::pass::::UHASH::::msg", ClusterManager.username + "::::" + ClusterManager.password + "::::" + ClusterManager.uhash + "::::" + msg, "vh_clusterMessage.php");
    }

    public void addClusterSlot() {
        Utils.JSONRequest("user::::pass::::UHASH", ClusterManager.username + "::::" + ClusterManager.password + "::::" + ClusterManager.uhash, "vh_addClusterSlot.php");
    }

}
