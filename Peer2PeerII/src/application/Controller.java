package application;


import java.io.IOException;

import sockdemocmd.ServerThread;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class Controller {
	
	private MessageModel model = new MessageModel();
	private ServerThread server;
	
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
	TextField hostNameTextField;
	
	@FXML
	TextField portTextField;
	
	@FXML
	Button sendButton;
	
	@FXML
	Button attachFilesButton;
	
	@FXML
	void initialize() throws IOException{
		System.out.println("Controller is initializing");
		server = new ServerThread(8888, this);
		server.start();
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
		model.send(messageTextArea.getText(), hostNameTextField.getText(), Integer.parseInt(portTextField.getText()));
		messageTextArea.setText("");
	}
	
	public MessageModel getModel(){
		return model;
	}
}