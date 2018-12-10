package ru.javabit.ship;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.FieldCellCoordinate;
import ru.javabit.gameField.GameFieldCell;

import java.util.ArrayList;

/**
 * FleetAutoDisposer
 * FleetManualDisposer - TODO
 */

public interface FleetDisposer {

    void disposeFleet(ArrayList<Ship> shipList);

    GameFieldCell getRandomPositiveCell();

    ArrayList<FieldCell> findPossiblePositionsForCell(FieldCell fieldCell);

    int getRowNum();

    int getColumnNum();

    FieldCell[][] getFieldCells();

    void makeAllReservedCellsFreewater();

    void maskReservedArea(ArrayList<FieldCellCoordinate> resFieldCellCoords);

    GameFieldCell getRandomCellFromHashSet();
}