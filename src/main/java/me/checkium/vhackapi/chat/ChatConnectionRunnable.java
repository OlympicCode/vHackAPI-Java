/*
 * Copyright 2018 David Cooke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.checkium.vhackapi.chat;

import me.checkium.vhackapi.Stats;
import me.checkium.vhackapi.VHackAPI;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatConnectionRunnable implements Runnable {

    private BufferedWriter bufferedWriter;
    private VHackAPI api;
    private String vhackxy;

    public ChatConnectionRunnable(BufferedWriter bufferedWriter, VHackAPI api, String vhackxy) {
        this.bufferedWriter = bufferedWriter;
        this.api = api;
        this.vhackxy = vhackxy;
    }

    public void run() {
        try(Socket socket = new Socket("chat.vhackxt.com", 7531)){
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str2 = "v[" + api.getUsername();
            bufferedWriter.write("NICK " + str2 + "\r\n");
            bufferedWriter.write("USER " + api.getStats(Stats.ID) + " 0 * : vHack XT@Android\r\n");
            bufferedWriter.flush();
            while (true) {
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
                        bufferedWriter.write("PRIVMSG vHackXTGuard :.join " + api.getStats(Stats.ID) + " " + api.getStats(Stats.HASH) + "\r\n");
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
