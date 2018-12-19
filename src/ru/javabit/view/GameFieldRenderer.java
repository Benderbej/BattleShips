package ru.javabit.view;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;
import ru.javabit.report.ConsoleDialogue;
import ru.javabit.view.GameFieldRenderable;

public class GameFieldRenderer implements GameFieldRenderable {

    private FieldCell[][] cellsArr;
    private FieldCell[][] enemiesCellsArr;
    private final String FIELDHEADER = "***^^^^^^^^^(-+o^^o+-)^^^^^^^^^***";
    private final String FIELDFOOTER = "***^^^^^^^^^(-+o^^o+-)^^^^^^^^^***";
    private final String PLAYERGRID =  "***=========YOUR SHIPS=========***";
    private final String ENEMYGRID =   "***========ENEMY SHIPS=========***";
    ConsoleDialogue dialogue;

    public GameFieldRenderer (GameField gameField) {
        this.cellsArr = gameField.getPlayerFieldGrid().getCellsArr();
        this.enemiesCellsArr = gameField.getEnemyFieldGrid().getCellsArr();
        dialogue = new ConsoleDialogue();
    }

    public void renderGameField() {
        System.out.flush();
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

    @Override
    public void setGameStatus(String s) {
        dialogue.makeReport(s);
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