package ru.javabit.ship;

import ru.javabit.gameField.*;
import ru.javabit.view.CellState;

import java.util.ArrayList;
import java.util.Random;

public class FleetAutoDisposer implements FleetDisposable {

    private int rowNum;
    private int columnNum;
    public FieldCell[][] fieldCells;//player's gamefield
    private static Random r = new Random();

    public FleetAutoDisposer(int rowNum, int columnNum, FieldCell[][] fieldCells){
        this.fieldCells = fieldCells;
        this.rowNum = rowNum;
        this.columnNum = columnNum;
    }

    public void disposeFleet(ArrayList<Ship> shipList) {
        for (Ship ship : shipList) {
            ship.placeShip(this);
        }
        makeAllReservedCellsFreewater();
    }

    public int getRandomPositiveInt() {
        int index = r.nextInt(fieldCells.length -1)+1;
        return index;
    }

    public GameFieldCell getRandomPositiveCell() {
        int x = getRandomPositiveInt();
        int y = getRandomPositiveInt();
        GameFieldCell cell = (GameFieldCell) fieldCells[x][y];
        return cell;
    }

    public ArrayList<FieldCell> findPossiblePositionsForCell(FieldCell fieldCell) {//find vertical and horizontal neighbors ad add it ti list if in bounds
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

    private FieldCell getNeighborCell(FieldCell fieldCell, int deltaX, int deltaY){//getNeighborCell, in bounds, not ship or reserved(over ship neighbor)

        int x=fieldCell.getFieldCellCoordinate().getX()+deltaX;
        int y=fieldCell.getFieldCellCoordinate().getY()+deltaY;
        if(checkNotOutOfBounds(x,y,columnNum,rowNum)){
            GameFieldCell cell = (GameFieldCell) fieldCells[x][y];
            if((cell.getState() != CellState.Reserved) && (cell.getState() != CellState.ShipPart)) {//повтор есть в getVerticalCells и getHorizontalCells
                return cell;
            }
        }
        return fieldCells[0][0];
    }

    private boolean fieldCellIsZero(FieldCell fieldCell){
        if(fieldCell.equals(fieldCells[0][0])){return true;} else {return false;}
    }

    public void makeAllReservedCellsFreewater(){//makeAllPaddingCellsFreewater
        for (FieldCell[] fieldCellArr: fieldCells) {
            for (FieldCell fieldCell : fieldCellArr) {
                if(fieldCell instanceof GameFieldCell) {
                    if (((GameFieldCell) fieldCell).getState() == CellState.Reserved) {
                        ((GameFieldCell) fieldCell).setState(CellState.FreeWater);
                    }
                }
            }
        }
    }

    public void maskReservedArea(ArrayList<FieldCellCoordinate> resFieldCellCoords) {
        for(FieldCellCoordinate coordinate : resFieldCellCoords){
            if(checkNotOutOfBounds(coordinate.getX(), coordinate.getY(), columnNum, rowNum)) {
                GameFieldCell gameFieldCell = (GameFieldCell) fieldCells[coordinate.getX()][coordinate.getY()];
                if(gameFieldCell.getState()!=CellState.ShipPart){
                    gameFieldCell.setState(CellState.Reserved);
                }
            }
        }
    }

    public static int getRandomInt(int size) {
        int id = r.nextInt(size);
        return id;
    }

    public static FieldCell getFromPossiblePosotionsList(ArrayList<FieldCell> possiblePosotionsList) {
        return possiblePosotionsList.get(r.nextInt(possiblePosotionsList.size()));
    }

    private boolean checkNotOutOfBounds(int x, int y, int maxX, int maxY){
        if((0 < x && x < maxX)&&(0 < y && y < maxY)) {return true;}
        return false;
    }
}