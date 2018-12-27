package ru.javabit.server;

/*
do room logic - unify 2 clients in one room
 */

import ru.javabit.Game;
import ru.javabit.GameMath;
import ru.javabit.exceptions.BattleShipsException;

public class Room {

    Game multiplayerGame;

    private static int roomId;

    Room(){
        roomId = GameMath.getRandomInt(1, Integer.MAX_VALUE);;
    }

    void init(){
        multiplayerGame = new MultiplayerGame();
        try {
            multiplayerGame.initGame();
            try {
                multiplayerGame.startGame();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (BattleShipsException e) {
            e.printStackTrace();
        }
    }

    public static int getRoomNum() {
        return roomId;
    }

}
