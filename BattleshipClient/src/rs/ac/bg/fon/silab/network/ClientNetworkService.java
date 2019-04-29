/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import rs.ac.bg.fon.silab.dto.ClientDto;
import rs.ac.bg.fon.silab.dto.ServerDto;

import static rs.ac.bg.fon.silab.constant.Network.*;

/**
 *
 * @author marko
 */
public class ClientNetworkService {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private static ClientNetworkService instance;

    private ClientNetworkService() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static synchronized ClientNetworkService getInstance() {
        if (instance == null) {
            instance = new ClientNetworkService();
        }

        return instance;
    }

    public void sendRequest(ClientDto clientDto) {
        try {
            out.writeObject(clientDto);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ServerDto receiveResponse() throws IOException, ClassNotFoundException {
        return (ServerDto) in.readObject();
    }

}
