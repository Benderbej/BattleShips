package ru.javabit.server;

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

public class GameServer {

    private static GameServer server;
    ServerSocket serverSocket;
    Socket socket;
    InputStream inputStream;
    OutputStream outputStream;








    GameField gameField;
    FleetsDisposal fleetsDisposal;
    GameFieldRenderable gameFieldRenderer;
    Fleet fleet1;
    Fleet fleet2;
    TurnMaster turnMaster;
    VictoryTrigger victoryTrigger;




    public static void main(String[] args) {
        GameServer server = GameServer.getInstance();
        server.init();
        server.start();
    }

    private GameServer(){ }

    public static GameServer getInstance(){
        if(server == null){
            server = new GameServer();
        }
        return server;
    }

    private void init(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8082);
            socket = serverSocket.accept();
            System.out.println("client connected...");
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            // Сама информация
            String html = "hhh";

            // Правила хорошего тона, деловой переписки
            String header = "HTTP/1.1 200 OK\n" +
                    "Content-Language: ru\n" +
                    "Content-Type: text/html; charset=utf-8\n" +
                    "Content-Length: " + html.length() + "\n" +
                    "Connection: close\n\n";

            String result = header + html;
            outputStream.write(result.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void start(){

    }




    private void privateWaitingForClients(){

    }













    public void initGame() throws BattleShipsException {
        gameField = new GameField(11, 11,"computer 1", "computer 2");
        fleet1 = new Fleet();
        fleet2 = new Fleet();
        fleetsDisposal = new FleetsDisposal(gameField, fleet1, fleet2);
        fleetsDisposal.disposeAutoAuto();
        ////gameFieldRenderer = new GameFieldSwingRenderer(gameField);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        ////gameFieldRenderer.renderGameField();
    }

    public void startGame() throws InterruptedException {//todo init gameprocess thread
        victoryTrigger = new VictoryTrigger(fleet1, fleet2);
        turnMaster = TurnMaster.getInstance();
        //turnMaster.initComputerVsComputer(gameField, "computer 1", "computer 2");
        ////turnMaster.setGameFieldRenderer(gameFieldRenderer);
        turnMaster.initHumanVsComputer(gameField, "human", "computer");
        turnMaster.setVictoryTrigger(victoryTrigger);
        new Thread(turnMaster).start();
    }

    public Fleet getFleet1() {
        return fleet1;
    }

    public Fleet getFleet2() {
        return fleet2;
    }













}