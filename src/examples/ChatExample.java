package examples;

import me.checkium.vhackapi.vHackAPI;
import me.checkium.vhackapi.vHackAPIBuilder;
import me.checkium.vhackapi.chat.ChatListener;
import me.checkium.vhackapi.chat.ChatMessage;
// implements ChatListener
public class ChatExample implements ChatListener {
          // Create your API instance
	 vHackAPI api = new vHackAPIBuilder().password("pass").username("user").getAPI();

	public void onChatMessage(ChatMessage message) {
		//this is executed every message
		
		if (message.getMessage().contains("!test")) {
			//if the message contains !test then send message with @ + message + Testing
			api.getChat().sendChatMessage("@" + message.getAuthor() + " Testing");
		}
	}
	 
}
