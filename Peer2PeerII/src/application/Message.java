package application;


public class Message {
	String senderID;
	String message;
	
	public Message(String message){
		this.message = message;
		//this.controller = controller;
		//TODO: senderID
	}
	
	public String getText(){
		return this.message;
	}

}
