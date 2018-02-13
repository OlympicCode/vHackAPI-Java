package net.olympiccode.vhack.api.events;

import net.olympiccode.vhack.api.entities.tasks.Task;
import net.olympiccode.vhack.api.vHackAPI;

public class TaskFinishEvent extends Event {
    protected final Task task;
    public TaskFinishEvent(vHackAPI api, Task task) {
        super(api);
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
