package ru.javabit.view;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;
import ru.javabit.view.GameFieldRenderable;

public class GameFieldRenderer implements GameFieldRenderable {

    //private ArrayList<FieldCell> cellsList;
    private FieldCell[][] cellsArr;
    private final String FIELDHEADER = "***^^^^^^^^^(-+o^^o+-)^^^^^^^^^***";
    private final String FIELDFOOTER = "***=========^^^^^^^^^^=========***";

    public GameFieldRenderer (GameField gameField){
        this.cellsArr = gameField.getGameFieldGrid().getCellsArr();
    }

    public void renderGameField(){
        renderHeader();
        for (FieldCell[] arr : cellsArr) {
            String line = "";
            for(FieldCell cell : arr){
                line += "["+cell.getSkin()+"]";
            }
            renderLine(line);
        }
        renderFooter();
    }

    private void renderLine(String line){
        //System.out.print("       ");
        System.out.println(line);
    }

    private void renderHeader(){
        System.out.println(FIELDHEADER);
    }

    private void renderFooter(){
        System.out.println(FIELDFOOTER);
    }

}
