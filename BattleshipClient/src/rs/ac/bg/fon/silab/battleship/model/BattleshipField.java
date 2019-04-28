/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.battleship.model;

import javafx.scene.control.Button;

/**
 *
 * @author marko
 */
public class BattleshipField {

    private Button button;
    private Boolean choosed;
    private Integer i;
    private Integer j;

    public BattleshipField(Button button, Boolean choosed, Integer i, Integer j) {
        this.button = button;
        this.choosed = choosed;
        this.i = i;
        this.j = j;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button b) {
        this.button = b;
    }

    public Boolean isChoosed() {
        return choosed;
    }

    public void setChoosed(Boolean choosed) {
        this.choosed = choosed;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getJ() {
        return j;
    }

    public void setJ(Integer j) {
        this.j = j;
    }

}
