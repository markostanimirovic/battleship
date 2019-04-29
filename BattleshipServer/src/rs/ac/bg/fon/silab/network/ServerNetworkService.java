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

/**
 *
 * @author marko
 */
public class ServerNetworkService {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ServerNetworkService(Socket socket) {
        try {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendRequest(ServerDto serverDto) {
        try {
            out.writeObject(serverDto);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ClientDto receiveResponse() throws IOException, ClassNotFoundException {
        return (ClientDto) in.readObject();
    }

}
