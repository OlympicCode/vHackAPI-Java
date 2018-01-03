package me.checkium.vhackapi.upgrades;

import me.checkium.vhackapi.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class UpgradeManager {

    protected String username;
    protected String password;
    protected String userHash;

    public UpgradeManager(String user, String pass, String uhash) {
        username = user;
        password = pass;
        userHash = uhash;
    }

    public UpgradeResult addUpdate(UpgradeType type) {

        JSONObject json = new JSONObject();
        json = Utils.JSONRequest("user::::pass::::UHASH::::utype", username + "::::" + password + "::::" + userHash + "::::" + type.toString(), "vh_addUpdate.php");
        int result = json.getInt("result");
        if (result == 0) {
            return UpgradeResult.SUCCESS;
        } else if (result == 1) {
            return UpgradeResult.NO_MONEY;
        } else if (result == 2) {
            return UpgradeResult.INVALID;
        } else if (result == 3) {
            return UpgradeResult.NO_MEMORY;
        } else {
            return UpgradeResult.INVALID;
        }
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> array = new ArrayList<Task>();
        JSONArray jsonArray;
        JSONObject json = Utils.JSONRequest("user::::pass::::UHASH", username + "::::" + password + "::::" + userHash, "vh_tasks.php");
        jsonArray = json.getJSONArray("data");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            Task task = new Task(object);
            array.add(task);
        }

        return array;
    }

    public boolean finishTask(Task task) {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String line = Utils.stringRequest("user::::pass::::UHASH::::taskid", username + "::::" + password + "::::" + userHash + "::::" + task.getTaskID(), "vh_finishTask.php");

        if (line.contains("4")) {
            return true;
        } else if (line.equals("")) {
            return false;
        } else {
            return false;
        }
    }

    public boolean abortTask(Task task) {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String line = Utils.stringRequest("user::::pass::::UHASH::::taskid", username + "::::" + password + "::::" + userHash + "::::" + task.getTaskID(), "vh_abortTask.php");

        if(line == null){
            throw new NullPointerException();
        }

        if (line.contains("0")) {
            //succeded
            return true;
        } else if ("".equals(line)) {
            //other error
            return false;
        } else if (line.contains("2")) {
            //task not running
            return false;
        } else {
            return false;
        }
    }
}
