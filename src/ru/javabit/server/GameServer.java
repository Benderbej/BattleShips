package ru.javabit.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameServer {

    private static GameServer server;
    ServerSocket serverSocket;
    ArrayList<ClientHandler> clientHandlerList;
    HashMap<Integer, ClientHandler> clientHandlerMap;
    InputStream inputStream;
    OutputStream outputStream;
    ArrayList<Room> roomList;

    public static void main(String[] args) throws IOException {
        GameServer server = GameServer.getInstance();
        server.init();
//        server.start();


    }

    private GameServer() throws IOException { }

    public static GameServer getInstance() throws IOException {
        if(server == null){
            server = new GameServer();
        }
        return server;
    }

    private void init() throws IOException {//todo try catch
        System.out.println("init()");
        serverSocket = new ServerSocket(8082);
        Socket clSocket;

        //Создаю сокет!
        while (true) {
            clSocket = serverSocket.accept();
            System.out.println("connected");//клиент коннектится
            ClientHandler clientHandler = new ClientHandler(clSocket);
            Thread thread = new Thread(clientHandler);
            thread.start();
            //clSocket.close();

            //clientHandlerMap.put(clientHandler.getClientServantId(), clientHandler);
        }


        //clientHandlerList.add(clientHandler);
        //roomList = new ArrayList<Room>();
        //roomList.add(new Room());

    }

    private void createNewClientHandler(){

    }

//    private void createRoom();


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
//    клиент.
//    пытается подключиться к серверу
//    подключается и ждет сигнала к ходу
}