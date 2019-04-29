/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static rs.ac.bg.fon.silab.constant.Network.*;

/**
 *
 * @author marko
 */
public class Server {

    private ServerSocket serverSocket;
    private List<ServerNetworkListener> networkListeners;

    private static Server instance;

    private Server() {
    }

    public static synchronized Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }

        return instance;
    }

    public void start() {
        try {
            networkListeners = new ArrayList<>();
            serverSocket = new ServerSocket(PORT);
            listen();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void stop() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void restart() {
        stop();
        start();
    }

    private void listen() {
        while (networkListeners.size() < 2) {
            try {
                Socket socket = serverSocket.accept();
                ServerNetworkListener networkListener = new ServerNetworkListener(socket);
                networkListener.start();
                networkListeners.add(networkListener);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        networkListeners.get(0).setEnemyListener(networkListeners.get(1));
        networkListeners.get(1).setEnemyListener(networkListeners.get(0));
    }

}
