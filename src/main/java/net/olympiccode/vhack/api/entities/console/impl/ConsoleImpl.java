package net.olympiccode.vhack.api.entities.console.impl;

import net.olympiccode.vhack.api.entities.console.Console;
import net.olympiccode.vhack.api.entities.console.ScannedTarget;
import net.olympiccode.vhack.api.entities.console.Target;
import net.olympiccode.vhack.api.entities.console.TransferResult;
import net.olympiccode.vhack.api.entities.impl.vHackAPIImpl;
import net.olympiccode.vhack.api.exceptions.ScanFailException;
import net.olympiccode.vhack.api.requests.Response;
import net.olympiccode.vhack.api.requests.Route;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsoleImpl implements Console {
    private vHackAPIImpl api;

    public ConsoleImpl(vHackAPIImpl api) {
        this.api = api;
    }

    public List<Target> getTargets() {
        Route.CompiledRoute cr = Route.Console.GET_IMG.compile(api, "0");
        Response r = api.getRequester().getResponse(cr);
        List<Target> map = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(r.getString());
            JSONArray array = object.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject data = array.getJSONObject(i);
                map.add(new TargetImpl(api, data.getString("img"), data.getString("hostname")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public ScannedTarget scanTarget(Target t) throws ScanFailException {
        return t.scan();
    }

    public TransferResult transferTarget(ScannedTarget st) {
        return st.transfer();
    }

}
