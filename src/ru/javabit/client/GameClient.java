package ru.javabit.client;

import ru.javabit.gameField.GameField;
import ru.javabit.view.GameFieldRenderer;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 *
 * клиент -тонкий, берет на себя работу только общения с сервером, отображения игровых полей. Отсылает на сервер минимум информации.
 * Вся основная логика игры проходит на сервере, сервер создает комнату в которой запускает игру, игра уже руководит игровым процессом и через ClientHandler-ов
 * отсылает посредством сервера обратно клиенту изменения информации
 *
 */

public class GameClient extends JFrame {

    private String site;
    private String port;

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private GameField gameField;


//    private JTextArea outTextArea;
//    private JTextField inTextField;

    private int clientServantId;
    private int enemyServantId;
    private JTextArea outTextArea;
    private JTextField inTextField;


    public GameClient() {
        super();
        site = "localhost";
        port = "8082";
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));


        Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    meet();
                    getClientServantId();

                    recieveGameField();
                    windowConstruct();




                    //closeConnections();
                }
            });
        t.start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            validate();
        }
    }

    private void closeConnections() {
        try {
            dataInputStream.close();
            dataOutputStream.close();
            //socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getClientServantId() {
        System.out.println("getClientServantId();");
        clientServantId = getServerIntResponse();
    }

    private void meet() {//first data
        System.out.println("meet()");
        sendCode(0);
    }

    private void sendTurn() {//data o turn
        sendCode(1);
    }

    private void sendCode(int code){
        System.out.println("sendCode()");
        try {
            dataOutputStream.writeInt(code);
            dataOutputStream.flush();
            //dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recieveGameField() {
        System.out.println("recieveGameField()");
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            try {
                gameField = (GameField) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("gamefield have read");

        if(gameField == null){
            System.out.println("gamefield = null");
        }
        GameFieldRenderer r = new GameFieldRenderer(gameField);
        r.renderGameField();
    }

    private int getServerIntResponse(){
        System.out.println("getServerIntResponse()");
        int response = -1;
        try {
            if(dataInputStream==null){System.out.println("dataInputStream=null");}
            response = dataInputStream.readInt();
            System.out.println(response);
            clientServantId = response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void main(String[] args) {
        GameClient client = new GameClient();
        client.startGame();




    }

    private void startGame() {
        while (true){

        }
    }


    private void firstConnect(){
//        clientServantId
    }





    private void windowConstruct(){
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        outTextArea = new JTextArea();
        add(outTextArea);
        inTextField = new JTextField();
        add(BorderLayout.SOUTH, inTextField);
        setVisible(true);
    }













//    1.подключаемся к серверу
//    2.регистрируемся на сервере
//    3.получаем от сервера расстановку своих кораблей и поле вражеских кораблей
//
//    получаем от сервера запрос на ход
//    ходим
//    передаем серверу ответ
















//    public static void main(String[] args) {
//
//    }
}
