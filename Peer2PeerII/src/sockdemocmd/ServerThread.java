package sockdemocmd;

import java.net.*;
import java.io.*;

import application.Controller;

public class ServerThread extends Thread {
	private ServerSocket accepter;
	private Controller controller;

	public ServerThread(int port, Controller c) throws IOException {
		this.controller = c;
		accepter = new ServerSocket(port);
		System.out.println("Server IP address: " + accepter.getInetAddress());
	}

	public void listen() throws IOException {
		for (;;) {
			Socket s = accepter.accept();
			SocketEchoThread echoer = new SocketEchoThread(s, controller);
			System.out.println("Connection accepted from " + s.getInetAddress());
			echoer.start();
		}
	}
	
	public void run(){
		try {
			this.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		ServerThread s = new ServerThread(Integer.parseInt(args[0]), null);
		s.run();
	}
}