package net.olympiccode.vhack.api.entities.impl;

import net.olympiccode.vhack.api.entities.User;
import net.olympiccode.vhack.api.events.NewMailEvent;
import net.olympiccode.vhack.api.vHackAPI;

public class UserImpl implements User {
    private vHackAPIImpl api;
    private long money;
    private int packages;
    private String ip;
    private int goldpackages;
    private int unreadMail;
    private int score;
    private int rank;
    private int activeSpyware;
    private int id;
    private boolean active;
    private int reputation;
    private int netcoins;
    private int boosters;

    public UserImpl(vHackAPI api) {
        this.api = (vHackAPIImpl) api;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public int getGoldPackages() {
        return goldpackages;
    }

    public void setGoldPackages(int goldpackages) {
        this.goldpackages = goldpackages;
    }

    public int getUnreadMail() {
        return unreadMail;
    }

    public void setUnreadMail(int unreadMail) {
        this.unreadMail = unreadMail;
        if (unreadMail > 0) {
            api.fireEvent(new NewMailEvent(api, unreadMail));
        }
    }

    public int getPackages() {
        return packages;
    }

    public void setPackages(int packages) {
        this.packages = packages;
    }

    public String getIP() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getActiveSpyware() {
        return activeSpyware;
    }

    public void setActiveSpyware(int activeSpyware) {
        this.activeSpyware = activeSpyware;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public int getNetcoins() {
        return netcoins;
    }

    public void setNetcoins(int netcoins) {
        this.netcoins = netcoins;
    }

    public int getBoosters() {
        return boosters;
    }

    public void setBoosters(int boosters) {
        this.boosters = boosters;
    }
}
