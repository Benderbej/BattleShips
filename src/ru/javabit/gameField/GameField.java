package ru.javabit.gameField;

import java.io.Serializable;
import java.util.ArrayList;

public class GameField implements Serializable {
    private int columnNum;
    private int rowNum;
    private Party player;
    private Party enemy;

    public GameField(int rowNum, int columnNum, String playerName, String enemyName) {
        player = new Party(playerName, rowNum, columnNum);
        enemy = new Party(enemyName, rowNum, columnNum);
        this.columnNum = columnNum;
        this.rowNum = rowNum;
    }

    public GameFieldGrid getPlayerFieldGrid() {
        return player.getGrid();
    }

    public GameFieldGrid getEnemyFieldGrid() {
        return enemy.getGrid();
    }

    public int getColumnNum() {
        return columnNum;
    }

    public int getRowNum() {
        return rowNum;
    }
}