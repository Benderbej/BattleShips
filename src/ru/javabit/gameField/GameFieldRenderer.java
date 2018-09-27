package ru.javabit.gameField;

import java.util.ArrayList;

public class GameFieldRenderer implements GameFieldRenderable {

    //private ArrayList<FieldCell> cellsList;
    private FieldCell[][] cellsArr;
    private final String FIELDHEADER = "***=========(-8=8-)=========***";
    private final String FIELDFOOTER = "***========^^^^^^^^^^=======***";

    public void renderGameField(){
        renderHeader();
        for (FieldCell[] arr : cellsArr) {
            String line = "";
            for(FieldCell cell : arr){
                line += cell.getSkin();
                renderLine(line);
            }
        }
        renderFooter();
    }

    private void renderLine(String line){
        System.out.print("       ");
        System.out.println(line);
    }

    private void renderHeader(){
        System.out.println(FIELDHEADER);
    }

    private void renderFooter(){
        System.out.println(FIELDFOOTER);
    }

}
