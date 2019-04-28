/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.battleship;

import rs.ac.bg.fon.silab.battleship.model.BattleshipField;
import java.util.List;
import java.util.stream.Collectors;
import rs.ac.bg.fon.silab.entity.Polygon;
import rs.ac.bg.fon.silab.entity.PolygonField;

/**
 *
 * @author marko
 */
public class BattleshipMapper {

    public Polygon toPolygon(List<BattleshipField> fieldsList) {
        return new Polygon(fieldsList.stream()
                .map(field -> new PolygonField(false, field.isChoosed(), field.getI(), field.getJ()))
                .collect(Collectors.toList()));
    }

    public PolygonField toPolygonField(int i, int j) {
        return new PolygonField(false, false, i, j);
    }

}
