package me.checkium.vhackapi.cluster;

import me.checkium.vhackapi.Utils;
import org.json.JSONObject;

public class ClusterManager {

    static String username, password, uhash;
    JSONObject cData;

    public ClusterManager(String username, String password, String uhash) {
        ClusterManager.username = username;
        ClusterManager.password = password;
        ClusterManager.uhash = uhash;
        this.cData = getClusterData();
    }

    public JSONObject getClusterData() {
        JSONObject cData = Utils.JSONRequest("user::::pass::::UHASH", username + "::::" + password + "::::" + uhash, "vh_ClusterData.php");
        return cData;
    }

    public Cluster getCluster() {
        return new Cluster(cData);
    }

    public Cluster createCluster(String cName, String cID) {
        Utils.JSONRequest("user::::pass::::UHASH::::cname::::cid", username + "::::" + password + "::::" + uhash + "::::" + cName + "::::" + cID, "vh_clusterAdd.php");
        return new Cluster(getClusterData());
    }

    public Cluster joinCluster(String cName) {
        Utils.JSONRequest("user::::pass::::UHASH::::cname", username + "::::" + password + "::::" + uhash + "::::" + cName, "vh_clusterJoin.php");
        return new Cluster(getClusterData());
    }

    public void leaveCluster() {
        Utils.JSONRequest("user::::pass::::UHASH", username + "::::" + password + "::::" + uhash, "vh_clusterLeave.php");
    }
}
