package ru.javabit.ship;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.FieldCellCoordinate;
import ru.javabit.gameField.GameFieldCell;

import java.util.ArrayList;

public abstract class Ship {

    int size;
    String name;
    ArrayList<FieldCell> cells;
    ShipPosition shipPosition;

    protected Ship(int size, String name){
        this.size = size;
        this.cells = new ArrayList<>(size);
    }





    private void placeShipCell(GameFieldCell fieldCell){
        cells.add(fieldCell);
        FleetAutoDisposal.setCellOccupied(fieldCell);
        //fieldCell.setSkin(CellState.ShipPart.getSkin());
    }

    private GameFieldCell placeStartShipCell(){
        GameFieldCell startShipCell = FleetAutoDisposal.getRandomPositiveCell();
        while(FleetAutoDisposal.checkIfCellOccupied(startShipCell)) {
            startShipCell = FleetAutoDisposal.getRandomPositiveCell();
        }
        placeShipCell(startShipCell);
        return startShipCell;
    }

    private void placeSecondShipCell(GameFieldCell startShipCell){
        ArrayList<FieldCell> fieldCells = FleetAutoDisposal.findPossiblePositionsForCell(startShipCell);
        //System.out.println("cells size"+fieldCells.size());
        if(fieldCells.size()>0) {
            FieldCell secondShipCell = FleetAutoDisposal.getFromPossiblePosotionsList(fieldCells);
            //System.out.println(" startShipCell.getX()="+startShipCell.getX() + " startShipCell.getY()="+startShipCell.getY()+" secondShipCell.getX()="+secondShipCell.getX()+" secondShipCell.getY="+secondShipCell.getY());
            if(startShipCell.getFieldCellCoordinate().getX() == secondShipCell.getFieldCellCoordinate().getX()){shipPosition = ShipPosition.Vertical;}
            if(startShipCell.getFieldCellCoordinate().getY() == secondShipCell.getFieldCellCoordinate().getY()){shipPosition = ShipPosition.Horizontal;}
            placeShipCell(startShipCell);
        } else {
            rebuildCurrentShip();
        }
    }

    private void placeOtherShipCell(){
        ArrayList<FieldCell> cells = null;
        GameFieldCell fieldCell = null;
        if (shipPosition == ShipPosition.Vertical){
            cells = getVerticalCells();
        }
        if (shipPosition == ShipPosition.Horizontal){
            cells = getHorizontalCells();
        }
        //System.out.println("cells size"+cells.size());
        if(cells.size()>1){
            fieldCell = (GameFieldCell) cells.get(FleetAutoDisposal.getRandomInt(cells.size()));
        }
        if(cells.size()==1){
            fieldCell = (GameFieldCell) cells.get(FleetAutoDisposal.getRandomInt(1));
        }
        if(cells.size()==0){
            //System.out.println("NO CELLS!");
            rebuildCurrentShip();
            return;
        }
        placeShipCell(fieldCell);
    }

    private ArrayList<FieldCell> getVerticalCells(){//похожий код, можно подумать о рефакторинге
        int x = cells.get(0).getFieldCellCoordinate().getX();
        ArrayList<FieldCell> possibleCellsList = new ArrayList<>(2);
        int minY = FleetAutoDisposal.getMinYCell(cells);
        int maxY = FleetAutoDisposal.getMaxYCell(cells);

        if(minY > 2) {
            GameFieldCell minYFieldCell = (GameFieldCell) FleetAutoDisposal.fieldCells[x][minY - 1];
            if(!FleetAutoDisposal.checkIfCellOccupied(minYFieldCell)){
                possibleCellsList.add(minYFieldCell);
            }
        }
        if (maxY < 10) {
            GameFieldCell maxYFieldCell = (GameFieldCell) FleetAutoDisposal.fieldCells[x][maxY + 1];
            if(!FleetAutoDisposal.checkIfCellOccupied(maxYFieldCell)){
                possibleCellsList.add(maxYFieldCell);
            }
        }
        return possibleCellsList;
    }

    private ArrayList<FieldCell> getHorizontalCells(){//похожий код, можно подумать о рефакторинге
        int y = cells.get(0).getFieldCellCoordinate().getY();
        ArrayList<FieldCell> possibleCellsList = new ArrayList<>(2);
        int minX = FleetAutoDisposal.getMinXCell(cells);
        int maxX = FleetAutoDisposal.getMaxXCell(cells);

        if (minX > 2) {
            GameFieldCell minXFieldCell = (GameFieldCell) FleetAutoDisposal.fieldCells[minX - 1][y];
            if(!FleetAutoDisposal.checkIfCellOccupied(minXFieldCell)){
                possibleCellsList.add(minXFieldCell);
            }
        }
        if (maxX < 10) {
            GameFieldCell maxXFieldCell = (GameFieldCell) FleetAutoDisposal.fieldCells[maxX + 1][y];
            if(!FleetAutoDisposal.checkIfCellOccupied(maxXFieldCell)){
                possibleCellsList.add(maxXFieldCell);
            }
        }
        return possibleCellsList;
    }

    public void placeShip(){
        GameFieldCell fieldCell = placeStartShipCell();
        //System.out.println(" "+fieldCell.getX()+" "+fieldCell.getY());
        placeSecondShipCell(fieldCell);
        if (size > 2) {
            for (int i = 0; i < size; i++) {
                placeOtherShipCell();
            }
        }
        //предусмотреть алгоритм, если корабль не влезает(добавили пару клеток а третья никак не лезет) пробовать строить корабль заново а клетки текущего стирать
        FleetAutoDisposal.maskReservedArea(buildReservedArea());

    }

    private ArrayList<FieldCellCoordinate> buildReservedArea(){
        ArrayList<FieldCellCoordinate> resFieldCellCoords = new ArrayList<>();
        if(shipPosition == ShipPosition.Horizontal){
            int maxX = FleetAutoDisposal.getMaxXCell(cells);
            int minX = FleetAutoDisposal.getMinXCell(cells);
            int y = cells.get(0).getFieldCellCoordinate().getY();
            int startX = minX-1;
            int finX = maxX+1;
            resFieldCellCoords.add(new FieldCellCoordinate(0,0));
            for (int i = startX; i <= finX; i++ ){
                resFieldCellCoords.add(new FieldCellCoordinate(i,y-1));
                resFieldCellCoords.add(new FieldCellCoordinate(i,y));
                resFieldCellCoords.add(new FieldCellCoordinate(i,y+1));
            }
        }
        if(shipPosition == ShipPosition.Vertical){
            int maxY = FleetAutoDisposal.getMaxYCell(cells);
            int minY = FleetAutoDisposal.getMinYCell(cells);
            int x = cells.get(0).getFieldCellCoordinate().getX();
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

    private void rebuildCurrentShip(){
        //System.out.println("rebuildship");
        //this.shipPosition = null;
        this.cells = new ArrayList<FieldCell>(size);

        placeShip();
    }



    //abstract void placeShip();

}