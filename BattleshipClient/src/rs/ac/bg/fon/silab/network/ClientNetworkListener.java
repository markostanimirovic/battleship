/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.network;

import rs.ac.bg.fon.silab.battleship.BattleshipBehaviourSubject;
import rs.ac.bg.fon.silab.dto.ServerDto;
import rs.ac.bg.fon.silab.login.LoginBehaviourSubject;

/**
 *
 * @author marko
 */
public class ClientNetworkListener extends Thread {

    @Override
    public void run() {
        while (true) {
            ServerDto serverDto = ClientNetworkService.getInstance().receiveResponse();

            LoginBehaviourSubject.getInstance().next(serverDto);
            BattleshipBehaviourSubject.getInstance().next(serverDto);
        }
    }

}
