package net.olympiccode.vhack.api.entities.tasks;

public interface Task {
    long getFinishStamp();
    TaskType getType();
    int getId();
    int getTo();
}
