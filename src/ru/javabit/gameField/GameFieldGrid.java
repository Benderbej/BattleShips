package ru.javabit.gameField;

import java.util.ArrayList;

public class GameFieldGrid {

    FieldCellFactory fieldCellFactory;

    private FieldCell[][] cellsArr;
    private GameFieldRulers gameFieldRulers;

    GameFieldGrid (int xsize, int ysize){

        this.gameFieldRulers = new GameFieldRulers(ysize, xsize);
        this.cellsArr = new FieldCell[xsize][ysize];

        fieldCellFactory = new FieldCellFactory();
        //for (int y=0; y < ysize + 1; y++){
        //    for(int x=0; x < xsize + 1; x++){
        for (int y=0; y < ysize; y++){
            for(int x=0; x < xsize; x++){
                if(y == 0){
                    cellsArr[x][y] = fieldCellFactory.createMetaFieldCell(x,y,gameFieldRulers.getFieldRowsSkin(x));
                    //System.out.println("skin="+cellsArr[x][y].getSkin());
                }
                if((x == 0)&&(y != 0)){
                    cellsArr[x][y] = fieldCellFactory.createMetaFieldCell(x,y,gameFieldRulers.getFieldColsSkin(y));
                    //System.out.println("skin="+cellsArr[x][y].getSkin());
                }
                if((x != 0)&&(y != 0)) {
                    FieldCell fieldCell = fieldCellFactory.createGameFieldCell(x, y);
                    if (fieldCell == null){
                        System.out.println("!!! fieldCell = null");
                    }
                    cellsArr[x][y] = fieldCellFactory.createGameFieldCell(x, y);
                }
            }
        }
    }

    public FieldCell[][] getCellsArr() {
        return cellsArr;
    }
}