package me.checkium.vhackapi.chat;

import me.checkium.vhackapi.VHackAPI;

public class Chat {

    public void addListener(ChatListener listener, VHackAPI api) {
        Initiater ini = new Initiater();
        ini.addListener(listener, api);
    }

    public void sendChatMessage(String message) {
        ChatUtils.sendChatMessage(message);
    }

    public void chatMessage(String raw) {
        Initiater ini = new Initiater();
        ini.chatMessage(raw);
    }

}
