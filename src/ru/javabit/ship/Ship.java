package ru.javabit.ship;

import ru.javabit.SingleGame;
import ru.javabit.GameMath;
import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.FieldCellCoordinate;
import ru.javabit.gameField.GameFieldCell;
import ru.javabit.view.CellState;

import java.util.ArrayList;

/**
 * NB! алгоритм расстановки кораблей получился слишком сложен, и разделив его на класс корабля и класс растановщика выяснилось, что слишком много есть общей логики расстановки для всех кораблей, то есть
 * оказалось что много конкретной логики может содержать класс "корабль" и корабль таким образом не может быть интерфейсом. Можно попробовать создать интерфейс Disposable например, который будет описывать только ту логику
 * в кораблях, которай касается их расстановки(но пока это 90% всей их логики)
 * Тогда, если абстрактный класс "корабль" будет имплементировать Disposable,- на игровую карту можно будет размещать не только "корабли", но и делать какие-то сложные интересные карты, например, с береговыми объектами и т.д.
 *
 * в placeShipCell() мы модифицируем объекты GameFieldCell внутри массива fieldCells, который передает нам FleetAutoDisposer
 * и пополняем общий список боевых клеток cells(чтобы можно было проверить все ли клетки корабля уничтожены)
 */
public abstract class Ship {

    int size;
    String name;
    ArrayList<FieldCell> cells;//ship cells
    ShipPosition shipPosition;
    FleetDisposer disposer;
    boolean coastShip;


    protected Ship(int size, String name) {
        this.size = size;
        this.cells = new ArrayList<>(size);
        this.name = name;
    }

    public void placeShip(FleetDisposer disposer) throws BattleShipsException {
        this.disposer = disposer;
    }

    public void placeShipToCoast(FleetDisposer disposer) throws BattleShipsException {
        coastShip = true;
        placeShip(disposer);
    }

    //в placeShipCell() мы модифицируем объекты GameFieldCell внутри массива fieldCells, который передает нам FleetAutoDisposer
    //и пополняем общий список боевых клеток cells(чтобы можно было проверить все ли клетки корабля уничтожены)
    private void placeShipCell(GameFieldCell fieldCell) {
        cells.add(fieldCell);
        GameFieldCell.setCellOccupied(fieldCell);
    }

