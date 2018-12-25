package ru.javabit.server;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import ru.javabit.Client;
import ru.javabit.Game;
import ru.javabit.SingleGame;
import ru.javabit.VictoryTrigger;
import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.GameField;
import ru.javabit.ship.Fleet;
import ru.javabit.ship.FleetsDisposal;
import ru.javabit.turn.TurnMaster;
import ru.javabit.view.GameFieldRenderable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {

    private static GameServer server;
    ServerSocket serverSocket;
    ArrayList<Socket> clientSockets;
    InputStream inputStream;
    OutputStream outputStream;





    public static void main(String[] args) throws IOException {
//        GameServer server = GameServer.getInstance();
//        server.init();
//        server.start();

        Game multiplayerGame = new MultiplayerGame();
        try {
            multiplayerGame.initGame();
            try {
                multiplayerGame.startGame();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (BattleShipsException e) {
            e.printStackTrace();
        }


    }

    private GameServer() throws IOException { }

    public static GameServer getInstance() throws IOException {
        if(server == null){
            server = new GameServer();
        }
        return server;
    }

    private void init() throws IOException{//todo try catch
        System.out.println("init()");
        serverSocket = new ServerSocket(8082);
        Socket clSocket;
        clSocket = serverSocket.accept();
        System.out.println("accept!");

            inputStream = clSocket.getInputStream();
            outputStream = clSocket.getOutputStream();

    }

//    private void createRoom();




//    private void innit(){
//        ServerSocket serverSocket = null;
//        try {
//            serverSocket = new ServerSocket(8082);
//            socket = serverSocket.accept();
//            System.out.println("client connected...");
//            inputStream = socket.getInputStream();
//            outputStream = socket.getOutputStream();
//
//            // Сама информация
//            String html = "hhh";
//
//            // Правила хорошего тона, деловой переписки
//            String header = "HTTP/1.1 200 OK\n" +
//                    "Content-Language: ru\n" +
//                    "Content-Type: text/html; charset=utf-8\n" +
//                    "Content-Length: " + html.length() + "\n" +
//                    "Connection: close\n\n";
//
//            String result = header + html;
//            outputStream.write(result.getBytes());
//            outputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void start(){

    }


    private void privateWaitingForClients(){

    }











//
//    сервер включается на прием
//
//    высылает первому игроку данные - gamefield полностью с указанием какая из сеток кораблей-его сетка
//    ждет ответа
//    получает ответ, обрабатывает ход, обновляет
//
//    отправляет второму игроку данные  - gamefield полностью с указанием какая из сеток кораблей-его сетка
//    ...
//
//
//
//
//
//    клиент.
//    пытается подключиться к серверу
//    подключается и ждет сигнала к ходу

}