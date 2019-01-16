package ru.javabit.netgame.server;

import ru.javabit.Game;
import ru.javabit.GameMath;
import ru.javabit.VictoryTrigger;
import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.turn.TurnMaster;

import java.util.ArrayList;
import java.util.LinkedList;

public class Room {

    private MultiplayerGame multiplayerGame;
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
        ArrayList<Integer> clientIds = new ArrayList<>();
        clientIds.add(handlersList.get(0).getClientServantId());
        clientIds.add(handlersList.get(1).getClientServantId());
        multiplayerGame = new MultiplayerGame(getClientsIdsList());
        try {
            multiplayerGame.initGame();
        } catch (BattleShipsException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Integer> getClientsIdsList() {
        ArrayList<Integer> clientIds = new ArrayList<>();
        clientIds.add(handlersList.get(0).getClientServantId());
        clientIds.add(handlersList.get(1).getClientServantId());
        return clientIds;
    }

    public void startGame() {
        try {
            multiplayerGame.startGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MultiplayerGame getMultiplayerGame() {
        return multiplayerGame;
    }
}
