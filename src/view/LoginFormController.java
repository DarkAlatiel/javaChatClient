package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.Main;

public class LoginFormController {

    @FXML private TextField ipTextField;
    @FXML private TextField loginTextField;
    @FXML private TextField passwordTextField;

    @FXML private Button confirmButton;
    @FXML private Button clearButton;


    @FXML public void confirmButtonAction() throws Exception {
        String ip = ipTextField.getText();
        String login = loginTextField.getText();
        String password = loginTextField.getText();
        Main main = Main.getInstance();
        main.createClient(ip, login, password);
    }

        @FXML public void clearButtonAction(){
        ipTextField.clear();
        loginTextField.clear();
        passwordTextField.clear();
    }

}