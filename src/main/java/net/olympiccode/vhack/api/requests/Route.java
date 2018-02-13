package net.olympiccode.vhack.api.requests;

import net.olympiccode.vhack.api.entities.impl.vHackAPIImpl;
import net.olympiccode.vhack.api.utils.Encryption;
import net.olympiccode.vhack.api.vHackAPI;
import net.olympiccode.vhack.api.vHackInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private final String route;
    private final String compilableRoute;
    private final int paramCount;
    private final String[] majorParameters;
    private final List<Integer> majorParamIndexes = new ArrayList<>();

    private Route(String route, String... majorParameters) {
        this.route = route;
        this.paramCount = majorParameters.length;
        this.majorParameters = majorParameters;
        compilableRoute = "vh_" + route + ".php?user=%s&pass=%s";
    }

    public String getRoute() {
        return route;
    }

    public String getCompilableRoute() {
        return compilableRoute;
    }

    public int getParamCount() {
        return paramCount;
    }

    public CompiledRoute compile(vHackAPI apir, String... params) {
        if (params.length != paramCount) {
            throw new IllegalArgumentException("Error Compiling Route: [" + route + "], incorrect amount of parameters provided." +
                    "Expected: " + (paramCount) + ", Provided: " + params.length);
        }


        vHackAPIImpl api = (vHackAPIImpl) apir;
        long time = 1515188460;
        JSONObject arguments = new JSONObject();
        try {
            if (params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    arguments.put(majorParameters[i], params[i]);
                }
            } else {
                arguments.put("", "");
            }

            arguments.put("time", String.valueOf(time));

            arguments.put("uhash", api.getUhash());
            arguments.put("user", api.getUsername());
            arguments.put("pass", api.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = arguments.toString();

        String lenghtandtime = Encryption.md5Encrypt(json.length() + Encryption.md5Encrypt(time + ""));
        String logindetails = api.getUsername() + "" + Encryption.md5Encrypt(Encryption.md5Encrypt(api.getPassword()));
        json = Encryption.md5Encrypt(time + "" + json);
        lenghtandtime = Encryption.md5Encrypt("aeffI" + Encryption.md5Encrypt(Encryption.md5Encrypt(Encryption.base64Encrypt(lenghtandtime))));
        logindetails = Encryption.md5Encrypt(lenghtandtime + Encryption.base64Encrypt(logindetails));
        json = Encryption.base64Encrypt(json);
        json = Encryption.md5Encrypt(Encryption.md5Encrypt(lenghtandtime + Encryption.md5Encrypt(Encryption.md5Encrypt(logindetails) + json) + logindetails + Encryption.md5Encrypt(json)));


        String compiledRoute = vHackInfo.API_PREFIX + "vh_" + route + ".php?user=" + Encryption.base64Encrypt(arguments.toString()) + "&pass=" + json;
        return new CompiledRoute(this, compiledRoute);
    }

    @Override
    public int hashCode() {
        return (route).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Route))
            return false;

        Route oRoute = (Route) o;
        return paramCount == (oRoute.paramCount) && route.equals(oRoute.route);
    }

    @Override
    public String toString() {
        return "Route(" + route + ")";
    }

    public static class Misc {
        public static final Route UPDATE = new Route("update");
    }

    public static class Console {
        public static final Route GET_IMG = new Route("getImg", "by");
        public static final Route SCAN_HOST = new Route("scanHost", "hostname");
        public static final Route LOAD_REMOTE_DATA = new Route("loadRemoteData", "target");
        public static final Route TR_TRANSFER = new Route("trTransfer", "target");
        public static final Route SPYWARE_UPLOAD = new Route("spywareUpload", "target");
    }

    public static class Mail {
        public static final Route MAILS = new Route("mails", "action");
        public static final Route MAILS_READ = new Route("mails", "action", "mID");
    }

    public static class Log {
        public static final Route GET_SYS_LOG = new Route("getSysLog");
    }

    public static class Rank {
        public static final Route RANKING = new Route("ranking", "coh", "lig");
    }

    public static class Cluster {
        public static final Route DATA = new Route("ClusterData");

        public static final Route MEMBERS = new Route("getClusterMember");
        public static final Route ADD_SLOT = new Route("addClusterSlot");
        public static final Route REQUESTS = new Route("getClusterRequests");
        public static final Route SYSTEM = new Route("clusterSystem");
        public static final Route ATTACK_CENTER_INFO = new Route("attackCenterInfo");
        public static final Route SCAN_TAG = new Route("scanTag", "ctag");
    }

    public static class User {
        public static final Route GET_PROFILE_INFO = new Route("getProfileInfo", "userName");
    }

    public static class Notepad {
        public static final Route GET_DATA = new Route("getNotepadData");
        public static final Route UPDATE_CONTENT = new Route("updateNotepadContent", "content");
        public static final Route ADD_IP = new Route("addNotepadIP", "description", "ip");
        public static final Route REMOVE_IP = new Route("removeNotepadIP", "ipdid");
    }

    public static class Tasks {
        public static final Route UPDATE_INFO = new Route("updateInfo", "utype");
        public static final Route ADD_UPDATE = new Route("addUpdate", "utype");
        public static final Route FILL_TASKS = new Route("fillTasks", "utype");
        public static final Route GET_TASKS = new Route("tasks");
        public static final Route BOOST_TASKS = new Route("tasks", "boost");
        public static final Route FINISH_ALL = new Route("finishAll");
        public static final Route FINISH_TASK = new Route("finishTask", "taskid");
        public static final Route ABORT_TASK = new Route("abortTask", "taskid");
    }

    public static class Botnet {
        public static final Route INFO = new Route("botnetInfo");
        public static final Route UPGRADE_PC = new Route("upgradePC", "hostname", "inst", "much", "ofwhat");
        public static final Route CAREER_STATUS = new Route("getCareerStatus");
        public static final Route START_LEVEL = new Route("startLevel", "lvl");
    }

    public static class Daily {
        public static final Route GET_DATA = new Route("getDailyData");
        public static final Route GET_DAILY = new Route("getDaily", "dtype");
    }

    public static class Events {
        public static final Route GET_EVENTS = new Route("getEvents");
        public static final Route OPEN_FREE_BONUS = new Route("openFreeBonus");
        public static final Route OPEN_ALL_BONUS = new Route("openAllBonus");
    }

    public class CompiledRoute {
        private final Route baseRoute;
        private final String compiledRoute;

        private CompiledRoute(Route baseRoute, String compiledRoute) {
            this.baseRoute = baseRoute;
            this.compiledRoute = compiledRoute;
        }

        public String getCompiledRoute() {
            return compiledRoute;
        }

        public Route getBaseRoute() {
            return baseRoute;
        }

        @Override
        public int hashCode() {
            return (compiledRoute).hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof CompiledRoute))
                return false;

            CompiledRoute oCompiled = (CompiledRoute) o;

            return baseRoute.equals(oCompiled.getBaseRoute()) && compiledRoute.equals(oCompiled.compiledRoute);
        }

        @Override
        public String toString() {
            return "CompiledRoute(" + compiledRoute + ")";
        }
    }
}