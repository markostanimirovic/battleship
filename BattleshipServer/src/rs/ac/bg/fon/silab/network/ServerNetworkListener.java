/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.network;

import java.net.Socket;
import java.util.List;
import rs.ac.bg.fon.silab.constant.Operation;
import rs.ac.bg.fon.silab.dto.ClientDto;
import rs.ac.bg.fon.silab.dto.ServerDto;

import rs.ac.bg.fon.silab.constant.Status;
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
            ClientDto clientDto = networkService.receiveResponse();
            ServerDto serverDto = new ServerDto();
            try {
                switch (clientDto.getOperation()) {
                    case Operation.LOGIN:
                        user = (User) clientDto.getPayload();

                        serverDto.setStatus(Status.SUCCESS);
                        serverDto.setOperation(Operation.LOGIN_SUCCESSFUL);
                        serverDto.setPayload(user.getUsername());
                        networkService.sendRequest(serverDto);

                        if (enemyListener != null && enemyListener.getUser() != null) {
                            networkService.sendRequest(new ServerDto(Operation.ENEMY_USERNAME, Status.SUCCESS, enemyListener.getUser().getUsername()));
                            enemyListener.getNetworkService().sendRequest(new ServerDto(Operation.ENEMY_USERNAME, Status.SUCCESS, user.getUsername()));
                        }

                        break;
                    case Operation.SUBMIT_POLYGON:
                        polygon = (Polygon) clientDto.getPayload();

                        serverDto.setStatus(Status.SUCCESS);
                        serverDto.setOperation(Operation.SUBMIT_POLYGON_SUCCESSFUL);
                        networkService.sendRequest(serverDto);

                        if (enemyListener != null && enemyListener.getPolygon() != null) {
                            networkService.sendRequest(new ServerDto(Operation.ENEMY_HIT_FIRST, Status.SUCCESS));
                            enemyListener.getNetworkService().sendRequest(new ServerDto(Operation.YOU_HIT_FIRST, Status.SUCCESS));
                        }

                        break;
                    case Operation.HIT:
                        PolygonField checkedPolygonField = (PolygonField) clientDto.getPayload();
                        PolygonField enemyField = enemyListener.getPolygon().getPolygonFields().stream()
                                .filter(field -> field.equals(checkedPolygonField)).findAny().orElse(null);

                        enemyField.setChecked(true);

                        if (enemyField.isChoosed()) {
                            serverDto.setStatus(Status.SUCCESS);
                            serverDto.setOperation(Operation.YOU_SUCCESSFUL_HIT);
                            serverDto.setPayload(enemyField);

                            networkService.sendRequest(serverDto);
                            enemyListener.getNetworkService().sendRequest(new ServerDto(Operation.ENEMY_SUCCESSFUL_HIT, Status.SUCCESS, enemyField));
                        } else {
                            serverDto.setStatus(Status.SUCCESS);
                            serverDto.setOperation(Operation.YOU_FAILED_HIT);
                            serverDto.setPayload(enemyField);

                            networkService.sendRequest(serverDto);
                            enemyListener.getNetworkService().sendRequest(new ServerDto(Operation.ENEMY_FAILED_HIT, Status.SUCCESS, enemyField));
                        }

                        if (enemyListener.getPolygon().getPolygonFields().stream().filter(field -> field.isChecked() && field.isChoosed()).count() == 20) {
                            networkService.sendRequest(new ServerDto(Operation.YOU_WIN, Status.SUCCESS));
                            enemyListener.getNetworkService().sendRequest(new ServerDto(Operation.ENEMY_WIN, Status.SUCCESS));
                        }

                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                serverDto.setStatus(Status.ERROR);
                serverDto.setException(e);
                networkService.sendRequest(serverDto);
            }

        }
    }
}
