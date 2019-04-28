/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab;

import javafx.application.Application;
import javafx.stage.Stage;
import rs.ac.bg.fon.silab.network.ClientNetworkListener;
import rs.ac.bg.fon.silab.shared.ViewManager;

/**
 *
 * @author marko
 */
public class BattleshipClientApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ViewManager.getInstance().showLoginView();

        ClientNetworkListener networkListener = new ClientNetworkListener();
        networkListener.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
