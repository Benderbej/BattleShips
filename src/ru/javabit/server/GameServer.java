package ru.javabit.server;

import ru.javabit.exceptions.BattleShipsException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class GameServer {

    private static GameServer server;
    ServerSocket serverSocket;
    HashMap<Integer, ClientHandler> clientHandlerMap;
    ArrayList<ClientHandler> freeClients;
    ArrayList<Room> roomList;

    public static void main(String[] args) throws IOException {
        GameServer server = GameServer.getInstance();
        server.init();
//        server.start();
    }

    private GameServer() throws IOException {
        clientHandlerMap = new HashMap<>();
        freeClients = new ArrayList<>();
        roomList = new ArrayList<>();
        addRoom(new Room());
    }

    public static GameServer getInstance() throws IOException {
        if(server == null){
            server = new GameServer();
        }
        return server;
    }

    private void init() {//todo try catch
        try {
            serverSocket = new ServerSocket(8082);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket clSocket;

        //Создаю сокет!
        while (true) {
            try {
                clSocket = serverSocket.accept();
                System.out.println("connected");//клиент коннектится
                ClientHandler clientHandler = new ClientHandler(clSocket);
                clientHandler.start();
                clientHandlerMap.put(clientHandler.getClientServantId(), clientHandler);
                processNewClientHandler(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //clSocket.close();
        }
    }

    private void processNewClientHandler(ClientHandler clientHandler){//find free room and fill it      if no free room create it
        if(unfilledRoomExist()){
//          System.out.println("unfilledRoomExist true");
            fillRoom(clientHandler, getUnfilledRoom());
        } else {
//          System.out.println("unfilledRoomExist false");
            freeClients.add(clientHandler);//todo upgrade(all rooms filled->> new room)
            addRoom(new Room());//if no empty or unfill rooms create one new
            processNewClientHandler(clientHandler);
            freeClients.remove(clientHandler);
        }
    }

//    private boolean freeClientExist(){
//        if(freeClients.size()>0){
//            return true;
//        }
//        return false;
//    }


    private boolean unfilledRoomExist(){
        for (Room room : roomList) {
            if(!roomIsFilled(room)){return true;}
        }
        return false;
    }

    private Room getUnfilledRoom(){
        for (Room room : roomList) {
            if(!roomIsFilled(room)){return room;}
        }
        return null;
    }

//    private Room findLastRoom(){
//        return roomList.get(roomList.size()-1);
//    }

    private boolean roomIsFilled(Room room){
        System.out.println("room.roomSize"+room.roomSize);
        System.out.println("room.handlersList.size()"+room.handlersList.size());
        if(room.roomSize == room.handlersList.size()){return true;}
        return false;
    }

    private boolean roomNeedOnePlayer(Room room){
        if(room.roomSize == room.handlersList.size()+1){return true;}
        return false;
    }

//    private ClientHandler getLastFreeClient(){
//        return freeClients.get(freeClients.size()-1);
//    }

    private void fillRoom(ClientHandler clientHandler, Room room){
        System.out.println("fillRoom()");
        if(!roomIsFilled(room)) {
            if(roomNeedOnePlayer(room)){
                room.handlersList.add(clientHandler);
                freeClients.remove(clientHandler);
                room.initGame();//when room is ready StartGame
                room.startGame();
            } else {
                room.handlersList.add(clientHandler);
                freeClients.remove(clientHandler);
            }
        }
    }

    private void deleteRoom(){}//todo

    private void addRoom(Room room){
        roomList.add(room);
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