package ru.javabit.server;

/*
processing client on serverside
 */

import ru.javabit.GameMath;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {



    private int clientServantId;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    ClientHandler(Socket socket){
        this.socket = socket;
        try {
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getClientServantId() {
        return clientServantId;
    }

    public void setClientServantId() {
        clientServantId = GameMath.getRandomInt(1, Integer.MAX_VALUE);
        System.out.println("ClientHandler"+clientServantId);
    }

    @Override
    public void run() {
        System.out.println("ClientHandler run()");
        try {
            defineRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendClientServantId(){
        try {
            dataOutputStream.writeInt(clientServantId);
            dataOutputStream.flush();
            //dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    private void defineRequest() throws IOException {
        System.out.println("ClientHandler.defineRequest()");
        int code = dataInputStream.readInt();
        System.out.println("code="+code);
        switch (code){
            case 0:
                setClientServantId();
                sendClientServantId();
                System.out.println("first connect");
            break;
            case 1:
                System.out.println("turn");
            break;
        }
    }
}