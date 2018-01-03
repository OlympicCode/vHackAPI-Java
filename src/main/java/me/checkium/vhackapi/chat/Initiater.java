package me.checkium.vhackapi.chat;

import me.checkium.vhackapi.VHackAPI;

import java.util.ArrayList;
import java.util.List;

public class Initiater {

    private static List<ChatListener> listeners = new ArrayList<ChatListener>();

    public void addListener(ChatListener toAdd, VHackAPI api) {
        if (ChatUtils.bufferedWriter == null) {
            listeners.add(toAdd);
            ChatUtils.connectToChat(api);
        }
    }


    public void chatMessage(String rawmessage) {
        for (ChatListener cl : listeners) {
            ChatMessage message = new ChatMessage(rawmessage);
            cl.onChatMessage(message);
        }
    }
}
