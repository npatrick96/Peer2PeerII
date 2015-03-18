package sockdemocmd;

import java.net.*;
import java.io.*;

import application.Controller;

public class SocketEchoThread extends Thread {
    private Socket socket;
    private Controller controller;
    
    public SocketEchoThread(Socket socket, Controller c) {
        this.socket = socket;
        this.controller = c;
    }

    public void run() {
        try {
            BufferedReader responses = 
                new BufferedReader
                (new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            //writer.println("Connection open.");
            //writer.println("I will echo a single message, then close.");

            StringBuilder sb = new StringBuilder();
            while (!responses.ready()){}
            while (responses.ready()) {
                sb.append(responses.readLine() + '\n');
            }
            System.out.println("From: " + socket.getInetAddress() + ": " + sb);
            
            try{
            controller.getModel().addMessage(sb.toString());
            } catch (IllegalStateException ise) {
            	
            }
            //writer.print(sb);
            //writer.flush();
            socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } 
    }
}