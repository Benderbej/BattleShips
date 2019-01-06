package ru.javabit.netgame.server;

import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.GameField;
import ru.javabit.netgame.client.ClientRequestCode;
import ru.javabit.netgame.client.PrimitiveObj;
import ru.javabit.ship.Fleet;
import ru.javabit.ship.FleetDisposer;
import ru.javabit.ship.FleetsDisposal;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class Server {

    private static Server server;
    private ArrayList<Room> roomList;
    private ArrayList<ClientHandler> freeClients;
    ServerSocket serverSocket;
    HashMap<Integer, ClientHandler> clientHandlerMap;

    public static void main(String[] args) throws IOException {
        server = Server.getInstance();
        server.init();
        server.run();
    }

    private void init() {
        try {
            serverSocket = new ServerSocket(8082);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run() {

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();

                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
                int code = dataInputStream.readInt();
                System.out.println("code="+code);

                processRequest(code, socket);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }





        /*
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("connected");//клиент коннектится
                //DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                //int code = dataInputStream.readInt();
                //System.out.println("code"+code);
                processRequest(0, socket);




                ClientHandler clientHandler = new ClientHandler();

                clientHandlerMap.put(clientHandler.getClientServantId(), clientHandler);
                //processNewClientHandler(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //clSocket.close();
        }
        */

    }


    private void meet(Socket socket) {

        try {
            System.out.println("Accepted from: " + socket.getInetAddress());
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            try {
                String a = (String) ois.readObject();
                System.out.println("a.i=" + a);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }


//            ClientHandler clientHandler = new ClientHandler();
//            GameField gameField = new GameField(11, 11,"computer 1", "computer 2");
//            Fleet fleet1 = new Fleet();
//            Fleet fleet2 = new Fleet();
//            FleetsDisposal fleetsDisposal = new FleetsDisposal(gameField, fleet1, fleet2);
//            try {
//                fleetsDisposal.disposeAutoAuto();
//            } catch (BattleShipsException e) {
//                e.printStackTrace();
//            }
//            objectOutputStream.writeObject(gameField);


    }

    private void giveGameField(Socket socket){
        System.out.println("giveGameField()");
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            int clientHandlerId = dis.readInt();
            System.out.println("clientHandlerId" + clientHandlerId);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            oos.writeObject("StRRing!");
            oos.flush();
            oos.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void giveString(Socket socket) {
        System.out.println("giveString()");
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            int clientHandlerId = dis.readInt();
            System.out.println("clientHandlerId" + clientHandlerId);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            oos.writeObject("StRRing!");
            oos.flush();
            oos.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void processRequest(int code, Socket socket) {
        switch (code){
            case ClientRequestCode.MEET :
                System.out.println("client need id");
                meet(socket);
                break;
            case ClientRequestCode.GIVESTRING :
                System.out.println("client need some string");
                giveString(socket);
                //takeId();
                break;
            case ClientRequestCode.TAKETURN :
                System.out.println("client take GameFieldCell");
                break;
            case ClientRequestCode.GIVEGAMEFIELD :
                System.out.println("clientNeed GameField");
                giveGameField(socket);
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