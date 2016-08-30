package examples;

import me.checkium.vhackapi.vHackAPI;
import me.checkium.vhackapi.vHackAPIBuilder;
import me.checkium.vhackapi.chat.ChatListener;
import me.checkium.vhackapi.chat.ChatMessage;

public class ChatExample implements ChatListener {

	 vHackAPI api = new vHackAPIBuilder().password("pass").username("user").getAPI();

	public void onChatMessage(ChatMessage message) {
		if (message.getMessage().contains("!test")) {
			api.getChat().sendChatMessage("@" + message.getAuthor() + " Testing");
		}
	}
	 
}
