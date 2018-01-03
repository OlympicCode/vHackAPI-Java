package me.checkium.vhackapi.chat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatMessage {

    private static final Pattern pattern = Pattern.compile(":v\\[(.+)!(.+)@(.*) PRIVMSG \\#(\\w+) :(.*)");

    private String rawmessage;
    private String author;
    private String hash;
    private String domain;
    private String channel;
    private String message;

    public ChatMessage(String rawwmessage) {
        Matcher matcher = pattern.matcher(rawwmessage);
        if (matcher.matches()) {
            this.rawmessage = rawwmessage;
            author = matcher.group(1);
            hash = matcher.group(2);
            domain = matcher.group(3);
            channel = matcher.group(4);
            message = matcher.group(5);
        }
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getRawMessage() {
        return rawmessage;
    }

    public String getHash() {
        return hash;
    }

    public String getDomain() {
        return domain;
    }

    public String getChannel() {
        return channel;
    }

    public UserRoles getUserRole() {
        switch (domain) {
            case "admin.vhack.biz": {
                return UserRoles.ADMIN;
            }
            case "mod.vhack.biz": {
                return UserRoles.MOD;
            }
            case "vip.vhack.biz": {
                return UserRoles.VIP;
            }
            default: {
                return UserRoles.STANDARD_USER;
            }
        }
    }
}
