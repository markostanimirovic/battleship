package rs.ac.bg.fon.silab.battleship;

import rs.ac.bg.fon.silab.battleship.model.BattleshipState;
import rs.ac.bg.fon.silab.battleship.model.BattleshipField;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import rs.ac.bg.fon.silab.constant.Operation;
import rs.ac.bg.fon.silab.constant.Status;
import rs.ac.bg.fon.silab.dto.ClientDto;
import rs.ac.bg.fon.silab.entity.PolygonField;
import rs.ac.bg.fon.silab.network.ClientNetworkService;
import rs.ac.bg.fon.silab.shared.ViewManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author marko
 */
public class BattleshipService {

    private List<BattleshipField> xPolygon;
    private List<BattleshipField> yPolygon;

    private List<BattleshipField> choosedFields;

    private BattleshipState currentState;
    private List<BattleshipField> currentMove;

    private BattleshipController battleshipController;
    private BattleshipMapper battleshipMapper;
    private BattleshipValidator battleshipValidator;

    public BattleshipService(BattleshipController battleshipController) {
        this.battleshipController = battleshipController;
        battleshipMapper = new BattleshipMapper();
        battleshipValidator = new BattleshipValidator();

        currentState = BattleshipState.FIRST_FOUR;
        currentMove = new ArrayList<>();
        choosedFields = new ArrayList<>();

        xPolygon = new ArrayList<>();
        yPolygon = new ArrayList<>();
        populatePolygons();
        disableYPolygon();

        listenToEvents();
    }

    public void toggleField(Button b) {
        BattleshipField toggledField = xPolygon.stream().filter(field -> field.getButton().equals(b)).findAny().orElse(null);

        if (choosedFields.indexOf(toggledField) > -1) {
            return;
        }

        if (currentState == BattleshipState.FINISHED) {
            ViewManager.getInstance().showAlert("Nemate pravo da dodajete nove brodove!", "Greška", Alert.AlertType.ERROR);
            return;
        }

        if (toggledField.isChoosed()) {
            currentMove.remove(toggledField);
            toggledField.getButton().getStyleClass().remove("ship-button");
        } else {
            currentMove.add(toggledField);
            toggledField.getButton().getStyleClass().add("ship-button");
        }

        toggledField.setChoosed(!toggledField.isChoosed());
    }

    public void submitMove() {
        if (currentState == BattleshipState.FIRST_FOUR && battleshipValidator.isFourValid(currentMove)) {
            choosedFields.addAll(currentMove);
            currentState = BattleshipState.FIRST_THREE;
            currentMove.clear();

            updateCurrentMoveInfo();
        } else if ((currentState == BattleshipState.FIRST_THREE || currentState == BattleshipState.SECOND_THREE)
                && battleshipValidator.isThreeValid(xPolygon, currentMove)) {
            choosedFields.addAll(currentMove);
            currentState = BattleshipState.values()[currentState.ordinal() + 1];
            currentMove.clear();

            updateCurrentMoveInfo();
        } else if ((currentState == BattleshipState.FIRST_TWO || currentState == BattleshipState.SECOND_TWO
                || currentState == BattleshipState.THIRD_TWO)
                && battleshipValidator.isTwoValid(xPolygon, currentMove)) {
            choosedFields.addAll(currentMove);
            currentState = BattleshipState.values()[currentState.ordinal() + 1];
            currentMove.clear();

            updateCurrentMoveInfo();
        } else if ((currentState == BattleshipState.FIRST_ONE || currentState == BattleshipState.SECOND_ONE
                || currentState == BattleshipState.THIRD_ONE || currentState == BattleshipState.FOURTH_ONE)
                && battleshipValidator.isOneValid(xPolygon, currentMove)) {
            choosedFields.addAll(currentMove);
            currentState = BattleshipState.values()[currentState.ordinal() + 1];
            currentMove.clear();

            updateCurrentMoveInfo();
        } else if (currentState == BattleshipState.FINISHED) {
            ViewManager.getInstance().showAlert("Nemate pravo da dodajete nove brodove!", "Greška", Alert.AlertType.ERROR);
        } else {
            ViewManager.getInstance().showAlert("Uneti potez nije validan!", "Greška", Alert.AlertType.ERROR);
        }
    }

