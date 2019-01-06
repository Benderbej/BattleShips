package ru.javabit.netgame.client;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;
import ru.javabit.view.GameFieldRenderer;

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
    int clientHandlerId = 33333333;

    Client(){
        site = "localhost";
        port = "8082";
    }

    void meet() {
            Socket socket = null;
            String site = "localhost";
            String port = "8082";
            try {
                socket = new Socket(site, Integer.parseInt(port));
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                dataOutputStream.writeInt(ClientRequestCode.MEET);
                dataOutputStream.flush();
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                //oos.writeObject("Sstring!");
                oos.writeObject("stringg");
                oos.flush();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
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

    GameField giveGameField() {
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
                g = (GameField) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //GameFieldRenderer gameFieldRenderer = new GameFieldRenderer(g);
            //gameFieldRenderer.renderGameField();
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
        return g;
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

    private void sendRequestCode(int code){

    }

    private void sendRequest(int code){

    }
}