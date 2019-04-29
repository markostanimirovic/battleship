/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.login;

import rs.ac.bg.fon.silab.shared.BehaviourSubject;

/**
 *
 * @author marko
 */
public class LoginBehaviourSubject extends BehaviourSubject {

    private static LoginBehaviourSubject instance;

    private LoginBehaviourSubject() {
    }

    public static synchronized LoginBehaviourSubject getInstance() {
        if (instance == null) {
            instance = new LoginBehaviourSubject();
        }

        return instance;
    }

}
