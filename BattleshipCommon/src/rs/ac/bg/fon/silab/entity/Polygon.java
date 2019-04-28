/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author marko
 */
public class Polygon implements Serializable {

    private List<PolygonField> polygonFields;

    public Polygon() {
    }

    public Polygon(List<PolygonField> polygonFields) {
        this.polygonFields = polygonFields;
    }

    public List<PolygonField> getPolygonFields() {
        return polygonFields;
    }

    public void setPolygonFields(List<PolygonField> polygonFields) {
        this.polygonFields = polygonFields;
    }

}
