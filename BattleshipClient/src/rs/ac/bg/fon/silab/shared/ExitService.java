/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.shared;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 *
 * @author marko
 */
public class ExitService {

    public void exitWhenServerIsClosed() {
        Platform.runLater(() -> {
            ViewManager.getInstance().showAlert("Server je ugašen!", "Greška", Alert.AlertType.ERROR);
            System.exit(0);
        });
    }

    public void exitWhenEnemyIsDisconnected() {
        Platform.runLater(() -> {
            ViewManager.getInstance().showAlert("Protivnik je napustio igru!", "Greška", Alert.AlertType.ERROR);
            System.exit(0);
        });
    }

}
