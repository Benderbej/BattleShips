package ru.javabit.view;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;
import ru.javabit.view.GameFieldRenderable;

public class GameFieldRenderer implements GameFieldRenderable {

    //private ArrayList<FieldCell> cellsList;
    private FieldCell[][] cellsArr;
    private FieldCell[][] enemiesCellsArr;
    private final String FIELDHEADER = "***^^^^^^^^^(-+o^^o+-)^^^^^^^^^***";
    private final String FIELDFOOTER = "***^^^^^^^^^(-+o^^o+-)^^^^^^^^^***";
    private final String PLAYERGRID =  "***=========YOUR SHIPS=========***";
    private final String ENEMYGRID =   "***========ENEMY SHIPS=========***";

    public GameFieldRenderer (GameField gameField){
        this.cellsArr = gameField.getPlayerFieldGrid().getCellsArr();
        this.enemiesCellsArr = gameField.getEnemyFieldGrid().getCellsArr();
    }

    public void renderGameField(){
        renderHeader();
        renderLine(PLAYERGRID);
        for (FieldCell[] arr : cellsArr) {
            String line = "";
            for(FieldCell cell : arr){
                line += "["+cell.getSkin()+"]";
            }
            renderLine(line);
        }
        renderLine(ENEMYGRID);
        for (FieldCell[] arr : enemiesCellsArr) {
            String line = "";
            for(FieldCell cell : arr){
                line += "["+cell.getSkin()+"]";
            }
            renderLine(line);
        }
        renderFooter();
    }

    private void renderLine(String s){
        System.out.println(s);
    }

    private void renderHeader(){
        System.out.println(FIELDHEADER);
    }

    private void renderFooter(){
        System.out.println(FIELDFOOTER);
    }
}