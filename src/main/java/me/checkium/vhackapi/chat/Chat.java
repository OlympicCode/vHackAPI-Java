package me.checkium.vhackapi.chat;

import me.checkium.vhackapi.vHackAPI;

public class Chat {

	
	public void addListener(ChatListener listener, vHackAPI api) {
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
