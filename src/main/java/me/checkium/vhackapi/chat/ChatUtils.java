package me.checkium.vhackapi.chat;

import me.checkium.vhackapi.VHackAPI;

import java.io.*;

public class ChatUtils {

    static String vhackxy = "#vHackXT";
    static BufferedWriter bufferedWriter;

    public static String m9147c(String str) {
        String str2 = "abcdefghijklmnopqrstuvwxyz0123456789[]_{}|";
        StringBuffer str3 = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            String substring = str.substring(i, i + 1);
            if (str2.contains(substring.toLowerCase())) {
                str3.append(substring);
            }
        }
        if (str3.length() < 5) {
            return str3 + "|";
        }
        return str3.toString();
    }

    public static void sendChatMessage(String message) {
        try {
            System.out.println("Sending: " + message);
            bufferedWriter.write("PRIVMSG " + vhackxy + " :" + message + "\r\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void connectToChat(final VHackAPI api) {
        Thread threadA = new Thread(new ChatConnectionRunnable(bufferedWriter, api, vhackxy), "Thread A");
        threadA.start();
    }

}
