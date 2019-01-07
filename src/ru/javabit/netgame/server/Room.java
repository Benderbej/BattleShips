package ru.javabit.netgame.server;

import ru.javabit.Game;
import ru.javabit.GameMath;
import ru.javabit.exceptions.BattleShipsException;

import java.util.LinkedList;

public class Room {

    Game multiplayerGame;
    LinkedList<ClientHandler> handlersList;
    public final int roomSize;
    public static final int roomDefaultSize = 2;
    private int roomId;

    Room() {
        roomId = GameMath.getRandomInt(1, Integer.MAX_VALUE);
        handlersList = new LinkedList<>();
        roomSize = 2;
    }

    public int getRoomNum() {
        return roomId;
    }

    public void initGame() {
        System.out.println("room"+roomId + " initGame() "+ handlersList.get(0).getClientServantId() + " vs "+ handlersList.get(1).getClientServantId());
        multiplayerGame = new MultiplayerGame();
        try {
            multiplayerGame.initGame();
        } catch (BattleShipsException e) {
            e.printStackTrace();
        }
//        for (ClientHandler clientHandler : handlersList) {
//            clientHandler.sendGameFieldToClient(multiplayerGame.getGameField());
//        }
        //multiplayerGame.getGameField();
    }

    public void startGame() {
        System.out.println("room"+roomId + " startGame() "+ handlersList.get(0).getClientServantId() + " vs "+ handlersList.get(1).getClientServantId());
//        try {
//            multiplayerGame.startGame();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
