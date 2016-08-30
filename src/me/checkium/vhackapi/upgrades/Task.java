package me.checkium.vhackapi.upgrades;

import org.json.JSONObject;

public class Task {

	protected UpgradeType type;
	protected int start;
	protected int end;
	protected int upTo;
	protected int TaskID;
	public Task(JSONObject data) {
		type = UpgradeType.valueOf(data.getString("type"));
		start = data.getInt("start");
		end = data.getInt("end");
		upTo = data.getInt("wto");
		TaskID = data.getInt("taskid");
	}
	
	public UpgradeType getType() {
		return type;
	}
	
	public int getStartTime() {
		return start;
	}
	
	public int getEndTime() {
		return end;
	}
	
	public int getLevel() {
		return upTo;
	}
	
	public int getTaskID() {
		return TaskID;
	}
}
