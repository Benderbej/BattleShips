package ru.javabit.netgame.client;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;
import ru.javabit.view.GameFieldRenderer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

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
    private GameField gameField;
    Boolean battleSide;//who attacker true is attacker
    int currentTurnActorId;
    int winnerId;
    Client(){
        site = "localhost";
        port = "8082";
    }

    void meet() {
        Socket socket = null;
        DataOutputStream dataOutputStream = null;
        String site = "localhost";
        String port = "8082";
            try {
                socket = new Socket(site, Integer.parseInt(port));
                dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                dataOutputStream.writeInt(ClientRequestCode.MEET);
                dataOutputStream.flush();
                DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                clientHandlerId = dis.readInt();
                System.out.println("clientHandlerId"+clientHandlerId);
                dataOutputStream.close();
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    dataOutputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }


    private void takeTurn() {}

    boolean takeFieldCell(FieldCell fieldCell) {
        boolean result= false;
        System.out.println("takeFieldCell()");
        DataOutputStream dos = null;
        ObjectOutputStream oos = null;
        Socket socket = null;
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            sendRequestCode(dos, ClientRequestCode.TAKEFIELDCELL);
            oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            oos.writeObject(fieldCell);
            oos.flush();
            result = true;
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            closeStreamsAndSockets(new ArrayList<OutputStream>(Arrays.asList(dos, oos)), null, socket);
        }
        return result;
    }

    boolean giveGameField() {
        DataOutputStream dos = null;
        ObjectInputStream ois = null;
        Socket socket = null;
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            sendRequestCode(dos, ClientRequestCode.GIVEGAMEFIELD);
            ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            try {
                gameField = (GameField) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (gameField != null){
                GameFieldRenderer gameFieldRenderer = new GameFieldRenderer(gameField);
                gameFieldRenderer.renderGameField();
                return true;
            }
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            closeStreamsAndSockets(new ArrayList<OutputStream>(Arrays.asList(dos)), new ArrayList<InputStream>(Arrays.asList(ois)), socket);
        }
        return false;
    }

    boolean giveBattleSide() {
        Boolean b = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        Socket socket = null;
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            sendRequestCode(dos, ClientRequestCode.GIVEBATTLESIDE);
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            b = (Boolean) recieveDataVar(dis, Boolean.class);
            if (b == null){
                System.out.println("battleSide = null");
            } else {
                battleSide = b;
                return true;
            }
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            closeStreamsAndSockets(new ArrayList<OutputStream>(Arrays.asList(dos)), new ArrayList<InputStream>(Arrays.asList(dis)), socket);
        }
        return false;
    }

    private boolean giveCurrentTurnActorId() {
        int i = 0;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        Socket socket = null;
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            sendRequestCode(dos, ClientRequestCode.GIVECURRENTACTORID);
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            i = (Integer) dis.readInt();
            if (i != 0){
                currentTurnActorId = i;
                return true;
            }
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            closeStreamsAndSockets(new ArrayList<OutputStream>(Arrays.asList(dos)), new ArrayList<InputStream>(Arrays.asList(dis)), socket);
        }
        return false;
    }

    private boolean giveWinnerId() {
        int i = 0;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        Socket socket = null;
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            sendRequestCode(dos, ClientRequestCode.GIVEWINNERID);
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            i = (Integer) dis.readInt();
            winnerId = i;
            return true;
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            closeStreamsAndSockets(new ArrayList<OutputStream>(Arrays.asList(dos)), new ArrayList<InputStream>(Arrays.asList(dis)), socket);
        }
        return false;
    }

    void giveString() {
        DataOutputStream dos = null;
        ObjectInputStream ois = null;
        Socket socket = null;
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            sendRequestCode(dos, ClientRequestCode.GIVESTRING);
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
            closeStreamsAndSockets(new ArrayList<OutputStream>(Arrays.asList(dos)), new ArrayList<InputStream>(Arrays.asList(ois)), socket);
        }
    }

    private void sendRequestCode(DataOutputStream dataOutputStream, int code) {
        try {
            dataOutputStream.writeInt(code);
            dataOutputStream.flush();
            dataOutputStream.writeInt(clientHandlerId);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Object recieveDataVar(DataInputStream dataInputStream, Class<?> cls) {
        Object o=null;
        try {
            if (cls == Integer.class) {
                o = (Integer) dataInputStream.readInt();
            } else if (cls == Boolean.class) {
                o = (Boolean) dataInputStream.readBoolean();
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return o;
    }


    private Object recieveObjectVar(ObjectInputStream objectInputStream, Class<?> cls) {
        Object o = null;
        try {
            if (cls == GameField.class) {
                o = objectInputStream.readObject();
            } else if (cls == String.class) {
                o = objectInputStream.readBoolean();
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    private void sendFirstRequest(int code) {

    }

    private void sendRequest(int code) {

    }

    void recieveGameField() {
        GameField gameField = null;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("try to getGameField...");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(giveGameField()){return;}
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void recieveBattleSide() {
        Boolean battleSide = null;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("try to getBattleSide...");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(giveBattleSide()){return;}
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void getCurrentTurnActorId() {//CurrentTurnActorId is clienthandlerid of client who is able to make turn
        int currentTurnActorId = 0;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("try to getCurrentTurnActorId...");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(giveCurrentTurnActorId()){return;}
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void getWinnerId() {//CurrentTurnActorId is clienthandlerid of client who is able to make turn
        int winnerId = 0;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("try to getCurrentTurnActorId...");
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(giveWinnerId()){return;}
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public GameField getGameField() {
        return gameField;
    }

    FieldCell[][] getAllyCellsArr(){
        if(battleSide){return getGameField().getPlayerFieldGrid().getCellsArr();}else{return getGameField().getEnemyFieldGrid().getCellsArr();}
    }

    FieldCell[][] getEnemyCellsArr(){
        if(battleSide){return getGameField().getEnemyFieldGrid().getCellsArr();}else{return getGameField().getPlayerFieldGrid().getCellsArr();}
    }

    private void closeStreamsAndSockets(ArrayList<OutputStream> outputStreamsList, ArrayList<InputStream> inputStreamsList, Socket socket){
        try {
            if(outputStreamsList != null) {
                for (OutputStream stream : outputStreamsList) {
                    stream.close();
                }
            }
            if(inputStreamsList != null) {
                for (InputStream stream : inputStreamsList) {
                    stream.close();
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}