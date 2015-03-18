package application;


import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import fileTransfer.FileReceiverWrapper;
import fileTransfer.FileTransferSenderThread;
import sockdemocmd.ServerThread;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;




public class Controller {
	
	private MessageModel model = new MessageModel();
	private ServerThread chatServer;
	private FileReceiverWrapper fileReceiver;
	
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
		chatServer = new ServerThread(8888, this);
		chatServer.start();
		//fileReceiver = new FileReceiverWrapper(8888, this);
		//fileReceiver.start();
		messageArea.setItems(model.getObservable());
		messageArea.setCellFactory((callback) -> new MessageListCell());
		messageTextArea.wrapTextProperty().set(true);
	}
	
	@FXML
	private void sendNewMessage(){
		if (!messageTextArea.getText().isEmpty()){
			model.send(messageTextArea.getText(), hostNameTextField.getText(),
					Integer.parseInt(portTextField.getText()));
			messageTextArea.setText("");
		}
	}
	
	@FXML
	private void sendFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		
		File fileToSend = fileChooser.showOpenDialog(attachFilesButton.getScene().getWindow());
		if (fileToSend != null){
			Socket sock;
			try {
				sock = new Socket(hostNameTextField.getText(),
						Integer.parseInt(portTextField.getText()));
				new FileTransferSenderThread(sock, fileToSend).run();
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public MessageModel getModel(){
		return model;
	}
}