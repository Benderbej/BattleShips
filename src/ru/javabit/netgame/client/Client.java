package ru.javabit.netgame.client;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.Socket;

/**
 * first connection - meet
 *
 * game connection - takeId,
 *
 */

public class Client {

    String site;
    String port;
    int clientHandlerId;

    Client(){
        site = "localhost";
        port = "8082";
    }

    void meet() {
        try {
            Socket socket = new Socket(site, Integer.parseInt(port));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream.writeInt(ClientRequestCode.MEET);
            clientHandlerId = dataInputStream.readInt();
            System.out.println(clientHandlerId);
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }

    void takeId() {
        try {
            Socket socket = new Socket(site, Integer.parseInt(port));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream.writeInt(ClientRequestCode.TAKEID);
            socket.shutdownOutput();
            dataOutputStream.writeInt(clientHandlerId);
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void takeTurn(){}

    private void giveGameField(){}

    private void sendRequestCode(int code){

    }

    private void sendRequest(int code){

    }
}