    GameFieldCell placeStartShipCell() throws BattleShipsException {
        GameFieldCell startShipCell=null;
        try {
            int i=0;
            if(coastShip == true) {
                startShipCell = disposer.getRandomCell();
                while (GameFieldCell.checkIfCellOccupied(startShipCell)) {
                    startShipCell = disposer.getRandomCell();
                    i++;
                    //if(i==10){throw new RuntimeException("too many attempts to find cell in placeStartShipCell() by getRandomCell()");}
                    if(i==100){throw new BattleShipsException("method getRandomCell() can not find and provide any cell to start ship disposing, may be field is too small or fleet too big, 100 attempts passed");}
                }
            } else {//default
                startShipCell = disposer.getRandomPositiveCell();
                while (GameFieldCell.checkIfCellOccupied(startShipCell)) {
                    startShipCell = disposer.getRandomPositiveCell();
                    i++;
                    //if(i==10){throw new RuntimeException("too many attempts to find cell in placeStartShipCell() by getRandomPositiveCell()");}
                    if(i==100){throw new BattleShipsException("method getRandomPositiveCell() can not find and provide any cell to start ship disposing, may be field is too small or fleet too big, 100 attempts passed");}
                }
            }
        } catch (BattleShipsException b) {
            StringBuilder info = new StringBuilder();
            info.append(System.getProperty("line.separator"));
            info.append("Potential rows*cols="+disposer.getColumnNum()*disposer.getRowNum() +" cols:"+disposer.getColumnNum()+ " rows:" +disposer.getRowNum());
            info.append(System.getProperty("line.separator"));
            if((disposer.getColumnNum()>1)&&(disposer.getRowNum()>1)) {
                info.append("Num of all field cells getFieldCells array size=" + (disposer.getFieldCells().length - 1) * (disposer.getFieldCells()[1].length));
                info.append(System.getProperty("line.separator"));
            }
            if(disposer instanceof FleetPerelmanDisposer) {
                info.append("FleetPerelmanDisposer: num of coast field cells: CoastGameFieldCellsSize=" + ((FleetPerelmanDisposer) disposer).getCoastGameFieldCellsSize());
                info.append(System.getProperty("line.separator"));
            }
            info.append("Fleet1 getShipListCellsCount=" + SingleGame.fleet1.getShipListCellsCount() + ", fleet2 getShipListCellsCount=" + SingleGame.fleet2.getShipListCellsCount());
            throw new BattleShipsException(b.getMessage() + info.toString());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        placeShipCell(startShipCell);
        return startShipCell;
    }

    void placeSecondShipCell(GameFieldCell startShipCell) throws BattleShipsException {
        ArrayList<FieldCell> fieldCellList = disposer.findPossiblePositionsForCell(startShipCell);
        if(fieldCellList.size()>0) {
            GameFieldCell secondShipCell = (GameFieldCell) GameMath.getFromPossiblePosotionsList(fieldCellList);
            if(startShipCell.getFieldCellCoordinate().getX() == secondShipCell.getFieldCellCoordinate().getX()){shipPosition = ShipPosition.Vertical;}
            if(startShipCell.getFieldCellCoordinate().getY() == secondShipCell.getFieldCellCoordinate().getY()){shipPosition = ShipPosition.Horizontal;}
            placeShipCell(secondShipCell);
        } else {
            rebuildCurrentShip();
        }
    }

    void placeMoreThanSecondShipCell() throws BattleShipsException {
        if (size > 2) {
            for (int i = 0; i < size-2; i++) {
                placeOtherShipCell();
            }
        }
    }

    void placeOtherShipCell() throws BattleShipsException {
        ArrayList<FieldCell> cells = null;
        GameFieldCell fieldCell = null;
        if (shipPosition == ShipPosition.Vertical){
            cells = getVerticalCells();
        }
        if (shipPosition == ShipPosition.Horizontal){
            cells = getHorizontalCells();
        }
        if(cells.size()>1){
            fieldCell = (GameFieldCell) cells.get(GameMath.getRandomInt(cells.size()));
        }
        if(cells.size()==1){
            fieldCell = (GameFieldCell) cells.get(GameMath.getRandomInt(1));
        }
        if(cells.size()==0){
            rebuildCurrentShip();
            return;
        }
        placeShipCell(fieldCell);
    }

    private ArrayList<FieldCell> getVerticalCells() {//похожий код, можно подумать о рефакторинге
        int x = cells.get(0).getFieldCellCoordinate().getX();
        ArrayList<FieldCell> possibleCellsList = new ArrayList<>(2);
        int minY = FieldCell.getMinYCell(cells);
        int maxY = FieldCell.getMaxYCell(cells);

        if(minY > 2) {
            GameFieldCell minYFieldCell = (GameFieldCell) disposer.getFieldCells()[x][minY - 1];
            if(!GameFieldCell.checkIfCellOccupied(minYFieldCell)){
                possibleCellsList.add(minYFieldCell);
            }
        }
        if (maxY < disposer.getRowNum()-1) {
            GameFieldCell maxYFieldCell = (GameFieldCell) disposer.getFieldCells()[x][maxY + 1];
            if(!GameFieldCell.checkIfCellOccupied(maxYFieldCell)){
                possibleCellsList.add(maxYFieldCell);
            }
        }
        return possibleCellsList;
    }

    private ArrayList<FieldCell> getHorizontalCells() {//похожий код, можно подумать о рефакторинге
        int y = cells.get(0).getFieldCellCoordinate().getY();
        ArrayList<FieldCell> possibleCellsList = new ArrayList<>(2);
        int minX = FieldCell.getMinXCell(cells);
        int maxX = FieldCell.getMaxXCell(cells);

        if (minX > 2) {
            GameFieldCell minXFieldCell = (GameFieldCell) disposer.getFieldCells()[minX - 1][y];
            if(!GameFieldCell.checkIfCellOccupied(minXFieldCell)){
                possibleCellsList.add(minXFieldCell);
            }
        }
        if (maxX < (disposer.getColumnNum()-1)) {
            GameFieldCell maxXFieldCell = (GameFieldCell) disposer.getFieldCells()[maxX + 1][y];
            if(!GameFieldCell.checkIfCellOccupied(maxXFieldCell)){
                possibleCellsList.add(maxXFieldCell);
            }
        }
        return possibleCellsList;
    }

    ArrayList<FieldCellCoordinate> buildReservedArea() {//reserve cells ares from occupying it by another ships(do it after replacing the ship)
        ArrayList<FieldCellCoordinate> resFieldCellCoords = new ArrayList<>();
        if(shipPosition == ShipPosition.Unar){//Unar is the same as horizontal the same as vertical!!!
            int maxX = FieldCell.getMaxXCell(cells);
            int minX = FieldCell.getMinXCell(cells);
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
        if(shipPosition == ShipPosition.Horizontal){
            int maxX = FieldCell.getMaxXCell(cells);
            int minX = FieldCell.getMinXCell(cells);
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
            int maxY = FieldCell.getMaxYCell(cells);
            int minY = FieldCell.getMinYCell(cells);
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

    private void rebuildCurrentShip() throws BattleShipsException {
        clearUnplacedShip();
        this.cells = new ArrayList<FieldCell>(size);
        placeShip(disposer);
    }

    private void clearUnplacedShip(){
        GameFieldCell gameFieldCell;
        for (FieldCell cell: cells) {
            gameFieldCell = (GameFieldCell) disposer.getFieldCells()[cell.getFieldCellCoordinate().getX()][cell.getFieldCellCoordinate().getY()];
            gameFieldCell.setState(CellState.FreeWater);
        }
    }

    public int getSize() {
        return size;
    }
}