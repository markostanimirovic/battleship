/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import rs.ac.bg.fon.silab.shared.ViewManager;

/**
 * FXML Controller class
 *
 * @author marko
 */
public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private LoginService loginService;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginService = new LoginService(this);
    }

    @FXML
    private void handleLogin(ActionEvent event) throws Exception {
        loginService.login(username.getText(), password.getText());
    }

}
