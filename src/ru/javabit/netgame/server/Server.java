package ru.javabit.netgame.server;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;
import ru.javabit.netgame.client.ClientRequestCode;
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
        try {
            int clientHandlerId = dataInputStream.readInt();
            GameField gameField= null;
            if(gameInRoomInited(roomList.get(0))) {
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                gameField = getGameByHandlerId().getGameField();
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
    }

    private void giveBattleSide(Socket socket, DataInputStream dataInputStream) {
        try {
            int clientHandlerId = dataInputStream.readInt();
            Boolean battleSide= null;
            if(gameInRoomInited(roomList.get(0))) {
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                battleSide = getGameByHandlerId().getBattleSide(clientHandlerId);
                dos.writeBoolean(battleSide);
                dos.flush();
                dos.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void giveCurrentActorId(Socket socket, DataInputStream dataInputStream) {
        try {
            int clientHandlerId = dataInputStream.readInt();
            int currentActorId = 0;
            if(gameInRoomInited(roomList.get(0))) {
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                if(getGameByHandlerId().getTurnMaster() != null) {
                    currentActorId = getGameByHandlerId().getTurnMaster().getCurrentTurnClientHandlerId();
                    if (currentActorId == 0) {
                        dos.writeInt(0);
                        dos.flush();
                        dos.close();
                    }
                }
                dos.writeInt(currentActorId);
                dos.flush();
                dos.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void giveWinnerId(Socket socket, DataInputStream dataInputStream) {
        try {
            int clientHandlerId = dataInputStream.readInt();
            int winnerId = 0;
            if(gameInRoomInited(roomList.get(0))) {
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

                if(getGameByHandlerId().getTurnMaster() != null) {

                    winnerId = getGameByHandlerId().getTurnMaster().getWinnerId();
                }
                    System.out.println("winnerId=" + winnerId);
                dos.writeInt(winnerId);
                dos.flush();
                dos.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            int clientHandlerId = dis.readInt();
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            oos.flush();
            oos.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private FieldCell takeFieldCell(Socket socket, DataInputStream dataInputStream) {
        FieldCell fc = null;
        try {
            int clientHandlerId = dataInputStream.readInt();
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));//!!!! создаю еще один инпут стрим чтобы прочесть объект
            try {
                fc = (FieldCell) ois.readObject();
                //System.out.println("FieldCell is taken x="+fc.getFieldCellCoordinate().getX()+" y="+fc.getFieldCellCoordinate().getY());
                getGameByHandlerId().getTurnMaster().setRemotePlayerTurn(clientHandlerId, fc);//на серваке в обработке takeFieldCell
                //remotePlayersTurns.put(clientHandlerId, choosenCell);//на серваке в обработке takeFieldCell
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ois.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fc;
    }

    private void processRequest(int code, Socket socket, DataInputStream dataInputStream) {
        switch (code) {
            case ClientRequestCode.MEET :
                System.out.println("client need id to set");
                meet(socket);
                break;
            case ClientRequestCode.GIVESTRING :
                System.out.println("client need some string");
                giveString(socket);
                //takeId();
                break;
            case ClientRequestCode.TAKEFIELDCELL :
                System.out.println("client take GameFieldCell");
                takeFieldCell(socket, dataInputStream);
                break;
            case ClientRequestCode.GIVEGAMEFIELD :
                System.out.println("clientNeed GameField");
                giveGameField(socket, dataInputStream);//TODO gameField
                break;
            case ClientRequestCode.GIVEBATTLESIDE :
                System.out.println("clientNeed Activeness");
                giveBattleSide(socket, dataInputStream);
                break;
            case ClientRequestCode.GIVECURRENTACTORID :
                System.out.println("clientNeed CurrentTurnPlayerId");
                giveCurrentActorId(socket, dataInputStream);
                break;
            case ClientRequestCode.GIVEWINNERID :
                System.out.println("clientNeed winnerId");
                giveWinnerId(socket, dataInputStream);
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
            room.startGame();
        }
    }
}