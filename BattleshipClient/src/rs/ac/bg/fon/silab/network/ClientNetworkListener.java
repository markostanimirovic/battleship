/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.network;

import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import rs.ac.bg.fon.silab.battleship.BattleshipBehaviourSubject;
import rs.ac.bg.fon.silab.constant.Operation;
import rs.ac.bg.fon.silab.dto.ServerDto;
import rs.ac.bg.fon.silab.login.LoginBehaviourSubject;
import rs.ac.bg.fon.silab.shared.ExitService;
import rs.ac.bg.fon.silab.shared.ViewManager;

/**
 *
 * @author marko
 */
public class ClientNetworkListener extends Thread {

    private static ClientNetworkListener instance;
    private ExitService exitService;

    private ClientNetworkListener() {
        exitService = new ExitService();
    }

    public static synchronized ClientNetworkListener getInstance() {
        if (instance == null) {
            instance = new ClientNetworkListener();
        }

        return instance;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ServerDto serverDto = ClientNetworkService.getInstance().receiveResponse();

                if (serverDto.getOperation() == Operation.ENEMY_DISCONNECTED) {
                    exitService.exitWhenEnemyIsDisconnected();
                }

                LoginBehaviourSubject.getInstance().next(serverDto);
                BattleshipBehaviourSubject.getInstance().next(serverDto);
            } catch (IOException | ClassNotFoundException ex) {
                exitService.exitWhenServerIsClosed();
                ex.printStackTrace();
                break;
            }
        }
    }

}
