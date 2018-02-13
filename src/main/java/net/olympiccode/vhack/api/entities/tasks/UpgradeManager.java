package net.olympiccode.vhack.api.entities.tasks;

import java.util.List;

public interface UpgradeManager {
    Upgrade getUpgrade(TaskType type);
    List<Task> getTasks();
    List<Task> getTasksByType(TaskType type);
    void useBooster();
    void useBooster(int amount);
}
