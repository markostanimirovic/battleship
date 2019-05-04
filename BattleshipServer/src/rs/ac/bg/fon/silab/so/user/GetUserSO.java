/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.so.user;

import rs.ac.bg.fon.silab.database.DBBroker;
import rs.ac.bg.fon.silab.entity.BaseEntity;
import rs.ac.bg.fon.silab.entity.User;
import rs.ac.bg.fon.silab.so.BaseSO;

/**
 *
 * @author marko
 */
public class GetUserSO extends BaseSO<User> {

    private User user;

    public User getUser() {
        return user;
    }

    @Override
    protected void executeOperation(User user) throws Exception {
        this.user = (User) DBBroker.getInstance().get(user);
    }

}
