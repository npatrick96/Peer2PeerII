package fileTransfer;

import java.net.*;
import java.io.*;

import application.Controller;

public class FileReceiverWrapper extends Thread {
	private ServerSocket accepter;
	private Controller controller;
	private Socket s;

	public FileReceiverWrapper(int port, Controller c) throws IOException {
		this.controller = c;
		accepter = new ServerSocket(port);
		System.out.println("Server IP address: " + accepter.getInetAddress());
	}

	public void listen() throws IOException {
		for (;;) {
			s = accepter.accept();
			FileTransferReceiverThread receiverThread = new FileTransferReceiverThread(s, new File("download"));
			System.out.println("Connection accepted from " + s.getInetAddress());
			receiverThread.start();
		}
	}
	
	public void run(){
		try {
			this.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public Socket getSocket(){
		return this.s;
	}

	public static void main(String[] args) throws IOException {
		//ServerThread s = new ServerThread(Integer.parseInt(args[0]), null);
		//s.run();
	}
}