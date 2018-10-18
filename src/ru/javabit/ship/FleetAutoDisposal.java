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
        FieldCell fieldCell = placeStartShipCell(ship);
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

    private void placeShipCell(Ship ship, FieldCell fieldCell){
        ship.cells.add(fieldCell);
        setCellOccupied(fieldCell);
        //fieldCell.setSkin(CellState.ShipPart.getSkin());
    }

    private FieldCell placeStartShipCell(Ship ship){
        FieldCell startShipCell = getRandomPositiveCell();
        while(checkIfCellOccupied(startShipCell)) {
            startShipCell = getRandomPositiveCell();
        }
        placeShipCell(ship, startShipCell);
        return startShipCell;
    }

    private void placeSecondShipCell(Ship ship, FieldCell startShipCell){
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
        FieldCell fieldCell = null;
        if (ship.shipPosition == ShipPosition.Vertical){
            cells = getVerticalCells(ship);
        }
        if (ship.shipPosition == ShipPosition.Horizontal){
            cells = getHorizontalCells(ship);
        }
        //System.out.println("cells size"+cells.size());
        if(cells.size()>1){
            fieldCell = cells.get(getRandomInt(cells.size()));
        }
        if(cells.size()==1){
            fieldCell = cells.get(getRandomInt(1));
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
            FieldCell minYFieldCell = fieldCells[x][minY - 1];
            if ((minYFieldCell.getSkin() != CellState.Reserved.getSkin()) && (minYFieldCell.getSkin() != CellState.ShipPart.getSkin())) {
                possibleCellsList.add(minYFieldCell);
            }
        }
        if (maxY < 10) {
            FieldCell maxYFieldCell = fieldCells[x][maxY + 1];
            if ((maxYFieldCell.getSkin() != CellState.Reserved.getSkin()) && (maxYFieldCell.getSkin() != CellState.ShipPart.getSkin())) {
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
                FieldCell minXFieldCell = fieldCells[minX - 1][y];
                if ((minXFieldCell.getSkin() != CellState.Reserved.getSkin()) && (minXFieldCell.getSkin() != CellState.ShipPart.getSkin())) {
                    possibleCellsList.add(minXFieldCell);
                }
            }
            if (maxX < 10) {
                FieldCell maxXFieldCell = fieldCells[maxX + 1][y];
                if ((maxXFieldCell.getSkin() != CellState.Reserved.getSkin()) && (maxXFieldCell.getSkin() != CellState.ShipPart.getSkin())) {
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
        //System.out.println("skin="+fieldCell.getSkin());
        if ((fieldCell.getSkin() == CellState.Reserved.getSkin()) || (fieldCell.getSkin() == CellState.ShipPart.getSkin())){b = true;} else {b = false;}
        return b;
    }

    private void makeAllReservedCellsFreewater(){//makeAllPaddingCellsFreewater
        for (FieldCell[] fieldCellArr: fieldCells) {
            for (FieldCell fieldCell : fieldCellArr) {
                if (fieldCell.getSkin() == CellState.Reserved.getSkin()){fieldCell.setSkin(CellState.FreeWater.getSkin());}
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
                if(fieldCells[coordinate.getX()][coordinate.getY()].getSkin()!=CellState.ShipPart.getSkin()){
                    fieldCells[coordinate.getX()][coordinate.getY()].setSkin(CellState.Reserved.getSkin());
                }
            }
        }
    }
}