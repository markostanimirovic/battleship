/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.so;

import rs.ac.bg.fon.silab.database.DBBroker;

/**
 *
 * @author marko
 */
public abstract class BaseSO {

    public synchronized void execute(Object o) throws Exception {
        try {
            loadDriver();
            openConnection();
            executeOperation(o);
            commit();
        } catch (Exception ex) {
            rollback();
            throw ex;
        } finally {
            closeConnection();
        }

    }

    private void loadDriver() throws Exception {
        DBBroker.getInstance().loadDriver();
    }

    private void openConnection() throws Exception {
        DBBroker.getInstance().openConnection();
    }

    protected abstract void executeOperation(Object o) throws Exception;

    private void commit() throws Exception {
        DBBroker.getInstance().commit();
    }

    private void rollback() throws Exception {
        DBBroker.getInstance().rollback();
    }

    private void closeConnection() throws Exception {
        DBBroker.getInstance().closeConnection();
    }

}
