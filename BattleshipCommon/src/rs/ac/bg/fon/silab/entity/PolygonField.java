/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.entity;

import java.io.Serializable;

/**
 *
 * @author marko
 */
public class PolygonField implements Serializable {

    private boolean checked;
    private boolean choosed;
    private int i;
    private int j;

    public PolygonField() {
    }

    public PolygonField(boolean checked, boolean choosed, int i, int j) {
        this.checked = checked;
        this.choosed = choosed;
        this.i = i;
        this.j = j;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChoosed() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed = choosed;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PolygonField other = (PolygonField) obj;
        if (this.i != other.i) {
            return false;
        }
        if (this.j != other.j) {
            return false;
        }
        return true;
    }

}
