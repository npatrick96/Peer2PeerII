package application;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sockdemo.TalkThread;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class Controller {
	
	private MessageModel model = new MessageModel();
	private sockdemo.TalkThread talker;
	private ArrayBlockingQueue<String> channel;
	
	@FXML
	TextArea messageTextArea;
	
	@FXML
	MenuBar topMenu;
	
	@FXML
	ListView<Message> messageArea;
	
	@FXML
	VBox bottomVBox;
	
	@FXML
	VBox buttonsVBox;
	
	@FXML
	HBox messageAndButtonsHBox;
	
	@FXML
	Label statusLabel;
	
	@FXML
	Button sendButton;
	
	@FXML
	Button attachFilesButton;
	
	@FXML
	void initialize(){
		messageArea.setItems(model.getObservable());
		messageArea.setCellFactory((callback) -> new MessageListCell());
		messageTextArea.wrapTextProperty().set(true);
		
	}
	
	@FXML
	private void handleNewMessage(){
		model.addMessage(messageTextArea.getText());
		messageTextArea.setText("");
	}
	
	@FXML
	private void sendNewMessage(){
		send(messageTextArea.getText(), "sam", 8888);
		messageTextArea.setText("");
	}
	
	private void send(String msg, String host, int port) {
		if (talker != null && talker.isGoing()) {
			talker.halt();
		}
		talker = new TalkThread(msg, host, port, channel);
		new Receiver().start();
		talker.start();		
	}
	
	/*private JTextArea makeTextArea(String title, JPanel where) {
		JTextArea t = new JTextArea();
		JScrollPane scroller = new JScrollPane(t);
		scroller.setBorder(BorderFactory.createTitledBorder(title));
		where.add(scroller);
		return t;
	}*/
	
	
	private class Receiver extends Thread {
		public void run() {
			while (talker.isGoing()) {
				String line;
				try {
					line = channel.take();
					model.addMessage(line);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
