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
public class ServerDto implements Serializable {

    private int status;
    private int operation;
    private Object payload;
    private Exception exception;

    public ServerDto() {
    }

    public ServerDto(int status, int operation) {
        this.status = status;
        this.operation = operation;
    }

    public ServerDto(int status, int operation, Object payload) {
        this.status = status;
        this.operation = operation;
        this.payload = payload;
    }

    public ServerDto(int status, Exception exception) {
        this.status = status;
        this.exception = exception;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

}
