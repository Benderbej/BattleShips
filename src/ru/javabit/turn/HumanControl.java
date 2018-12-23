package ru.javabit.turn;

import ru.javabit.GameMath;
import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.FieldCellCoordinate;
import ru.javabit.gameField.GameFieldCell;
import ru.javabit.view.CellState;

import java.util.ArrayList;

public class HumanControl {
    private ArrayList<GameFieldCell> enemyFieldCellsList;
    private FieldCell[][] fieldCells;//enemy's gamefield

    HumanControl(FieldCell[][] fieldCells) {
        this.fieldCells = fieldCells;
        fillFieldCellsList();
    }

    private void fillFieldCellsList() {
        enemyFieldCellsList = new ArrayList<GameFieldCell>();
        for (FieldCell[] arr : fieldCells) {
            for(FieldCell cell : arr){
                if(cell instanceof GameFieldCell){
                    enemyFieldCellsList.add((GameFieldCell) cell);
                }

            }
        }
        System.out.println(enemyFieldCellsList.size());
    }

    public boolean attack() {
        boolean success = false;
        if(attackCell(chooseCellToAttack())){success = true;}
        return success;
    }

    private FieldCellCoordinate chooseCellToAttack() {
        GameFieldCell gameFieldCell = enemyFieldCellsList.get(GameMath.getRandomInt(enemyFieldCellsList.size()));
        FieldCellCoordinate fieldCellCoordinate = gameFieldCell.getFieldCellCoordinate();
        return fieldCellCoordinate;
    }

    private boolean attackCell(FieldCellCoordinate coordinate) {
        boolean success = false;
        GameFieldCell gameFieldCell = (GameFieldCell) fieldCells[coordinate.getX()][coordinate.getY()];
        switch (gameFieldCell.getState()){
            case FreeWater:
                gameFieldCell.setState(CellState.WaterAttacked);
                success = false;
                break;
            case ShipPart:
                gameFieldCell.setState(CellState.ShipDamaged);
                success = true;
                break;
        }
        enemyFieldCellsList.remove(gameFieldCell);
        return success;
    }

    private void moveFromFieldCellsList(int x, int y){
        //enemyFieldCellsList.get()
    }

}
