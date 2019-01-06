package ru.javabit.netgame.client;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import ru.javabit.gameField.GameField;

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


    /*
        try {

            System.out.println("meet()");
            Socket socket = new Socket(site, Integer.parseInt(port));
            */
            //DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));


            //DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            //dataOutputStream.writeInt(ClientRequestCode.MEET);
            //dataOutputStream.flush();


            //clientHandlerId = dataInputStream.readInt();


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


            /*
            ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            try {
                try {
                    String obj = (String) objectInputStream.readObject();
                    System.out.println(obj);
                } catch (OptionalDataException excep) {
                    System.out.println("opt eof="+ excep.eof + " length="+excep.length);
                    excep.printStackTrace();
                }

                //System.out.println("obj.i="+obj.i);



//                GameField gameField = (GameField) objectInputStream.readObject();
//                int i = gameField.getEnemyFieldGrid().getCellsArr().length;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
*/





//            dataInputStream.close();
//            dataOutputStream.close();
//            socket.close();

            /*

        } catch (IOException ex){
            ex.printStackTrace();
        }
*/

    }

    void takeId() {
        try {
            Socket socket = new Socket(site, Integer.parseInt(port));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream.writeInt(ClientRequestCode.GIVESTRING);
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

    void giveGameField(){
        System.out.println("giveGameField()");
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

    void giveString(){
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