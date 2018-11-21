package ru.javabit.gameField;

import java.util.ArrayList;

public class GameField {

    private GameFieldGrid playerFieldGrid;
    private GameFieldGrid enemyFieldGrid;
    private String playerName;
    private String enemyName;
    private int columnNum;
    private int rowNum;

    public GameField(int rowNum, int columnNum, String playerName, String enemyName) {
        this.columnNum = columnNum;
        this.rowNum = rowNum;
        playerFieldGrid = new GameFieldGrid(rowNum, columnNum);
        enemyFieldGrid = new GameFieldGrid(rowNum, columnNum);
        this.playerName = playerName;
        this.enemyName = enemyName;
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