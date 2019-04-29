/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.battleship;

import rs.ac.bg.fon.silab.shared.BehaviourSubject;

/**
 *
 * @author marko
 */
public class BattleshipBehaviourSubject extends BehaviourSubject {

    private static BattleshipBehaviourSubject instance;

    private BattleshipBehaviourSubject() {
    }

    public static synchronized BattleshipBehaviourSubject getInstance() {
        if (instance == null) {
            instance = new BattleshipBehaviourSubject();
        }

        return instance;
    }

}
