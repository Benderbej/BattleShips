package ru.javabit.netgame.client;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;
import ru.javabit.view.GameFieldRenderer;

import java.io.*;
import java.net.Socket;

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
            //dos.writeInt(ClientRequestCode.TAKEFIELDCELL);
            //dos.flush();
            //dos.writeInt(clientHandlerId);
            //dos.flush();


            oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            oos.writeObject(fieldCell);
            oos.flush();
            result = true;
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            try {
                dos.close();
                oos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    boolean giveGameField() {
        System.out.println("giveGameField()");
        DataOutputStream dos = null;
        ObjectInputStream ois = null;
        Socket socket = null;
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            sendRequestCode(dos, ClientRequestCode.GIVEGAMEFIELD);
            //dos.writeInt(ClientRequestCode.GIVEGAMEFIELD);
            //dos.flush();
            //dos.writeInt(clientHandlerId);
            //dos.flush();

            ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            try {
                gameField = (GameField) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (gameField == null){
                System.out.println("gameField = null)");
            } else {
                GameFieldRenderer gameFieldRenderer = new GameFieldRenderer(gameField);
                gameFieldRenderer.renderGameField();
                return true;
            }
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
        System.out.println("giveGameField() end");
        return false;
    }

    boolean giveBattleSide() {
        System.out.println("giveBattleSide()");
        Boolean b = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        Socket socket = null;
        try {


            socket = new Socket(site, Integer.parseInt(port));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            sendRequestCode(dos, ClientRequestCode.GIVEBATTLESIDE);
            //dos.writeInt(ClientRequestCode.GIVEBATTLESIDE);
            //dos.flush();
            //dos.writeInt(clientHandlerId);
            //dos.flush();
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            b = (Boolean) dis.readBoolean();
            if (b == null){
                System.out.println("battleSide = null");
            } else {
                battleSide = b;
                return true;
            }
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            try {
                dos.close();
                dis.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("giveBattleSide() end");
        return false;
    }

    private boolean giveCurrentTurnActorId() {
        System.out.println("giveCurrentTurnActorId()");
        int i = 0;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        Socket socket = null;
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            sendRequestCode(dos, ClientRequestCode.GIVECURRENTACTORID);
            //dos.writeInt(ClientRequestCode.GIVECURRENTACTORID);
            //dos.flush();
            //dos.writeInt(clientHandlerId);
            //dos.flush();

            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            i = (Integer) dis.readInt();
            if (i == 0){
                System.out.println("CurrentTurnActorId = 0");
            } else {
                currentTurnActorId = i;
                return true;
            }
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            try {
                dos.close();
                dis.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("giveCurrentTurnActorId() end");
        return false;
    }

    private boolean giveWinnerId() {
        System.out.println("giveWinnerId()");
        int i = 0;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        Socket socket = null;
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            sendRequestCode(dos, ClientRequestCode.GIVEWINNERID);
            //dos.writeInt(ClientRequestCode.GIVEWINNERID);
            //dos.flush();
            //dos.writeInt(clientHandlerId);
            //dos.flush();


            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            i = (Integer) dis.readInt();
            winnerId = i;
            return true;

        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            try {
                dos.close();
                dis.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("giveWinnerId() end");
        return false;
    }

    /*
    void giveString() {
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
    */

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

    /*
    private Object recieveDataVar(DataInputStream dataInputStream, Class<?> cls, int code) {
        try {
            if (cls == Integer.class) {
                Integer i = (Integer) dataInputStream.readInt();
            } else if (cls == Boolean.class) {
                Boolean i = (Boolean) dataInputStream.readBoolean();
            }
            return i;
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    */

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

//    private void bar() {
//        foo(String.class);
//    }

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

}