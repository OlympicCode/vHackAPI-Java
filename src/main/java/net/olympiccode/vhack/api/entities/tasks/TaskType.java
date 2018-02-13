package net.olympiccode.vhack.api.entities.tasks;

public class TaskType {
    
    public static TaskType INTERNET = new TaskType("inet"),
    FIREWALL = new TaskType("fw"),
    ANTIVIRUS = new TaskType("av"),
    SDK = new TaskType("sdk"),
    IPSPOOF = new TaskType("ipsp"),
    SPAM = new TaskType("spam"),
    SCAN = new TaskType("scan"),
    SPYWARE = new TaskType("adw"),
    CPU = new TaskType("cpu"),
    RAM = new TaskType("ram"),
    HDD = new TaskType("hdd");

    String id;
    public TaskType(String id) {
        this.id = id;
    }

    public static TaskType of(String s) {
        switch (s) {
            case "fw":
                return TaskType.FIREWALL;
            case "av":
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
                return TaskType.HDD;
            case "inet":
                return TaskType.INTERNET;
            default:
                throw new RuntimeException("Invalid task type");
        }
    }
    public String getId() {
        return id;
    }
}
