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

        //System.out.println(">>"+fieldCell.getSkin() + "<<");
        //fieldCell.setSkin("@");
        //System.out.println(">>"+fieldCell.getSkin() + "<<");
        //ArrayList<FieldCell> fieldCells = findPossiblePositionsForCell(fieldCell);
        //System.out.println(""+ fieldCells.size());
        //printPossiblePositionsForCell(fieldCells);

        Ship ship = fleet.shipList.get(0);
        placeShip(ship);

    }

    private ArrayList<FieldCell> disposeShip(int size){
        ArrayList<FieldCell> cells = new ArrayList<>(size);

        return cells;
    }

    public void disposeFleet(ArrayList<FieldCell> shipCells){
        for (FieldCell cell : shipCells) {

        }
    }

    private void placeShip(Ship ship){
        //int size = ship.size;
        System.out.println("placeShip");
        FieldCell fieldCell = placeStartShipCell(ship);
        System.out.println(" "+fieldCell.getX()+" "+fieldCell.getY());
        placeSecondShipCell(ship, fieldCell);
        System.out.println("ship.size="+ship.size);
        if (ship.size > 2) {
            for (int i = 0; i < ship.size; i++) {
                placeOtherShipCell(ship);
            }
        }
        //предусмотреть алгоритм, если корабль не влезает(добавили пару клеток а третья никак не лезет) пробовать строить корабль заново а клетки текущего стирать


    }

    private void setShipPosition(){

    }

    private void placeShipCell(Ship ship, FieldCell fieldCell){
        ship.cells.add(fieldCell);
        fieldCell.setSkin(CellState.ShipPart.getSkin());
    }

    private FieldCell placeStartShipCell(Ship ship){
        System.out.println("placeStartShipCell()");
        FieldCell startShipCell = getRandomPositiveCell();
        placeShipCell(ship, startShipCell);
        return startShipCell;
    }

    private void placeSecondShipCell(Ship ship, FieldCell startShipCell){
        ArrayList<FieldCell> fieldCells = findPossiblePositionsForCell(startShipCell);
        FieldCell secondShipCell = getFromPossiblePosotionsList(fieldCells);
        System.out.println(" startShipCell.getX()="+startShipCell.getX() + " startShipCell.getY()="+startShipCell.getY()+" secondShipCell.getX()="+secondShipCell.getX()+" secondShipCell.getY="+secondShipCell.getY());
        if(startShipCell.getX() == secondShipCell.getX()){ship.shipPosition = ShipPosition.Vertical;}
        if(startShipCell.getY() == secondShipCell.getY()){ship.shipPosition = ShipPosition.Horizontal;}
        placeShipCell(ship, startShipCell);
    }

    private void placeOtherShipCell(Ship ship){
        System.out.println("placeOtherShipCell()");
        ArrayList<FieldCell> cells = null;
        FieldCell fieldCell;
        if (ship.shipPosition == ShipPosition.Vertical){
            cells = getVerticalCells(ship);
            //fieldCell = cells.get(getRandomInt(2));
            //placeShipCell(ship, fieldCell);
            System.out.println("Vertical");
        }
        if (ship.shipPosition == ShipPosition.Horizontal){
            cells = getHorizontalCells(ship);
            //fieldCell = cells.get(getRandomInt(2));
            //placeShipCell(ship, fieldCell);
            System.out.println("Horizontal");
        }

        System.out.println("cells size"+cells.size());
        fieldCell = cells.get(getRandomInt(2));
        placeShipCell(ship, fieldCell);

    }

    private int getRandomPositiveInt(){
        int index = r.nextInt(gameField.getGameFieldGrid().getCellsArr().length -1)+1;
        return index;
    }

    private int getRandomInt(int size){//довольно общий метод, сделать статическим?
        int id = r.nextInt(size);
        return id;
    }

    private ArrayList<FieldCell> getVerticalCells(Ship ship){//похожий код, можно подумать о рефакторинге
        int x = ship.cells.get(0).getX();
        ArrayList<FieldCell> possibleCellsList = new ArrayList<>(2);
        int minY = getMinYCell(ship.cells);
        int maxY = getMaxYCell(ship.cells);
        FieldCell minYFieldCell = fieldCells[x][minY-1];
        if((minY > 1)&&((minYFieldCell.getSkin() == CellState.Reserved.getSkin()) && (minYFieldCell.getSkin() == CellState.ShipPart.getSkin()))){possibleCellsList.add(minYFieldCell);}
        FieldCell maxYFieldCell = fieldCells[x][maxY+1];
        if((maxY < 11)&&((maxYFieldCell.getSkin() == CellState.Reserved.getSkin()) && (maxYFieldCell.getSkin() == CellState.ShipPart.getSkin()))){possibleCellsList.add(maxYFieldCell);}
        return possibleCellsList;
    }

    private ArrayList<FieldCell> getHorizontalCells(Ship ship){//похожий код, можно подумать о рефакторинге
        int y = ship.cells.get(0).getY();
        ArrayList<FieldCell> possibleCellsList = new ArrayList<>(2);
        int minX = getMinXCell(ship.cells);
        int maxX = getMaxXCell(ship.cells);
        FieldCell minXFieldCell = fieldCells[minX-1][y];
        if((minX > 1)&&((minXFieldCell.getSkin() == CellState.Reserved.getSkin()) && (minXFieldCell.getSkin() == CellState.ShipPart.getSkin()))){possibleCellsList.add(minXFieldCell);}
        FieldCell maxXFieldCell = fieldCells[maxX+1][y];
        if((maxX < 11)&&((maxXFieldCell.getSkin() == CellState.Reserved.getSkin()) && (maxXFieldCell.getSkin() == CellState.ShipPart.getSkin()))){possibleCellsList.add(maxXFieldCell);}
        return possibleCellsList;
    }

    private int getMinXCell(ArrayList<FieldCell> cells){//похожий код, можно подумать о рефакторинге
        int  x = 11;
        for (FieldCell cell: cells) {
            if (cell.getX() < x){x = cell.getX();}
        }
        return x;
    }
    private int getMaxXCell(ArrayList<FieldCell> cells){//похожий код, можно подумать о рефакторинге
        int  x = 0;
        for (FieldCell cell: cells) {
            if (cell.getX() > x){x = cell.getX();}
        }
        return x;
    }
    private int getMinYCell(ArrayList<FieldCell> cells){//похожий код, можно подумать о рефакторинге
        int  y = 11;
        for (FieldCell cell: cells) {
            if (cell.getY() < y){y = cell.getY();}
        }
        return y;
    }
    private int getMaxYCell(ArrayList<FieldCell> cells){//похожий код, можно подумать о рефакторинге
        int  y = 0;
        for (FieldCell cell: cells) {
            if (cell.getY() > y){y = cell.getY();}
        }
        return y;
    }

    private FieldCell getRandomPositiveCell(){
        int x = getRandomPositiveInt();
        int y = getRandomPositiveInt();
        FieldCell cell = fieldCells[x][y];
        return cell;
    }

    //

    private void setShipPaddings(){//invoke after replacing ship!

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
        System.out.println(" "+ fieldCell1.getX() + " " + fieldCell1.getY());
        if(!fieldCellIsZero(fieldCell1)){possibleCellsList.add(fieldCell1);}
        System.out.println(" "+ fieldCell1.getX() + " " + fieldCell1.getY());
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

    private FieldCell getFromPossiblePosotionsList(ArrayList<FieldCell> possiblePosotionsList){
        return possiblePosotionsList.get(r.nextInt(possiblePosotionsList.size()));
    }

    private FieldCell getNeighborCell(FieldCell fieldCell, int deltaX, int deltaY){//getNeighborCell, in bounds, not ship or reserved(over ship neighbor)

        int x=fieldCell.getX()+deltaX;
        int y=fieldCell.getY()+deltaY;
        if((0 < x && x < 11)&&(0 < y && y < 11)){
            FieldCell cell = fieldCells[x][y];
            if((cell.getSkin() != CellState.Reserved.getSkin()) && (cell.getSkin() != CellState.ShipPart.getSkin())) {//повтор есть в getVerticalCells и getHorizontalCells
                return cell;
            }
        }
        return fieldCells[0][0];
    }

    private boolean fieldCellIsZero(FieldCell fieldCell){
        if(fieldCell.equals(fieldCells[0][0])){return true;} else {return false;}
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