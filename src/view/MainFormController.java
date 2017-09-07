package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class MainFormController {

    private MainFormControllerListener listener;

    public void addListener(MainFormControllerListener listener){
        this.listener = listener;
    }

    @FXML TextArea newMessageTextArea;
    @FXML TextArea messagesTextArea;
    @FXML Button sendMessageButton;

    @FXML public void sendMessageAction(){
        if(listener!=null && newMessageTextArea.getText().length()>0) {
            listener.sendMessage(newMessageTextArea.getText());
        }
    }

    @FXML
    public void showMessage(String message){
        if(messagesTextArea!=null) {
            String text = messagesTextArea.getText() + "\n" + message;
            messagesTextArea.setText(text);
        }
    }

}