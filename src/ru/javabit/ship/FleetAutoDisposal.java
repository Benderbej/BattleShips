package ru.javabit.ship;

import ru.javabit.gameField.*;
import ru.javabit.view.CellState;

import java.util.ArrayList;
import java.util.Random;

public class FleetAutoDisposal implements FleetDisposable {

    private Fleet fleet;
    public static GameField gameField;//player's gamefield
    public static FieldCell[][] fieldCells;//player's gamefield
    private static FieldCell[][] enemiesFieldCells;//enemy's gamefield

    private static Random r = new Random();


    public FleetAutoDisposal(Fleet fleet, GameField gameField){
        this.fleet = fleet;
        this.gameField = gameField;
        this.fieldCells = gameField.getPlayerFieldGrid().getCellsArr();
        disposeFleet(fleet.shipList);
        makeAllReservedCellsFreewater();
    }

    public void disposeFleet(ArrayList<Ship> shipList){
        for (Ship ship : shipList) {
            ship.placeShip();
        }
    }

    public static int getRandomPositiveInt(){
        int index = r.nextInt(gameField.getPlayerFieldGrid().getCellsArr().length -1)+1;
        return index;
    }

    public static int getRandomInt(int size){//довольно общий метод, сделать статическим?
        int id = r.nextInt(size);
        return id;
    }

    public static GameFieldCell getRandomPositiveCell(){
        int x = getRandomPositiveInt();
        int y = getRandomPositiveInt();
        GameFieldCell cell = (GameFieldCell) fieldCells[x][y];
        return cell;
    }

    public static ArrayList<FieldCell> findPossiblePositionsForCell(FieldCell fieldCell){//find vertical and horizontal neighbors ad add it ti list if in bounds
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

    public static FieldCell getFromPossiblePosotionsList(ArrayList<FieldCell> possiblePosotionsList){
        return possiblePosotionsList.get(r.nextInt(possiblePosotionsList.size()));
    }

    private static FieldCell getNeighborCell(FieldCell fieldCell, int deltaX, int deltaY){//getNeighborCell, in bounds, not ship or reserved(over ship neighbor)

        int x=fieldCell.getFieldCellCoordinate().getX()+deltaX;
        int y=fieldCell.getFieldCellCoordinate().getY()+deltaY;
        if(checkNotOutOfBounds(x,y,gameField.getColumnNum(),gameField.getRowNum())){
            GameFieldCell cell = (GameFieldCell) fieldCells[x][y];
            if((cell.getState() != CellState.Reserved) && (cell.getState() != CellState.ShipPart)) {//повтор есть в getVerticalCells и getHorizontalCells
                return cell;
            }
        }
        return fieldCells[0][0];
    }

    private static boolean fieldCellIsZero(FieldCell fieldCell){
        if(fieldCell.equals(fieldCells[0][0])){return true;} else {return false;}
    }

    private static boolean checkNotOutOfBounds(int x, int y, int maxX, int maxY){
        if((0 < x && x < maxX)&&(0 < y && y < maxY)) {return true;}
        return false;
    }

    private void makeAllReservedCellsFreewater(){//makeAllPaddingCellsFreewater
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

    public static void maskReservedArea(ArrayList<FieldCellCoordinate> resFieldCellCoords) {
        for(FieldCellCoordinate coordinate : resFieldCellCoords){
            if(checkNotOutOfBounds(coordinate.getX(), coordinate.getY(), gameField.getColumnNum(), gameField.getRowNum())) {
                GameFieldCell gameFieldCell = (GameFieldCell) fieldCells[coordinate.getX()][coordinate.getY()];
                if(gameFieldCell.getState()!=CellState.ShipPart){
                    gameFieldCell.setState(CellState.Reserved);
                }
            }
        }
    }
}