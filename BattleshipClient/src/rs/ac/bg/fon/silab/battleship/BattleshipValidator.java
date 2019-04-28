/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.battleship;

import java.util.List;
import java.util.stream.Collectors;
import rs.ac.bg.fon.silab.battleship.model.BattleshipField;

/**
 *
 * @author marko
 */
public class BattleshipValidator {

    public boolean isFourValid(List<BattleshipField> currentMove) {
        if (currentMove.size() != 4) {
            return false;
        }

        for (BattleshipField currentField : currentMove) {
            if (!isNextToTheOthers(currentField, currentMove)) {
                return false;
            }
        }

        for (BattleshipField currentField : currentMove) {
            if (!isNextToTheOthersFourSizeShip(currentField, currentMove)) {
                return false;
            }
        }

        return true;
    }

    public boolean isThreeValid(List<BattleshipField> xPolygon, List<BattleshipField> currentMove) {
        if (currentMove.size() != 3) {
            return false;
        }

        for (BattleshipField currentField : currentMove) {
            if (!isNextToTheOthers(currentField, currentMove)) {
                return false;
            }
        }

        return isFarFromOtherShips(xPolygon, currentMove);
    }

    public boolean isTwoValid(List<BattleshipField> xPolygon, List<BattleshipField> currentMove) {
        if (currentMove.size() != 2) {
            return false;
        }

        for (BattleshipField currentField : currentMove) {
            if (!isNextToTheOthers(currentField, currentMove)) {
                return false;
            }
        }

        return isFarFromOtherShips(xPolygon, currentMove);
    }

    public boolean isOneValid(List<BattleshipField> xPolygon, List<BattleshipField> currentMove) {
        if (currentMove.size() != 1) {
            return false;
        }

        return isFarFromOtherShips(xPolygon, currentMove);
    }

    private boolean isNextToTheOthers(BattleshipField currentField, List<BattleshipField> currentMove) {
        for (BattleshipField field : currentMove.stream().filter(x -> !x.equals(currentField)).collect(Collectors.toList())) {
            if (isCurrentFieldNextToTheOtherField(currentField, field)) {
                return true;
            }
        }

        return false;
    }

    private boolean isNextToTheOthersIncludeDiagonal(BattleshipField currentField, List<BattleshipField> otherFields) {
        for (BattleshipField field : otherFields) {
            if (isCurrentFieldNextToTheOtherFieldIncludeDiagonal(currentField, field)) {
                return true;
            }
        }

        return false;
    }

    private boolean isNextToTheOthersFourSizeShip(BattleshipField currentField, List<BattleshipField> currentMove) {
        for (BattleshipField field : currentMove.stream().filter(x -> !x.equals(currentField)).collect(Collectors.toList())) {
            if (!isCurrentFieldNextToTheOtherFieldFourSizeShip(currentField, field)) {
                return false;
            }
        }

        return true;
    }

    private boolean isFarFromOtherShips(List<BattleshipField> xPolygon, List<BattleshipField> currentMove) {
        List<BattleshipField> otherChoosedFields = xPolygon.stream()
                .filter(field -> field.isChoosed())
                .filter(field -> currentMove.indexOf(field) == -1)
                .collect(Collectors.toList());

        for (BattleshipField currentField : currentMove) {
            if (isNextToTheOthersIncludeDiagonal(currentField, otherChoosedFields)) {
                return false;
            }
        }

        return true;
    }

    private boolean isCurrentFieldNextToTheOtherField(BattleshipField currentField, BattleshipField otherField) {
        return (otherField.getI() == currentField.getI() + 1 && otherField.getJ() == currentField.getJ())
                || (otherField.getI() == currentField.getI() - 1 && otherField.getJ() == currentField.getJ())
                || (otherField.getJ() == currentField.getJ() + 1 && otherField.getI() == currentField.getI())
                || (otherField.getJ() == currentField.getJ() - 1 && otherField.getI() == currentField.getI());
    }

    private boolean isCurrentFieldNextToTheOtherFieldIncludeDiagonal(BattleshipField currentField, BattleshipField otherField) {
        return isCurrentFieldNextToTheOtherField(currentField, otherField)
                || (otherField.getI() == currentField.getI() + 1 && otherField.getJ() == currentField.getJ() + 1)
                || (otherField.getI() == currentField.getI() - 1 && otherField.getJ() == currentField.getJ() - 1)
                || (otherField.getI() == currentField.getI() + 1 && otherField.getJ() == currentField.getJ() - 1)
                || (otherField.getI() == currentField.getI() - 1 && otherField.getJ() == currentField.getJ() + 1);
    }

    private boolean isCurrentFieldNextToTheOtherFieldFourSizeShip(BattleshipField currentField, BattleshipField otherField) {
        return ((otherField.getI() < currentField.getI() + 4 || otherField.getI() < currentField.getI() - 4) && otherField.getJ() == currentField.getJ())
                || ((otherField.getJ() < currentField.getJ() + 4 || otherField.getJ() < currentField.getJ() - 4) && otherField.getI() == currentField.getI())
                || ((otherField.getI() < currentField.getI() + 3 || otherField.getI() < currentField.getI() - 3) && otherField.getJ() < currentField.getJ() + 2)
                || ((otherField.getJ() < currentField.getJ() + 3 || otherField.getJ() < currentField.getJ() - 3) && otherField.getI() < currentField.getI() + 2);
    }

}
