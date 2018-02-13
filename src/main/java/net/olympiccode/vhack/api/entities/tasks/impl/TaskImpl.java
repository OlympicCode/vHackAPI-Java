package net.olympiccode.vhack.api.entities.tasks.impl;

import lombok.Getter;
import lombok.Setter;
import net.olympiccode.vhack.api.entities.impl.vHackAPIImpl;
import net.olympiccode.vhack.api.entities.tasks.Task;
import net.olympiccode.vhack.api.entities.tasks.TaskType;
import net.olympiccode.vhack.api.events.NewMailEvent;
import net.olympiccode.vhack.api.events.TaskFinishEvent;

@Getter
@Setter
public class TaskImpl implements Task {

    long finishStamp;
    int from;
    int to;
    TaskType type;
    int id;
    vHackAPIImpl api;
    public TaskImpl(vHackAPIImpl api,  long finishStamp, int from, int to, TaskType type, int id) {
        this.finishStamp = finishStamp;
        this.from = from;
        this.to = to;
        this.type = type;
        this.id = id;
        this.api = api;
    }

    public void finish() {
        ((UpgradeManagerImpl) api.getUpgradeManager()).activeTasks.remove(id);
        api.fireEvent(new TaskFinishEvent(api, this));
    }

    public String toString() {
        return getType().getId();
    }

}
