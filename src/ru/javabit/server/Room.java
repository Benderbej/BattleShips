package ru.javabit.server;

/*
do room logic - unify 2 clients in one room
 */

import ru.javabit.Game;
import ru.javabit.exceptions.BattleShipsException;

public class Room {

    void init(){
        Game multiplayerGame = new MultiplayerGame();
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

}
