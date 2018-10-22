package ru.javabit.gameField;

import java.util.ArrayList;

public class GameField {

    private GameFieldGrid playerFieldGrid;
    private GameFieldGrid enemyFieldGrid;
    private int columnNum = 11;
    private int rowNum = 11;

    public GameField(int rowNum, int columnNum){
        this.playerFieldGrid = new GameFieldGrid(rowNum, columnNum);
        this.enemyFieldGrid = new GameFieldGrid(rowNum, columnNum);
    }

    public GameFieldGrid getPlayerFieldGrid() {
        return playerFieldGrid;
    }

    public GameFieldGrid getEnemyFieldGrid() {
        return enemyFieldGrid;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public int getRowNum() {
        return rowNum;
    }
}