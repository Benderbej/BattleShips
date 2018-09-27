package ru.javabit.gameField;

import java.util.ArrayList;

public class GameField {

    private GameField gameField;
    private GameFieldGrid gameFieldGrid;

    private int columnNum = 10;
    private int rowNum = 10;

    private GameField(){}

    private GameField(int rowNum, int columnNum){
        this.gameFieldGrid = new GameFieldGrid(rowNum, columnNum);
    }

    public GameFieldGrid getGameFieldGrid() {
        return gameFieldGrid;
    }

    public GameField getInstance(int rowNum, int columnNum){
        if(gameField == null){
            gameField = new GameField(rowNum, columnNum);
        }
        return gameField;
    }

}