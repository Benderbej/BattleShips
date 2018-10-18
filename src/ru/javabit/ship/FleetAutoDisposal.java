package ru.javabit.ship;

import ru.javabit.gameField.*;
import ru.javabit.view.CellState;

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
        disposeFleet(fleet.shipList);
        makeAllReservedCellsFreewater();
    }

    private ArrayList<FieldCell> disposeShip(int size){
        ArrayList<FieldCell> cells = new ArrayList<>(size);

        return cells;
    }

    public void disposeFleet(ArrayList<Ship> shipList){
        for (Ship ship : shipList) {
            placeShip(ship);
        }
    }

    private void placeShip(Ship ship){
        GameFieldCell fieldCell = placeStartShipCell(ship);
        //System.out.println(" "+fieldCell.getX()+" "+fieldCell.getY());
        placeSecondShipCell(ship, fieldCell);
        if (ship.size > 2) {
            for (int i = 0; i < ship.size; i++) {
                placeOtherShipCell(ship);
            }
        }
        //предусмотреть алгоритм, если корабль не влезает(добавили пару клеток а третья никак не лезет) пробовать строить корабль заново а клетки текущего стирать
        maskReservedArea(buildReservedArea(ship));

    }

    private void setShipPosition(){

    }

    private void placeShipCell(Ship ship, GameFieldCell fieldCell){
        ship.cells.add(fieldCell);
        setCellOccupied(fieldCell);
        //fieldCell.setSkin(CellState.ShipPart.getSkin());
    }

    private GameFieldCell placeStartShipCell(Ship ship){
        GameFieldCell startShipCell = getRandomPositiveCell();
        while(checkIfCellOccupied(startShipCell)) {
            startShipCell = getRandomPositiveCell();
        }
        placeShipCell(ship, startShipCell);
        return startShipCell;
    }

    private void placeSecondShipCell(Ship ship, GameFieldCell startShipCell){
        ArrayList<FieldCell> fieldCells = findPossiblePositionsForCell(startShipCell);
        //System.out.println("cells size"+fieldCells.size());
        if(fieldCells.size()>0) {
            FieldCell secondShipCell = getFromPossiblePosotionsList(fieldCells);
            //System.out.println(" startShipCell.getX()="+startShipCell.getX() + " startShipCell.getY()="+startShipCell.getY()+" secondShipCell.getX()="+secondShipCell.getX()+" secondShipCell.getY="+secondShipCell.getY());
            if(startShipCell.getFieldCellCoordinate().getX() == secondShipCell.getFieldCellCoordinate().getX()){ship.shipPosition = ShipPosition.Vertical;}
            if(startShipCell.getFieldCellCoordinate().getY() == secondShipCell.getFieldCellCoordinate().getY()){ship.shipPosition = ShipPosition.Horizontal;}
            placeShipCell(ship, startShipCell);
        } else {
            rebuildCurrentShip(ship);
        }


    }

    private void placeOtherShipCell(Ship ship){
        ArrayList<FieldCell> cells = null;
        GameFieldCell fieldCell = null;
        if (ship.shipPosition == ShipPosition.Vertical){
            cells = getVerticalCells(ship);
        }
        if (ship.shipPosition == ShipPosition.Horizontal){
            cells = getHorizontalCells(ship);
        }
        //System.out.println("cells size"+cells.size());
        if(cells.size()>1){
            fieldCell = (GameFieldCell) cells.get(getRandomInt(cells.size()));
        }
        if(cells.size()==1){
            fieldCell = (GameFieldCell) cells.get(getRandomInt(1));
        }
        if(cells.size()==0){
            //System.out.println("NO CELLS!");
            rebuildCurrentShip(ship);
            return;
        }


        //fieldCell = cells.get(getRandomInt(1));
        //fieldCell = cells.get(0);

        placeShipCell(ship, fieldCell);

    }

    private void rebuildCurrentShip(Ship ship){
        //System.out.println("rebuildship");
        ship.shipPosition = null;
        ship.cells = new ArrayList<FieldCell>(ship.size);
        placeShip(ship);
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
        int x = ship.cells.get(0).getFieldCellCoordinate().getX();
        ArrayList<FieldCell> possibleCellsList = new ArrayList<>(2);
        int minY = getMinYCell(ship.cells);
        int maxY = getMaxYCell(ship.cells);

        if(minY > 2) {
            GameFieldCell minYFieldCell = (GameFieldCell) fieldCells[x][minY - 1];
            if(!checkIfCellOccupied(minYFieldCell)){
                possibleCellsList.add(minYFieldCell);
            }
        }
        if (maxY < 10) {
            GameFieldCell maxYFieldCell = (GameFieldCell) fieldCells[x][maxY + 1];
            if(!checkIfCellOccupied(maxYFieldCell)){
                possibleCellsList.add(maxYFieldCell);
            }
        }
        return possibleCellsList;
    }

    private ArrayList<FieldCell> getHorizontalCells(Ship ship){//похожий код, можно подумать о рефакторинге
            int y = ship.cells.get(0).getFieldCellCoordinate().getY();
            ArrayList<FieldCell> possibleCellsList = new ArrayList<>(2);
            int minX = getMinXCell(ship.cells);
            int maxX = getMaxXCell(ship.cells);

            if (minX > 2) {
                GameFieldCell minXFieldCell = (GameFieldCell) fieldCells[minX - 1][y];
                if(!checkIfCellOccupied(minXFieldCell)){
                    possibleCellsList.add(minXFieldCell);
                }
            }
            if (maxX < 10) {
                GameFieldCell maxXFieldCell = (GameFieldCell) fieldCells[maxX + 1][y];
                if(!checkIfCellOccupied(maxXFieldCell)){
                    possibleCellsList.add(maxXFieldCell);
                }
            }
        return possibleCellsList;
    }

    private int getMinXCell(ArrayList<FieldCell> cells){//похожий код, можно подумать о рефакторинге
        int  x = 11;
        for (FieldCell cell: cells) {
            if (cell.getFieldCellCoordinate().getX() < x){x = cell.getFieldCellCoordinate().getX();}
        }
        return x;
    }
    private int getMaxXCell(ArrayList<FieldCell> cells){//похожий код, можно подумать о рефакторинге
        int  x = 0;
        for (FieldCell cell: cells) {
            if (cell.getFieldCellCoordinate().getX() > x){x = cell.getFieldCellCoordinate().getX();}
        }
        return x;
    }
    private int getMinYCell(ArrayList<FieldCell> cells){//похожий код, можно подумать о рефакторинге
        int  y = 11;
        for (FieldCell cell: cells) {
            if (cell.getFieldCellCoordinate().getY() < y){y = cell.getFieldCellCoordinate().getY();}
        }
        return y;
    }
    private int getMaxYCell(ArrayList<FieldCell> cells){//похожий код, можно подумать о рефакторинге
        int  y = 0;
        for (FieldCell cell: cells) {
            if (cell.getFieldCellCoordinate().getY() > y){y = cell.getFieldCellCoordinate().getY();}
        }
        return y;
    }

    private GameFieldCell getRandomPositiveCell(){
        int x = getRandomPositiveInt();
        int y = getRandomPositiveInt();
        GameFieldCell cell = (GameFieldCell) fieldCells[x][y];
        return cell;
    }


    //

    private void setShipPaddings(){//invoke after replacing ship!

    }

    private void setCellOccupied(GameFieldCell fieldCell){
        fieldCell.setState(CellState.ShipPart);
    }

    private void setCellReserved(GameFieldCell fieldCell){
        fieldCell.setState(CellState.Reserved);
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
            System.out.println("PossiblePosition: x="+ cell.getFieldCellCoordinate().getX()+" y="+cell.getFieldCellCoordinate().getY());
        }
    }

    private FieldCell getFromPossiblePosotionsList(ArrayList<FieldCell> possiblePosotionsList){
        return possiblePosotionsList.get(r.nextInt(possiblePosotionsList.size()));
    }

    private FieldCell getNeighborCell(FieldCell fieldCell, int deltaX, int deltaY){//getNeighborCell, in bounds, not ship or reserved(over ship neighbor)

        int x=fieldCell.getFieldCellCoordinate().getX()+deltaX;
        int y=fieldCell.getFieldCellCoordinate().getY()+deltaY;
        if((0 < x && x < 11)&&(0 < y && y < 11)){
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

    private ArrayList<FieldCell> reserveFieldCells(FieldCell fieldCell){//reserve cells ares from occupying it by another ships(do it after replacing the ship)
        return null;
    }

    private boolean checkIfCellNotOutOfBounds(FieldCell fieldCell){
        boolean b = false;
        //if (){b = true;} else {b = false;}
        return b;
    }

    private boolean checkIfCellOccupied(GameFieldCell fieldCell){
        boolean b;
        if ((fieldCell.getState() == CellState.Reserved) || (fieldCell.getState() == CellState.ShipPart)){b = true;} else {b = false;}
        return b;
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

    private ArrayList<FieldCellCoordinate> buildReservedArea(Ship ship){
        ArrayList<FieldCellCoordinate> resFieldCellCoords = new ArrayList<>();
        if(ship.shipPosition == ShipPosition.Horizontal){
            int maxX = getMaxXCell(ship.cells);
            int minX = getMinXCell(ship.cells);
            int y = ship.cells.get(0).getFieldCellCoordinate().getY();
            int startX = minX-1;
            int finX = maxX+1;
            resFieldCellCoords.add(new FieldCellCoordinate(0,0));
            for (int i = startX; i <= finX; i++ ){
                resFieldCellCoords.add(new FieldCellCoordinate(i,y-1));
                resFieldCellCoords.add(new FieldCellCoordinate(i,y));
                resFieldCellCoords.add(new FieldCellCoordinate(i,y+1));
            }
        }
        if(ship.shipPosition == ShipPosition.Vertical){
            int maxY = getMaxYCell(ship.cells);
            int minY = getMinYCell(ship.cells);
            int x = ship.cells.get(0).getFieldCellCoordinate().getX();
            int startY = minY-1;
            int finY = maxY+1;
            resFieldCellCoords.add(new FieldCellCoordinate(0,0));
            for (int i = startY; i <= finY; i++ ){
                resFieldCellCoords.add(new FieldCellCoordinate(x-1,i));
                resFieldCellCoords.add(new FieldCellCoordinate(x,i));
                resFieldCellCoords.add(new FieldCellCoordinate(x+1,i));
            }
        }
        return resFieldCellCoords;
    }

    private void maskReservedArea(ArrayList<FieldCellCoordinate> resFieldCellCoords) {
        for(FieldCellCoordinate coordinate : resFieldCellCoords){
            if((0 < coordinate.getX() && coordinate.getX() < 11)&&(0 < coordinate.getY() && coordinate.getY() < 11)){
                GameFieldCell gameFieldCell = (GameFieldCell) fieldCells[coordinate.getX()][coordinate.getY()];
                if(gameFieldCell.getState()!=CellState.ShipPart){
                    gameFieldCell.setState(CellState.Reserved);
                }
            }
        }
    }
}