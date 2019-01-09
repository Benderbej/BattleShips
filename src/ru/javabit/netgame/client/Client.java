package ru.javabit.netgame.client;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;
import ru.javabit.view.GameFieldRenderer;

import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

/**
 * first connection - meet
 *
 * game connection - takeId,
 *
 */

public class Client {

    String site;
    String port;
    int clientHandlerId = 33333333;
    GameField gameField;

    Client(){
        site = "localhost";
        port = "8082";
    }

    void meet() {
            Socket socket = null;
        DataOutputStream dataOutputStream = null;
            String site = "localhost";
            String port = "8082";
            try {
                socket = new Socket(site, Integer.parseInt(port));
                dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                dataOutputStream.writeInt(ClientRequestCode.MEET);
                dataOutputStream.flush();
                DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                clientHandlerId = dis.readInt();
                System.out.println("clientHandlerId"+clientHandlerId);
                dataOutputStream.close();
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    dataOutputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }


    private void takeTurn() {}

    void takeFieldCell(FieldCell fieldCell) {
        System.out.println("giveGameField()");
        FieldCell fc = null;
        DataOutputStream dos = null;
        ObjectOutputStream oos = null;
        Socket socket = null;
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dos.writeInt(ClientRequestCode.GIVEGAMEFIELD);
            dos.flush();
            dos.writeInt(clientHandlerId);
            dos.flush();
            oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            oos.writeObject(fc);
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            try {
                dos.close();
                oos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    boolean giveGameField() {
        System.out.println("giveGameField()");
        GameField g = null;
        DataOutputStream dos = null;
        ObjectInputStream ois = null;
        Socket socket = null;
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dos.writeInt(ClientRequestCode.GIVEGAMEFIELD);
            dos.flush();
            dos.writeInt(clientHandlerId);
            dos.flush();
            ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            try {
                gameField = (GameField) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (gameField == null){
                System.out.println("waiting for another player Game an Gamefield has not inited yet");
            } else {
                GameFieldRenderer gameFieldRenderer = new GameFieldRenderer(gameField);
                gameFieldRenderer.renderGameField();
                return true;
            }
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            try {
                dos.close();
                ois.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("giveGameField() end");
        return false;
    }

    void giveString() {
        System.out.println("giveString()");
        DataOutputStream dos = null;
        ObjectInputStream ois = null;
        Socket socket = null;
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dos.writeInt(ClientRequestCode.GIVEGAMEFIELD);
            dos.flush();
            dos.writeInt(clientHandlerId);
            dos.flush();
            ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            String s = null;
            try {
                s = (String) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("s=" + s);
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            try {
                dos.close();
                ois.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRequestCode(int code) {

    }

    private void sendRequest(int code) {

    }

    void getGameField() {
        GameField gameField = null;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("try to getGameField...");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(giveGameField()){return;}
                }
            }
        });
        t.start();
    }
}