    public void submitPolygon() {
        if (currentState != BattleshipState.FINISHED) {
            ViewManager.getInstance().showAlert("Niste postavili sve brodove!", "Greška", Alert.AlertType.ERROR);
        } else {
            disableXPolygon();
            disableActionButtons();
            currentState = BattleshipState.COMPLETED;
            updateCurrentMoveInfo();
            ClientNetworkService.getInstance().sendRequest(new ClientDto(battleshipMapper.toPolygon(xPolygon), Operation.SUBMIT_POLYGON));
        }
    }

    public void hit(Button button) {
        if (button.getStyleClass().contains("success-button") || button.getStyleClass().contains("failed-button")) {
            return;
        }

        String ij = button.getId().substring(1);
        int i = Integer.parseInt(ij.substring(0, 1));
        int j = Integer.parseInt(ij.substring(1));
        ClientNetworkService.getInstance().sendRequest(new ClientDto(battleshipMapper.toPolygonField(i, j), Operation.HIT));
    }

    private void populatePolygons() {
        IntStream.range(0, 10).forEach(i -> {
            IntStream.range(0, 10).forEach(j -> {
                populatePolygonField(xPolygon, "x", i, j);
                populatePolygonField(yPolygon, "y", i, j);
            });
        });
    }

    private void populatePolygonField(List<BattleshipField> polygon, String prefix, int i, int j) {
        try {
            Field fX = battleshipController.getClass().getDeclaredField(prefix + i + j);
            fX.setAccessible(true);
            polygon.add(new BattleshipField((Button) fX.get(battleshipController), false, i, j));
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private void disableXPolygon() {
        xPolygon.forEach(field -> {
            field.getButton().setDisable(true);
        });
    }

    private void disableYPolygon() {
        yPolygon.forEach(field -> {
            field.getButton().setDisable(true);
        });
    }

    private void enableYPolygon() {
        yPolygon.forEach(field -> {
            field.getButton().setDisable(false);
        });
    }

    private void disableActionButtons() {
        try {
            Field submitMoveField = battleshipController.getClass().getDeclaredField("submitMove");
            Field submitPolygonField = battleshipController.getClass().getDeclaredField("submitPolygon");

            submitMoveField.setAccessible(true);
            submitPolygonField.setAccessible(true);

            ((Button) submitMoveField.get(battleshipController)).setDisable(true);
            ((Button) submitPolygonField.get(battleshipController)).setDisable(true);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private void updateCurrentMoveInfo() {
        try {
            Field currentMoveInfoField = battleshipController.getClass().getDeclaredField("currentMoveInfo");
            currentMoveInfoField.setAccessible(true);
            Label currentMoveInfoLabel = (Label) currentMoveInfoField.get(battleshipController);

            if (currentState == BattleshipState.FIRST_THREE || currentState == BattleshipState.SECOND_THREE) {
                currentMoveInfoLabel.setText("Izaberite brod veličine 3");
            } else if (currentState == BattleshipState.FIRST_TWO || currentState == BattleshipState.SECOND_TWO
                    || currentState == BattleshipState.THIRD_TWO) {
                currentMoveInfoLabel.setText("Izaberite brod veličine 2");
            } else if (currentState == BattleshipState.FIRST_ONE || currentState == BattleshipState.SECOND_ONE
                    || currentState == BattleshipState.THIRD_ONE || currentState == BattleshipState.FOURTH_ONE) {
                currentMoveInfoLabel.setText("Izaberite brod veličine 1");
            } else if (currentState == BattleshipState.FINISHED) {
                currentMoveInfoLabel.setText("Potvrdite raspored");
            } else if (currentState == BattleshipState.COMPLETED) {
                currentMoveInfoLabel.setText("");
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private void updateMoveStatus(boolean youHit) {
        setMoveStatus(youHit ? "Vi ste na potezu" : "Protivnik je na potezu");
    }

    private void setMoveStatus(String status) {
        try {
            Field moveStatusField = battleshipController.getClass().getDeclaredField("moveStatus");
            moveStatusField.setAccessible(true);
            Label moveStatusLabel = (Label) moveStatusField.get(battleshipController);
            moveStatusLabel.setText(status);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private void setXLabel(String labelValue) {
        try {
            Field xLabelField = battleshipController.getClass().getDeclaredField("xLabel");
            xLabelField.setAccessible(true);
            Label xLabel = (Label) xLabelField.get(battleshipController);
            xLabel.setText(labelValue);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private void setYLabel(String labelValue) {
        try {
            Field yLabelField = battleshipController.getClass().getDeclaredField("yLabel");
            yLabelField.setAccessible(true);
            Label yLabel = (Label) yLabelField.get(battleshipController);
            yLabel.setText(labelValue);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private void updateYPolygonButton(PolygonField polygonField, boolean success) {
        try {
            Field yButtonField = battleshipController.getClass().getDeclaredField("y" + polygonField.getI() + polygonField.getJ());
            yButtonField.setAccessible(true);
            Button yButton = (Button) yButtonField.get(battleshipController);
            yButton.getStyleClass().add(success ? "success-button" : "failed-button");
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private void updateXPolygonButton(PolygonField polygonField, boolean success) {
        try {
            Field xButtonField = battleshipController.getClass().getDeclaredField("x" + polygonField.getI() + polygonField.getJ());
            xButtonField.setAccessible(true);
            Button xButton = (Button) xButtonField.get(battleshipController);
            xButton.getStyleClass().remove("ship-button");
            xButton.getStyleClass().add(success ? "success-button" : "failed-button");
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private void listenToEvents() {
        BattleshipBehaviourSubject.getInstance().subscribe((serverDto) -> {
            if (serverDto.getStatus() == Status.ERROR) {
                Platform.runLater(() -> {
                    ViewManager.getInstance().showAlert(serverDto.getException().getMessage(), "Greška", Alert.AlertType.ERROR);
                });
            } else {
                switch (serverDto.getOperation()) {
                    case Operation.LOGIN_SUCCESSFUL:
                        Platform.runLater(() -> {
                            setXLabel((String) serverDto.getPayload());
                        });
                        break;
                    case Operation.ENEMY_USERNAME:
                        Platform.runLater(() -> {
                            setYLabel((String) serverDto.getPayload());
                        });
                        break;
                    case Operation.SUBMIT_POLYGON_SUCCESSFUL:
                        Platform.runLater(() -> {
                            try {
                                ViewManager.getInstance().showWaitingView();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                        break;
                    case Operation.YOU_HIT_FIRST:
                        Platform.runLater(() -> {
                            ViewManager.getInstance().hideWaitingView();
                            enableYPolygon();
                            updateMoveStatus(true);
                        });
                        break;
                    case Operation.ENEMY_HIT_FIRST:
                        Platform.runLater(() -> {
                            ViewManager.getInstance().hideWaitingView();
                            updateMoveStatus(false);
                        });
                        break;
                    case Operation.YOU_SUCCESSFUL_HIT:
                        Platform.runLater(() -> {
                            updateYPolygonButton((PolygonField) serverDto.getPayload(), true);
                        });
                        break;
                    case Operation.YOU_FAILED_HIT:
                        Platform.runLater(() -> {
                            updateYPolygonButton((PolygonField) serverDto.getPayload(), false);
                            disableYPolygon();
                            updateMoveStatus(false);
                        });
                        break;
                    case Operation.ENEMY_SUCCESSFUL_HIT:
                        Platform.runLater(() -> {
                            updateXPolygonButton((PolygonField) serverDto.getPayload(), true);
                        });
                        break;
                    case Operation.ENEMY_FAILED_HIT:
                        Platform.runLater(() -> {
                            updateXPolygonButton((PolygonField) serverDto.getPayload(), false);
                            enableYPolygon();
                            updateMoveStatus(true);
                        });
                        break;
                    case Operation.YOU_WIN:
                        Platform.runLater(() -> {
                            disableYPolygon();
                            String statusMessage = "Čestitamo! Pobedili ste!";
                            setMoveStatus(statusMessage);
                            ViewManager.getInstance().showAlert(statusMessage, "Poruka", Alert.AlertType.INFORMATION);
                        });
                        break;
                    case Operation.ENEMY_WIN:
                        Platform.runLater(() -> {
                            disableYPolygon();
                            String statusMessage = "Protivnik je pobedio";
                            setMoveStatus(statusMessage);
                            ViewManager.getInstance().showAlert(statusMessage, "Poruka", Alert.AlertType.INFORMATION);
                        });
                        break;
                }
            }
        });
    }

}
