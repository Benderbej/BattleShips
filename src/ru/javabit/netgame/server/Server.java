package ru.javabit.netgame.server;

import ru.javabit.Game;
import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;
import ru.javabit.netgame.client.ClientRequestCode;
import ru.javabit.netgame.client.PrimitiveObj;
import ru.javabit.ship.Fleet;
import ru.javabit.ship.FleetDisposer;
import ru.javabit.ship.FleetsDisposal;
import ru.javabit.turn.MultiplayerTurnMaster;
import ru.javabit.view.GameFieldRenderer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
                int code = dataInputStream.readInt();
                System.out.println("code="+code);
                processRequest(code, socket, dataInputStream);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void meet(Socket socket) {
        ClientHandler clientHandler = new ClientHandler();
        clientHandlerMap.put(clientHandler.getClientServantId(), clientHandler);
        processNewClientHandler(socket, clientHandler);

        checkStartGame();
    }

    private void processNewClientHandler(Socket socket, ClientHandler handler) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeInt(handler.getClientServantId());
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void giveGameField(Socket socket, DataInputStream dataInputStream) {
        System.out.println("giveGameField()");
        try {
            int clientHandlerId = dataInputStream.readInt();
            System.out.println("clientHandlerId=" + clientHandlerId);


            GameField gameField= null;
            if(gameInRoomInited(roomList.get(0))) {

                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                gameField = getGameByHandlerId().getGameField();
                if(gameField == null){
                    System.out.println("gameField IS NULL!");
                }
                if(getGameByHandlerId() == null){
                    System.out.println("game IS NULL!");
                }


//            GameField gameField = new GameField(11, 11,"computer 1", "computer 2");
//            Fleet fleet1 = new Fleet();
//            Fleet fleet2 = new Fleet();
//            FleetsDisposal fleetsDisposal = new FleetsDisposal(gameField, fleet1, fleet2);
//            try {
//                fleetsDisposal.disposeAutoAuto();
//            } catch (BattleShipsException e) {
//                e.printStackTrace();
//            }
                GameFieldRenderer gameFieldRenderer = new GameFieldRenderer(gameField);
                gameFieldRenderer.renderGameField();

                oos.writeObject(gameField);
                oos.flush();
                oos.close();
            } else {
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                oos.writeObject(null);
                oos.flush();
                oos.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("giveGameField() end");
    }

    private void giveActiveness(Socket socket, DataInputStream dataInputStream) {
        System.out.println("giveActiveness()");
        try {
            int clientHandlerId = dataInputStream.readInt();
            System.out.println("clientHandlerId=" + clientHandlerId);

            Boolean activeness= null;
            if(gameInRoomInited(roomList.get(0))) {

                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                activeness = getGameByHandlerId().getPlayerActiveness(clientHandlerId);
                if(activeness == null){
                    System.out.println("activeness IS NULL!");
                }
                dos.writeBoolean(activeness);
                dos.flush();
                dos.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("giveActiveness() end");
    }

    private void giveCurrentActorId(Socket socket, DataInputStream dataInputStream) {
        System.out.println("giveCurrentActorId()");
        try {
            int clientHandlerId = dataInputStream.readInt();
            System.out.println("clientHandlerId=" + clientHandlerId);
            int currentActorId = 0;
            if(gameInRoomInited(roomList.get(0))) {
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                currentActorId = getGameByHandlerId().getTurnMaster().getCurrentTurnClientHandlerId();
                if(currentActorId == 0){
                    dos.writeInt(0);
                    dos.flush();
                    dos.close();
                    System.out.println("currentActorId IS NULL!");
                }
                dos.writeInt(currentActorId);
                dos.flush();
                dos.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("giveCurrentActorId() end");
    }

    private MultiplayerGame getGameByHandlerId() {//TODO jut one game now
        return roomList.get(0).getMultiplayerGame();
    }

    private boolean gameInRoomInited(Room room){
        if(room.getMultiplayerGame() == null){
            return false;
        }
        return true;
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

    private FieldCell takeFieldCell(Socket socket) {
        System.out.println("takeFieldCell()");
        FieldCell fc = null;
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            int clientHandlerId = dis.readInt();
            System.out.println("clientHandlerId" + clientHandlerId);
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            try {
                fc = (FieldCell) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ois.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //remotePlayersTurns
        //roomList.get(0).getMultiplayerGame().getTurnMaster();//TODO choose room
        //roomList.get(0).getMultiplayerGame().


        return fc;
    }

    private void processRequest(int code, Socket socket, DataInputStream dataInputStream) {
        switch (code) {
            case ClientRequestCode.MEET :
                System.out.println("client need id");
                meet(socket);
                break;
            case ClientRequestCode.GIVESTRING :
                System.out.println("client need some string");
                giveString(socket);
                //takeId();
                break;
            case ClientRequestCode.TAKEFIELDCELL :
                System.out.println("client take GameFieldCell");
                takeFieldCell(socket);
                break;
            case ClientRequestCode.GIVEGAMEFIELD :
                System.out.println("clientNeed GameField");
                giveGameField(socket, dataInputStream);//TODO gameField
                break;
            case ClientRequestCode.GIVEACTIVENESS :
                System.out.println("clientNeed Activeness");
                giveActiveness(socket, dataInputStream);
                break;
            case ClientRequestCode.GIVECURRENTACTORID :
                System.out.println("clientNeed Activeness");
                giveCurrentActorId(socket, dataInputStream);
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
        roomList.add(room);
    }

    void checkStartGame(){//TODO just imitate room managing - two clients automatically start game
        if(clientHandlerMap.size()==2){
            System.out.println("roomsize = 2, starting game");


            System.out.println(roomList.size());
            Room room = roomList.get(0);
            long i = 0;
            for (Map.Entry<Integer, ClientHandler> pair : clientHandlerMap.entrySet()) {
                room.handlersList.add(pair.getValue());
            }
            for (ClientHandler h: room.handlersList) {
                System.out.println("h.getClientServantId()="+h.getClientServantId());
            }
            room.initGame();
        }
    }

}