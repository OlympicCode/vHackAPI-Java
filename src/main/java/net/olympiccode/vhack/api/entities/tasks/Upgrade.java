package net.olympiccode.vhack.api.entities.tasks;

public interface Upgrade {
    int getCost();
    int getFillCost();
    boolean upgrade();
    int getRemainingSlots();
}
