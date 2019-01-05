package ru.javabit.netgame.server;

import ru.javabit.netgame.client.ClientRequestCode;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static ru.javabit.netgame.client.ClientRequestCode.Meet;


public class Server {

    private static Server server;
    private ArrayList<Room> roomList;
    private ArrayList<ClientHandler> freeClients;
    ServerSocket serverSocket;
    HashMap<Integer, ClientHandler> clientHandlerMap;

    public static void main(String[] args) throws IOException {
        server = Server.getInstance();
        server.init();
    }

    private void init() {
        try {
            serverSocket = new ServerSocket(8082);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run(){
        while (true) {
            try {
                Socket Socket = serverSocket.accept();


                System.out.println("connected");//клиент коннектится
                ClientHandler clientHandler = new ClientHandler();



                clientHandlerMap.put(clientHandler.getClientServantId(), clientHandler);
                //processNewClientHandler(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //clSocket.close();
        }
    }

    private void processRequest(int code){
        switch (code){
            case ClientRequestCode.MEET :
                System.out.println("client need id");
                break;
            case ClientRequestCode.TAKEID :
                System.out.println("client need identification by id");
                break;
            case ClientRequestCode.TAKETURN :
                System.out.println("client take GameFieldCell");
                break;
            case ClientRequestCode.GIVEGAMEFIELD :
                System.out.println("clientNeed GameField");
                break;
        }
    }

    private Server() throws IOException {
        clientHandlerMap = new HashMap<>();
        freeClients = new ArrayList<>();
        roomList = new ArrayList<>();
        addRoom(new Room());
    }

    public static Server getInstance() throws IOException {
        if(server == null){
            server = new Server();
        }
        return server;
    }

    private void addRoom(Room room) {
    }

}