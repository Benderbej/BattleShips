package ru.javabit.gameField;

import java.util.ArrayList;

public class GameField {

    private GameFieldGrid playerFieldGrid;
    private GameFieldGrid enemyFieldGrid;
    private int columnNum;
    private int rowNum;

    public GameField(int rowNum, int columnNum) {
        this.columnNum = columnNum;
        this.rowNum = rowNum;
        playerFieldGrid = new GameFieldGrid(rowNum, columnNum);
        enemyFieldGrid = new GameFieldGrid(rowNum, columnNum);
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