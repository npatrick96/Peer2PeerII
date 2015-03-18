package application;

import java.util.concurrent.ArrayBlockingQueue;

import sockdemo.TalkThread;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MessageModel {
	private ObservableList<Message> messages = FXCollections.observableArrayList();
	ObservableList<Message> getObservable() {return messages;}
	sockdemo.TalkThread talker;
	private ArrayBlockingQueue<String> channel = new ArrayBlockingQueue<String>(2, true);
	
	public void addMessage(String text){
		try {
			if (!text.isEmpty()){
				Message message = new Message(text);
				messages.add(message);
				}
			}
			 catch (IllegalStateException ise) {
				System.out.println("Exception thrown  :" + ise);
			}
		}
	
	
	public double size(){
		return messages.size();
	}
	
	public void send(String msg, String host, int port) {
		if (talker != null && talker.isGoing()) {
			talker.halt();
		}
		talker = new TalkThread(msg, host, port, channel);
		new Receiver().start();
		talker.start();		
	}	
	
	public class Receiver extends Thread {
		public void run() {
			while (talker.isGoing()) {
				String line;
				try {
					line = channel.take();
					addMessage(line);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
	}



}
