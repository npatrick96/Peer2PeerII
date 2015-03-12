package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MessageModel {
	private ObservableList<Message> messages = FXCollections.observableArrayList();
	ObservableList<Message> getObservable() {return messages;}
	
	public void addMessage(String text){
		Message message = new Message(text);
		messages.add(message);
	}
}
