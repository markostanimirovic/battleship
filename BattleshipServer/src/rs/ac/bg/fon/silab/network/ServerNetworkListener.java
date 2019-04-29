/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.network;

import java.net.Socket;
import java.net.SocketException;
import rs.ac.bg.fon.silab.constant.Operation;
import rs.ac.bg.fon.silab.dto.ClientDto;
import rs.ac.bg.fon.silab.dto.ServerDto;

import rs.ac.bg.fon.silab.constant.Status;
import rs.ac.bg.fon.silab.controller.ServerController;
import rs.ac.bg.fon.silab.entity.Match;
import rs.ac.bg.fon.silab.entity.Polygon;
import rs.ac.bg.fon.silab.entity.PolygonField;
import rs.ac.bg.fon.silab.entity.User;

/**
 *
 * @author marko
 */
public class ServerNetworkListener extends Thread {

    private ServerNetworkListener enemyListener;
    private ServerNetworkService networkService;

    private User user;
    private Polygon polygon;

    public ServerNetworkListener(Socket socket) {
        networkService = new ServerNetworkService(socket);
    }

    public ServerNetworkListener(ServerNetworkListener enemyListener, Socket socket) {
        this.enemyListener = enemyListener;
        networkService = new ServerNetworkService(socket);
    }

    public ServerNetworkService getNetworkService() {
        return networkService;
    }

    public void setEnemyListener(ServerNetworkListener enemyListener) {
        this.enemyListener = enemyListener;
    }

    public User getUser() {
        return user;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ClientDto clientDto = networkService.receiveResponse();
                switch (clientDto.getOperation()) {
                    case Operation.LOGIN:
                        User userFromRequest = (User) clientDto.getPayload();
                        user = ServerController.getInstance().getUser(userFromRequest);
                        if (user != null) {
                            networkService.sendRequest(new ServerDto(Status.SUCCESS, Operation.LOGIN_SUCCESSFUL, user.getUsername()));
                        } else {
                            networkService.sendRequest(new ServerDto(Status.SUCCESS, Operation.LOGIN_UNSUCCESSFUL));
                            break;
                        }

                        if (enemyListener != null && enemyListener.getUser() != null) {
                            networkService.sendRequest(new ServerDto(Status.SUCCESS, Operation.ENEMY_USERNAME, enemyListener.getUser().getUsername()));
                            enemyListener.getNetworkService().sendRequest(new ServerDto(Status.SUCCESS, Operation.ENEMY_USERNAME, user.getUsername()));
                        }

                        break;
                    case Operation.SUBMIT_POLYGON:
                        polygon = (Polygon) clientDto.getPayload();
                        networkService.sendRequest(new ServerDto(Status.SUCCESS, Operation.SUBMIT_POLYGON_SUCCESSFUL));

                        if (enemyListener != null && enemyListener.getPolygon() != null) {
                            networkService.sendRequest(new ServerDto(Status.SUCCESS, Operation.ENEMY_HIT_FIRST));
                            enemyListener.getNetworkService().sendRequest(new ServerDto(Status.SUCCESS, Operation.YOU_HIT_FIRST));
                        }

                        break;
                    case Operation.HIT:
                        PolygonField checkedPolygonField = (PolygonField) clientDto.getPayload();
                        PolygonField enemyField = enemyListener.getPolygon().getPolygonFields().stream()
                                .filter(field -> field.equals(checkedPolygonField)).findAny().orElse(null);

                        enemyField.setChecked(true);

                        if (enemyField.isChoosed()) {
                            networkService.sendRequest(new ServerDto(Status.SUCCESS, Operation.YOU_SUCCESSFUL_HIT, enemyField));
                            enemyListener.getNetworkService().sendRequest(new ServerDto(Status.SUCCESS, Operation.ENEMY_SUCCESSFUL_HIT, enemyField));
                        } else {
                            networkService.sendRequest(new ServerDto(Status.SUCCESS, Operation.YOU_FAILED_HIT, enemyField));
                            enemyListener.getNetworkService().sendRequest(new ServerDto(Status.SUCCESS, Operation.ENEMY_FAILED_HIT, enemyField));
                        }

                        if (enemyListener.getPolygon().getPolygonFields().stream().filter(field -> field.isChecked() && field.isChoosed()).count() == 20) {
                            networkService.sendRequest(new ServerDto(Status.SUCCESS, Operation.YOU_WIN));
                            enemyListener.getNetworkService().sendRequest(new ServerDto(Status.SUCCESS, Operation.ENEMY_WIN));
                            ServerController.getInstance().saveMatch(new Match(getUser(), enemyListener.getUser()));
                        }

                        break;
                }
            } catch (Exception e) {
                if (e instanceof SocketException) {
                    if (enemyListener != null) {
                        enemyListener.getNetworkService().sendRequest(new ServerDto(Status.SUCCESS, Operation.ENEMY_DISCONNECTED));
                    }

                    e.printStackTrace();
                    Server.getInstance().restart();
                    break;
                }

                networkService.sendRequest(new ServerDto(Status.ERROR, e));
            }
        }
    }

}
