package ru.javabit.ship;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameFieldCell;

public class Cruiser extends Ship {

    Cruiser() {
        super(3, "Cruiser");
    }

    @Override
    public void placeShip(FleetAutoDisposer disposer) {
        super.placeShip(disposer);
        GameFieldCell fieldCell = placeStartShipCell();
        placeSecondShipCell(fieldCell);
        placeMoreThanSecondShipCell();
        disposer.maskReservedArea(buildReservedArea());
    }

    @Override
    public void placeShipToCoast(FleetAutoDisposer disposer, GameFieldCell cell) {
        super.placeShip(disposer);
        GameFieldCell fieldCell = placeStartShipCellToCoast(cell);
        placeSecondShipCellToCoast(fieldCell);
        placeMoreThanSecondShipCell();
        disposer.maskReservedArea(buildReservedArea());
    }
}