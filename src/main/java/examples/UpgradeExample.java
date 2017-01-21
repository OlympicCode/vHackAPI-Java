package examples;

import me.checkium.vhackapi.vHackAPI;
import me.checkium.vhackapi.vHackAPIBuilder;
import me.checkium.vhackapi.upgrades.Task;
import me.checkium.vhackapi.upgrades.UpgradeResult;
import me.checkium.vhackapi.upgrades.UpgradeType;

public class UpgradeExample {

	vHackAPI api = new vHackAPIBuilder().password("pass").username("user").getAPI();
	
	public void addUpgrade() {
		 UpgradeResult upgrade = api.getUpgradeManager().addUpdate(UpgradeType.adw);
			
		if (upgrade == UpgradeResult.NoMoney || upgrade == UpgradeResult.Invalid || upgrade == UpgradeResult.NoMemory) {
			System.out.println("Fail");
		}
		if (upgrade == UpgradeResult.Success) {
			System.out.println("Success");
		}
	}
	
	public void finishUpgrade() {
		Task task = api.getUpgradeManager().getTasks().get(0);
		if (api.getUpgradeManager().finishTask(task)) {
			System.out.println("Task finished with netcoins successfully");
		}
	}
	
	public void abortUpgrade() {
		Task task = api.getUpgradeManager().getTasks().get(0);
		if (api.getUpgradeManager().abortTask(task)) {
			System.out.println("Task aborted successfully");
		}
	}
}
