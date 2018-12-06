package ru.javabit.ship;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameFieldCell;

import java.util.ArrayList;
import java.util.HashMap;

public class FleetPerelmanDisposer extends FleetAutoDisposer {

    private HashMap coastGameFieldCells;

    public FleetPerelmanDisposer(int rowNum, int columnNum, FieldCell[][] fieldCells) {
        super(rowNum, columnNum, fieldCells);
        coastGameFieldCells = new HashMap();
    }

    public void disposeFleet(ArrayList<Ship> shipList) {
        for (Ship ship : shipList) {
            if(ship.size >=2) {
                ship.placeShipToCoast(this, null );
            } else {
                ship.placeShip(this);
            }
        }
        makeAllReservedCellsFreewater();
    }

    public GameFieldCell getRandomCoastCell() {//список
        int x = getRandomPositiveInt(true);
        int y = getRandomPositiveInt(false);

        //private int rowNum;
        //private int columnNum;

        //int minX = ;
        //int minY = ;
        //int maxX = minX+;
        //int maxY = minY+ ;


        GameFieldCell cell = (GameFieldCell) fieldCells[x][y];
        coastGameFieldCells.remove(cell);
        return cell;
    }

    public Object getRandomHashMap(HashMap map){

        return coastGameFieldCells.get(map.size());
    }
}