/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.controller;

import rs.ac.bg.fon.silab.entity.Match;
import rs.ac.bg.fon.silab.entity.User;
import rs.ac.bg.fon.silab.so.match.SaveMatchSO;
import rs.ac.bg.fon.silab.so.user.GetUserSO;

/**
 *
 * @author marko
 */
public class ServerController {

    private static ServerController instance;

    private ServerController() {
    }

    public static synchronized ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }

        return instance;
    }

    public User getUser(User user) throws Exception {
        GetUserSO getUserSO = new GetUserSO();
        getUserSO.execute(user);

        return (User) getUserSO.getUser();
    }

    public void saveMatch(Match match) throws Exception {
        SaveMatchSO saveMatchSO = new SaveMatchSO();
        saveMatchSO.execute(match);
    }

}
