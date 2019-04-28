/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.login;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import rs.ac.bg.fon.silab.battleship.BattleshipBehaviourSubject;
import rs.ac.bg.fon.silab.constant.Operation;
import rs.ac.bg.fon.silab.constant.Status;
import rs.ac.bg.fon.silab.dto.ClientDto;
import rs.ac.bg.fon.silab.network.ClientNetworkService;
import rs.ac.bg.fon.silab.shared.ViewManager;

/**
 *
 * @author marko
 */
public class LoginService {

    private LoginController loginController;
    private LoginMapper loginMapper;

    public LoginService(LoginController loginController) {
        this.loginController = loginController;
        this.loginMapper = new LoginMapper();
        listenToEvents();
    }

    public void login(String username, String password) {
        try {
            if (username.isEmpty() || password.isEmpty()) {
                ViewManager.getInstance().showAlert("Korisničko ime i lozinka su obavezna polja", "Greška", Alert.AlertType.ERROR);
            } else {
                ClientNetworkService.getInstance().sendRequest(new ClientDto(loginMapper.toUser(username, password), Operation.LOGIN));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void listenToEvents() {
        LoginBehaviourSubject.getInstance().subscribe((serverDto) -> {
            if (serverDto.getStatus() == Status.ERROR) {
                Platform.runLater(() -> {
                    ViewManager.getInstance().showAlert(serverDto.getException().getMessage(), "Greška", Alert.AlertType.ERROR);
                });
            } else {
                switch (serverDto.getOperation()) {
                    case Operation.LOGIN_SUCCESSFUL:
                        Platform.runLater(() -> {
                            ViewManager.getInstance().showAlert("Uspešno ste se prijavili!", "Poruka", Alert.AlertType.INFORMATION);
                            try {
                                ViewManager.getInstance().showBattleshipView();
                                BattleshipBehaviourSubject.getInstance().next(serverDto);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                        break;
                    case Operation.LOGIN_UNSUCCESSFUL:
                        Platform.runLater(() -> {
                            ViewManager.getInstance().showAlert("Pogrešni kredencijali!", "Greška", Alert.AlertType.ERROR);
                        });
                        break;
                }
            }
        });
    }

}
