package net.olympiccode.vhack.api.entities.tasks.impl;

import lombok.Getter;
import lombok.Setter;
import net.olympiccode.vhack.api.entities.impl.vHackAPIImpl;
import net.olympiccode.vhack.api.entities.tasks.Task;
import net.olympiccode.vhack.api.entities.tasks.TaskType;
import net.olympiccode.vhack.api.entities.tasks.Upgrade;
import net.olympiccode.vhack.api.requests.Response;
import net.olympiccode.vhack.api.requests.Route;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
@Setter
public class UpgradeImpl implements Upgrade {

    vHackAPIImpl api;
    TaskType type;
    int cost;
    int fillCost;
    int remainingSlots;
    public UpgradeImpl(vHackAPIImpl api, TaskType type, int costs, int fillprice, int can) {
            this.api = api;
            this.type = type;
            this.cost = costs;
            this.fillCost = fillprice;
            this.remainingSlots = can;
            if (cost < 0 || fillprice < 0) {
                throw new RuntimeException("Invalid upgrade price");
            }
    }

    public boolean upgrade() {
        Route.CompiledRoute route = Route.Tasks.ADD_UPDATE.compile(api, type.getId());
        Response r = api.getRequester().getResponse(route);
        try {
            JSONObject object = new JSONObject(r.getString());
            api.user.setMoney(api.user.getMoney() - cost);
            ((UpgradeManagerImpl) api.getUpgradeManager()).reloadTasks();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
         return false;
    }


}
