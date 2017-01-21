package me.checkium.vhackapi.chat;

public class ChatMessage {
    protected String rawmessage;
    protected String author;
    protected String message;
	public ChatMessage(String rawwmessage) {
        rawmessage = rawwmessage;
		String[] str1 = rawmessage.split(":");
		message = str1[2];
		String[] str2 = rawmessage.split("!");
		String str3 = str2[0].replace(":v[", "");
		author = str3;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String getRawMessage(){
		return rawmessage;
	}
}
