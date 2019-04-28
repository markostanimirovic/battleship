/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.shared;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import rs.ac.bg.fon.silab.BattleshipClientApplication;

/**
 *
 * @author marko
 */
public class ViewManager {

    private static ViewManager instance;

    private Stage stage;
    private Scene currentScene;

    private ViewManager() {
        stage = new Stage();
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public static synchronized ViewManager getInstance() {
        if (instance == null) {
            instance = new ViewManager();
        }

        return instance;
    }

    public void showLoginView() throws Exception {
        Parent root = FXMLLoader.load(BattleshipClientApplication.class.getResource("login/LoginView.fxml"));

        currentScene = new Scene(root);

        stage.setScene(currentScene);
        stage.show();
    }

    public void showBattleshipView() throws Exception {
        Parent root = FXMLLoader.load(BattleshipClientApplication.class.getResource("battleship/BattleshipView.fxml"));

        currentScene = new Scene(root);
        currentScene.getStylesheets().add("assets/styles.css");

        stage.setScene(currentScene);
        stage.centerOnScreen();
        stage.show();
    }

    public void showWaitingView() throws Exception {
        Parent root = FXMLLoader.load(BattleshipClientApplication.class.getResource("shared/WaitingView.fxml"));
        Scene loadingScene = new Scene(root);

        stage.setScene(loadingScene);
        stage.centerOnScreen();
        stage.show();
    }

    public void hideWaitingView() {
        stage.setScene(currentScene);
        stage.centerOnScreen();
        stage.show();
    }

    public void showAlert(String message, String title, Alert.AlertType alertType) {
        Alert errorAlert = new Alert(alertType);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

}
