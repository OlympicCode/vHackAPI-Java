package net.olympiccode.vhack.api.entities.impl;

import lombok.Getter;
import lombok.Setter;
import net.olympiccode.vhack.api.entities.User;
import net.olympiccode.vhack.api.entities.tasks.TaskType;
import net.olympiccode.vhack.api.events.NewMailEvent;
import net.olympiccode.vhack.api.vHackAPI;

@Getter
@Setter
public class UserImpl implements User {
    private vHackAPIImpl api;
    private long money;
    private int packages;
    private String IP;
    private int goldPackages;
    private int unreadMail;
    private int score;
    private int rank;
    private int activeSpyware;
    private int id;
    private boolean active;
    private int reputation;
    private int netcoins;
    private int boosters;

    private int internet;
    private int firewall;

    public UserImpl(vHackAPI api) {
        this.api = (vHackAPIImpl) api;
    }

    public void setUnreadMail(int unreadMail) {
        this.unreadMail = unreadMail;
        if (unreadMail > 0) {
            api.fireEvent(new NewMailEvent(api, unreadMail));
        }
    }

    public int getStat(TaskType t) {
        switch (t.getId()) {
            case "fw":
                return firewall;
           /* case "av":
                return TaskType.ANTIVIRUS;
            case "sdk":
                return TaskType.SDK;
            case "ipsp":
                return TaskType.IPSPOOF;
            case "spam":
                return TaskType.SPAM;
            case "scan":
                return TaskType.SCAN;
            case "adw":
                return TaskType.SPYWARE;
            case "cpu":
                return TaskType.CPU;
            case "ram":
                return TaskType.RAM;
            case "hdd":
                return TaskType.HDD;*/
            case "inet":
                return internet;
            default:
                throw new RuntimeException("Invalid task type");
        }
    }


}
