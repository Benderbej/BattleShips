package ru.javabit.server;

/*
processing client on serverside
 */

import ru.javabit.GameMath;
import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    private int clientServantId;
    Socket socket;

    ClientHandler(Socket socket){
        this.socket = socket;
    }

    public int getClientServantId() {
        return clientServantId;
    }

    public void setClientServantId() {
        clientServantId = GameMath.getRandomInt(1, Integer.MAX_VALUE);
        //System.out.println("ClientHandler"+clientServantId);
    }

    @Override
    public void run() {
        //System.out.println("ClientHandler run()");

        while (true){
            try {
                defineRequest();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            try {
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(clientServantId +"run");
        }
    }

    private void sendClientServantId(){
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeInt(clientServantId);
            dataOutputStream.flush();
            dataOutputStream.close();
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

    public void sendGameFieldToClient(GameField gameField){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            objectOutputStream.writeObject(gameField);
            objectOutputStream.flush();
            objectOutputStream.close();//
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FieldCell recieveFieldCellFromClient(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            try {
                objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                System.err.println("Class FieldCell is not found");
                e.printStackTrace();
            }
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void defineRequest() throws IOException {
        //System.out.println("ClientHandler.defineRequest()");
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        int code = dataInputStream.readInt();
        //System.out.println("code="+code);
        switch (code){
            case 0:
                setClientServantId();
                sendClientServantId();
                //System.out.println("first connect");
            break;
            case 1:
                //System.out.println("turn");
            break;
        }
        dataInputStream.close();
    }
}