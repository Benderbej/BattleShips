package ru.javabit.ship;

import ru.javabit.gameField.CellState;
import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;
import ru.javabit.gameField.GameFieldCell;

import java.util.ArrayList;
import java.util.Random;

public class FleetAutoDisposal implements FleetDisposable {

    private FieldCell disposeCell;
    private Fleet fleet;
    private GameField gameField;
    private FieldCell[][] fieldCells;
    private Random r = new Random();





    public FleetAutoDisposal(Fleet fleet, GameField gameField){
        this.fleet = fleet;
        this.gameField = gameField;
        this.fieldCells = gameField.getGameFieldGrid().getCellsArr();
        //getRandomPisitiveInt();

        FieldCell fieldCell = getRandomPositiveCell();

        System.out.println(">>"+fieldCell.getSkin() + "<<");
        fieldCell.setSkin("@");
        System.out.println(">>"+fieldCell.getSkin() + "<<");
        ArrayList<FieldCell> fieldCells = findPossiblePositionsForCell(fieldCell);
        System.out.println(""+ fieldCells.size());
        printPossiblePositionsForCell(fieldCells);

    }

    private ArrayList<FieldCell> disposeShip(int size){
        ArrayList<FieldCell> cells = new ArrayList<>(size);

        return cells;
    }

    public void disposeFleet(ArrayList<FieldCell> shipCells){
        for (FieldCell cell : shipCells) {

        }
    }

    private void placeShipCell(){

    }

    private int getRandomPositiveInt(){
        int index = r.nextInt(gameField.getGameFieldGrid().getCellsArr().length -1)+1;
        return index;
    }

    private FieldCell getRandomPositiveCell(){
        int x = getRandomPositiveInt();
        int y = getRandomPositiveInt();
        FieldCell cell = fieldCells[x][y];
        return cell;
    }

    //



    private void setShipPaddings(){

    }

    private void setCellOccupied(FieldCell fieldCell){
        fieldCell.setSkin(CellState.ShipPart.getSkin());
    }

    private void setCellReserved(FieldCell fieldCell){
        fieldCell.setSkin(CellState.Reserved.getSkin());
    }


    private ArrayList<FieldCell> findPossiblePositionsForCell(FieldCell fieldCell){//find vertical and horizontal neighbors ad add it ti list if in bounds
        ArrayList<FieldCell> possibleCellsList = new ArrayList<>(5);
        FieldCell fieldCell1 = getNeighborCell(fieldCell, -1, 0);
        if(!fieldCellIsZero(fieldCell1)){possibleCellsList.add(fieldCell1);}
        FieldCell fieldCell2 = getNeighborCell(fieldCell, 0, -1);
        if(!fieldCellIsZero(fieldCell2)){possibleCellsList.add(fieldCell2);}
        FieldCell fieldCell3 = getNeighborCell(fieldCell, +1, 0);
        if(!fieldCellIsZero(fieldCell3)){possibleCellsList.add(fieldCell3);}
        FieldCell fieldCell4 = getNeighborCell(fieldCell, 0, +1);
        if(!fieldCellIsZero(fieldCell4)){possibleCellsList.add(fieldCell4);}
        return possibleCellsList;
    }

    private void printPossiblePositionsForCell(ArrayList<FieldCell> possibleCellsList){
        for (FieldCell cell: possibleCellsList) {
            System.out.println("PossiblePosition: x="+ cell.getX()+" y="+cell.getY());
        }
    }

    private FieldCell getNeighborCell(FieldCell fieldCell, int deltaX, int deltaY){
        int x=fieldCell.getX()+deltaX;
        int y=fieldCell.getY()+deltaY;
        if((0 < x && x < 9)&&(0 < y && y < 9)){return fieldCells[y][x];}else{return fieldCells[0][0];}
    }

    private boolean fieldCellIsZero(FieldCell fieldCell){
        if(fieldCell.equals(fieldCells[0][0])){return true;}else {return false;}
    }

    private ArrayList<FieldCell> findPossiblePositionsForCellGroup(FieldCell fieldCell){
        //if(fieldCell.){}
        return null;
    }


    private ArrayList<FieldCell> reserveFieldCells(FieldCell fieldCell){//reserve cells ares from occupying it by another ships(do it after replacing the ship)
        return null;
    }



    private boolean checkIfCellNotOutOfBounds(FieldCell fieldCell){
        boolean b = false;
        //if (){b = true;} else {b = false;}
        return b;
    }

    private boolean checkIfCellOccupied(FieldCell fieldCell){
        boolean b;
        if (fieldCell.getSkin() == CellState.Reserved.getSkin() && fieldCell.getSkin() == CellState.ShipPart.getSkin()){b = true;} else {b = false;}
        return b;
    }

    private void makeAllPaddingCellsFreewater(){
        for (FieldCell[] fieldCellArr: fieldCells) {
            for (FieldCell fieldCell : fieldCellArr) {
                if (fieldCell.getSkin() == CellState.Reserved.getSkin()){fieldCell.setSkin(CellState.FreeWater.getSkin());}
            }
        }
    }

}
