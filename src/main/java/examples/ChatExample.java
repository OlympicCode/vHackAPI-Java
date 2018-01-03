package examples;

import me.checkium.vhackapi.chat.ChatListener;
import me.checkium.vhackapi.chat.ChatMessage;
import me.checkium.vhackapi.VHackAPI;
import me.checkium.vhackapi.VHackAPIBuilder;

// implements ChatListener
public class ChatExample implements ChatListener {
    // Create your API instance
    VHackAPI api = new VHackAPIBuilder().password("pass").username("user").getAPI();

    public void onChatMessage(ChatMessage message) {
        //this is executed every message

        if (message.getMessage().contains("!test")) {
            //if the message contains !test then send message with @ + message + Testing
            api.getChat().sendChatMessage("@" + message.getAuthor() + " Testing");
        }
    }

}
