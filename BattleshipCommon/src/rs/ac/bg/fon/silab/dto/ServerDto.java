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

    private Integer operation;
    private Integer status;
    private Object payload;
    private Exception exception;

    public ServerDto() {
    }

    public ServerDto(Integer operation, Integer status) {
        this.operation = operation;
        this.status = status;
    }

    public ServerDto(Integer operation, Integer status, Object payload) {
        this.operation = operation;
        this.status = status;
        this.payload = payload;
    }

    public ServerDto(Integer status, Exception exception) {
        this.status = status;
        this.exception = exception;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
