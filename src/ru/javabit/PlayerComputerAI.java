package ru.javabit;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameFieldCell;

import java.util.ArrayList;

public class PlayerComputerAI {

    private ArrayList<GameFieldCell> enemyFieldCellsList;
    public FieldCell[][] fieldCells;//player's gamefield

    PlayerComputerAI(FieldCell[][] fieldCells) {
        this.fieldCells = fieldCells;
        fillFieldCellsList();
    }

    private void fillFieldCellsList() {
        enemyFieldCellsList = new ArrayList<GameFieldCell>();
        for (FieldCell[] arr : fieldCells) {
            for(FieldCell cell : arr){
                if(cell instanceof GameFieldCell){
                    enemyFieldCellsList.add((GameFieldCell) cell);
                    //System.out.println("X=" + cell.getFieldCellCoordinate().getX() + " Y=" + cell.getFieldCellCoordinate().getY());
                }

            }
        }
        //System.out.println(enemyFieldCellsList.size());
    }

    private GameFieldCell chooseCellToAttack() {
        return null;
    }

    private void moveFromFieldCellsList(int x, int y){

        //enemyFieldCellsList.get()
    }
}
