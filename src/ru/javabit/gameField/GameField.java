package ru.javabit.gameField;

import java.util.ArrayList;

public class GameField {

    private static GameField gameField;
    private GameFieldGrid gameFieldGrid;

    //private int columnNum = 11;
    //private int rowNum = 11;

    private GameField(){}

    private GameField(int rowNum, int columnNum){
        this.gameFieldGrid = new GameFieldGrid(rowNum, columnNum);
    }

    public GameFieldGrid getGameFieldGrid() {
        return gameFieldGrid;
    }

    public static GameField getInstance(int rowNum, int columnNum){
        if(gameField == null){
            gameField = new GameField(rowNum, columnNum);
        }
        return gameField;
    }

}