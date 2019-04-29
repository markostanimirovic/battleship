/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.dto;

import java.io.Serializable;

/**
 *
 * @author marko
 */
public class ClientDto implements Serializable {

    private int operation;
    private Object payload;

    public ClientDto() {
    }

    public ClientDto(int operation, Object payload) {
        this.operation = operation;
        this.payload = payload;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

}
