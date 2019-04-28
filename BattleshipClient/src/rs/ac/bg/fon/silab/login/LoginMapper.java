/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.login;

import rs.ac.bg.fon.silab.entity.User;

/**
 *
 * @author marko
 */
public class LoginMapper {

    public User toUser(String username, String password) {
        return new User(username, password);
    }

}
