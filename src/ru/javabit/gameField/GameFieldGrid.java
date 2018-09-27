package ru.javabit.gameField;

import java.util.ArrayList;

public class GameFieldGrid {

    FieldCellFactory fieldCellFactory;
    public FieldCell[][] cellsArr;
    private GameFieldRulers gameFieldRulers;

    GameFieldGrid (int xsize, int ysize){

        this.gameFieldRulers = new GameFieldRulers(ysize, xsize);
        this.cellsArr = new FieldCell[xsize][ysize];

        fieldCellFactory = new FieldCellFactory();
        for (int y=0; y < ysize+1; y++){
            for(int x=0; x < xsize+1; x++){
                if(y == 0){
                    fieldCellFactory.createMetaFieldCell(x,y,gameFieldRulers.getFieldRowsSkin(y));
                }
                if((x == 0)&&(y != 0)){
                    fieldCellFactory.createMetaFieldCell(x,y,gameFieldRulers.getFieldColsSkin(x));
                }
                if((x != 0)&&(y != 0)) {
                    fieldCellFactory.createGameFieldCell(x, y);
                }
            }
        }
    }
}