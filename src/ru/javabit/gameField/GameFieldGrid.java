package ru.javabit.gameField;

import java.util.ArrayList;

public class GameFieldGrid {

    FieldCellFactory fieldCellFactory;

    private FieldCell[][] cellsArr;
    private GameFieldRulers gameFieldRulers;

    GameFieldGrid (int xsize, int ysize){

        gameFieldRulers = new GameFieldRulers(ysize, xsize);
        cellsArr = new FieldCell[xsize][ysize];

        fieldCellFactory = new FieldCellFactory();
        for (int y=0; y < ysize; y++){
            for(int x=0; x < xsize; x++){
                if(y == 0){
                    cellsArr[x][y] = fieldCellFactory.createMetaFieldCell(x,y,gameFieldRulers.getFieldRowsSkin(x));
                }
                if((x == 0)&&(y != 0)){
                    cellsArr[x][y] = fieldCellFactory.createMetaFieldCell(x,y,gameFieldRulers.getFieldColsSkin(y));
                }
                if((x != 0)&&(y != 0)) {
                    cellsArr[x][y] = fieldCellFactory.createGameFieldCell(x, y);
                }
            }
        }
    }
    public FieldCell[][] getCellsArr() {
        return cellsArr;
    }
}