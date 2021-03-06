package ru.javabit.ship;

import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameFieldCell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class FleetPerelmanDisposer extends FleetAutoDisposer {

    private HashSet<GameFieldCell> coastGameFieldCells;
    private int coastGameFieldCellsSize;//needed for exception info

    public FleetPerelmanDisposer(int rowNum, int columnNum, FieldCell[][] fieldCells) {
        super(rowNum, columnNum, fieldCells);
        coastGameFieldCells = new HashSet();
        setRandomCoastCells();
    }

    public void disposeFleet(ArrayList<Ship> shipList) throws BattleShipsException {
        for (Ship ship : shipList) {
            if(ship.size >=2) {
                //ship.placeShipToCoast(this, getRandomCellFromHashSet());
                ship.placeShipToCoast(this);
            } else {
                ship.placeShip(this);
            }
        }
        makeAllReservedCellsFreewater();
    }

    private void setRandomCoastCells() {
        for (int i=1; i < getColumnNum(); i++) {
            coastGameFieldCells.add((GameFieldCell) fieldCells[i][1]);
            coastGameFieldCells.add((GameFieldCell) fieldCells[i][getRowNum()-1]);
        }
        for (int j=1; j < getRowNum(); j++) {
            coastGameFieldCells.add((GameFieldCell) fieldCells[1][j]);
            coastGameFieldCells.add((GameFieldCell) fieldCells[getColumnNum()-1][j]);
        }
        coastGameFieldCellsSize = coastGameFieldCells.size();
    }

    public GameFieldCell getRandomCoastCell() {//список
        return null;
    }

    public GameFieldCell getRandomCellFromHashSet() {
        int size = coastGameFieldCells.size();
        int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
        int i = 0;
        for(GameFieldCell obj : coastGameFieldCells)
        {
            if (i == item)
                return obj;
            i++;
        }
        return null;
    }

    @Override
    public GameFieldCell getRandomCell() {
        return getRandomCellFromHashSet();
    }

    public int getCoastGameFieldCellsSize() {
        return coastGameFieldCellsSize;
    }
}