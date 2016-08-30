package me.checkium.vhackapi.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import me.checkium.vhackapi.Stats;
import me.checkium.vhackapi.vHackAPI;

public class ChatUtils {

	 // Chat

	 public static String m9147c(String str) {
	        String str2 = "abcdefghijklmnopqrstuvwxyz0123456789[]_{}|";
	        String str3 = "";
	        for (int i = 0; i < str.length(); i++) {
	            String substring = str.substring(i, i + 1);
	            if (str2.contains(substring.toLowerCase())) {
	                str3 = str3 + substring;
	            }
	        }
	        if (str3.length() < 5) {
	            return str3 + "|";
	        }
	        return str3;
	 }
	 
	 static String vhackxy = "#vHackXT";
	 static BufferedWriter bufferedWriter;
	public static void sendChatMessage(String message) {
		try {
			System.out.println("Sending: " + message);
			bufferedWriter.write("PRIVMSG " + vhackxy + " :" + message + "\r\n");
			bufferedWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void connectToChat(vHackAPI api) {
    	
    	Thread threadA = new Thread(new Runnable(){
            public void run(){
		 try {
			@SuppressWarnings("resource")
			Socket socket = new Socket("chat.vhackxt.com", 6667);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          String str2 = "v[" + api.getUsername();
          bufferedWriter.write("PASS " + api.getStats(Stats.hash) + "\r\nNICK " + str2 + "\r\n");
          bufferedWriter.write("USER " + api.getStats(Stats.id) + " 0 * : vHack XT@Android\r\n");
          bufferedWriter.flush();
         while(true) {
      	   String readLine = bufferedReader.readLine();
      	
     

      	   if (readLine != null) {
      	
                 if (readLine.toLowerCase().startsWith("ping ")) {
         
                     String str3 = "PONG " + readLine.substring(5) + "\r\n";
                     str3.getBytes();
  
                     bufferedWriter.write(str3);
                     bufferedWriter.flush();
                     bufferedWriter.flush();
                 }
                 if (readLine.contains(":(channel is full) transfering you to #")) {
                     vhackxy = readLine.substring(readLine.indexOf(":(channel is full) transfering you to", 1)).replace(":(channel is full) transfering you to ", "").replace("\r", "").replace("\n", "").replace(" ", "");
                 }
                 if (readLine.contains(" 433 *")) {
                     bufferedWriter.write("NICK " + str2 + "_" + "\r\n");
                     bufferedWriter.flush();
                 }
                 if (readLine.contains("376")) {
        
                     bufferedWriter.write("JOIN " + vhackxy + "\r\n");
                     bufferedWriter.flush();
                     bufferedWriter.flush();
                 }
                 if (readLine.contains("PRIVMSG " + vhackxy + " :")) {
                     
                	 Chat chat = new Chat();
                	 chat.chatMessage(readLine);
                 }

         }
         
         }
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
            }
        }, "Thread A");
	  threadA.start();
    }
	
}
