package net.olympiccode.vhack.api.entities.tasks.impl;

import net.olympiccode.vhack.api.entities.impl.vHackAPIImpl;
import net.olympiccode.vhack.api.entities.tasks.Task;
import net.olympiccode.vhack.api.entities.tasks.TaskType;
import net.olympiccode.vhack.api.entities.tasks.Upgrade;
import net.olympiccode.vhack.api.entities.tasks.UpgradeManager;
import net.olympiccode.vhack.api.requests.Response;
import net.olympiccode.vhack.api.requests.Route;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class UpgradeManagerImpl implements UpgradeManager {
    private vHackAPIImpl api;

    public HashMap<Integer, Task> activeTasks = new HashMap<>();
    ScheduledExecutorService taskService;
    public UpgradeManagerImpl(vHackAPIImpl api) {
        this.api = api;
        taskService= Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "[vHackAPI] Upgrade manager"));
        taskService.scheduleAtFixedRate(() -> {
             HashMap<Integer, Task> currentmap = (HashMap<Integer, Task>) activeTasks.clone();
            currentmap.forEach((integer, task) -> {
                TaskImpl taski = (TaskImpl) task;
                if (taski.getFinishStamp() <= System.currentTimeMillis()) {
                    taski.finish();
                }
            });
        }, 0, 1, TimeUnit.SECONDS);
    }

    public ScheduledExecutorService getTaskService() {
        return taskService;
    }

    public Upgrade getUpgrade(TaskType type) {
        Route.CompiledRoute cr = Route.Tasks.UPDATE_INFO.compile(api, type.getId());
        Response r = api.getRequester().getResponse(cr);
        int cost = -1;
        int fillprice = -1;
        int can = -1;
        try {
            JSONObject object = new JSONObject(r.getString());
            try {
                cost = Integer.parseInt(object.getString("costs"));
                fillprice = Integer.parseInt(object.getString("fillprice"));
                can = Integer.parseInt(object.getString("can"));
            } catch (NumberFormatException a) {
                cost = 0;
                fillprice = 0;
                can = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new UpgradeImpl(api, type, cost, fillprice, can);
    }

    public void reloadTasks() {
        Route.CompiledRoute cr = Route.Tasks.GET_TASKS.compile(api);
        Response r = api.getRequester().getResponse(cr);
        try {
            JSONObject object = new JSONObject(r.getString());
            reloadTasks(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void reloadTasks(JSONObject object) {
        try {
            if (!object.has("data")) {
                for (Task t : activeTasks.values()) {
                    ((TaskImpl) t).finish();
                }
                return;
            }
            JSONArray array = object.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                object = array.getJSONObject(i);
                TaskType type = TaskType.of(object.getString("type"));
                long start = TimeUnit.SECONDS.toMillis(Long.parseLong(object.getString("start")));
                long end = TimeUnit.SECONDS.toMillis(Long.parseLong(object.getString("end")));
                int to = api.getUser().getFirewall() + getTasksByType(type).size() + 1;
                int id = Integer.parseInt(object.getString("taskid"));
                Task task = new TaskImpl(api, end, to - 1, to, type, id);
                if (activeTasks.containsKey(id)) {
                    TaskImpl t = (TaskImpl) activeTasks.get(id);
                    t.setFinishStamp(end);
                } else {
                    activeTasks.put(id, task);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void useBooster(int amount) {
        Route.CompiledRoute r = Route.Tasks.BOOST_TASKS.compile(api, String.valueOf(amount));
        Response re = api.getRequester().getResponse(r);
        try {
            reloadTasks(new JSONObject(re.getString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void useBooster() {
        useBooster(1);
    }

    public List<Task> getTasks() {
        reloadTasks();
        return new ArrayList(activeTasks.values());
    }

    public List<Task> getKnownTasks() {
        return new ArrayList<>(activeTasks.values());
    }

    public List<Task> getTasksByType(TaskType type) {
        return getKnownTasks().stream().filter(task -> task.getType().equals(type)).collect(Collectors.toList());
    }

    public boolean upgrade(Upgrade up) {
        return up.upgrade();
    }

}
