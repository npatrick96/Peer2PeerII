package application;

import javafx.application.Platform;
import javafx.scene.control.ListCell;


public class MessageListCell extends ListCell<Message> {
	
	
	
       
   
   
    @Override
    protected void updateItem(Message message, boolean empty) {
        super.updateItem(message, empty);
        if (message != null){
        	Platform.runLater(new Runnable() {
        		@Override public void run() {
        			setText(message.getText());     
        			setWrapText(true);
        			setWidth(10);
        			setPrefWidth(10);
        		}
        	});
        }
    }
    
}