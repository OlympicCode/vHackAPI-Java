package examples;

import me.checkium.vhackapi.upgrades.Task;
import me.checkium.vhackapi.upgrades.UpgradeResult;
import me.checkium.vhackapi.upgrades.UpgradeType;
import me.checkium.vhackapi.VHackAPI;
import me.checkium.vhackapi.VHackAPIBuilder;

public class UpgradeExample {

    VHackAPI api = new VHackAPIBuilder().password("pass").username("user").getAPI();

    public void addUpgrade() {
        UpgradeResult upgrade = api.getUpgradeManager().addUpdate(UpgradeType.ADW);

        if (upgrade == UpgradeResult.NO_MONEY || upgrade == UpgradeResult.INVALID || upgrade == UpgradeResult.NO_MEMORY) {
            System.out.println("Fail");
        }
        if (upgrade == UpgradeResult.SUCCESS) {
            System.out.println("SUCCESS");
        }
    }

    public void finishUpgrade() {
        Task task = api.getUpgradeManager().getTasks().get(0);
        if (api.getUpgradeManager().finishTask(task)) {
            System.out.println("Task finished with NETCOINS successfully");
        }
    }

    public void abortUpgrade() {
        Task task = api.getUpgradeManager().getTasks().get(0);
        if (api.getUpgradeManager().abortTask(task)) {
            System.out.println("Task aborted successfully");
        }
    }
}
