/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab;

import rs.ac.bg.fon.silab.network.Server;

/**
 *
 * @author marko
 */
public class BattleshipServerApplication {

    public static void main(String[] args) {
        Server.getInstance().start();
    }

}
