package me.checkium.vhackapi.chat;

import java.util.ArrayList;
import java.util.List;

import me.checkium.vhackapi.vHackAPI;

public class Initiater {
    private static List<ChatListener> listeners = new ArrayList<ChatListener>();

    public boolean addListener(ChatListener toAdd, vHackAPI api) {
    	if (ChatUtils.bufferedWriter == null) {
    		listeners.add(toAdd);
    		ChatUtils.connectToChat(api);
    		
    	}
        
        return true;
    }
    
    
    public void chatMessage(String rawmessage) {
        for (ChatListener cl : listeners) {
           ChatMessage message = new ChatMessage(rawmessage);
           cl.onChatMessage(message);
        }
    }
}
