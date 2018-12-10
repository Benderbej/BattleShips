package ru.javabit.ship;

import ru.javabit.gameField.GameFieldCell;

public class Cruiser extends Ship {

    Cruiser() {
        super(3, "Cruiser");
    }

    @Override
    public void placeShip(FleetDisposer disposer) {
        super.placeShip(disposer);
        GameFieldCell fieldCell = placeStartShipCell();
        placeSecondShipCell(fieldCell);
        placeMoreThanSecondShipCell();
        disposer.maskReservedArea(buildReservedArea());
    }

    @Override
    public void placeShipToCoast(FleetDisposer disposer, GameFieldCell cell) {
        super.placeShipToCoast(disposer, cell);
        GameFieldCell fieldCell = placeStartShipCellToCoast(cell);
        placeSecondShipCellToCoast(fieldCell);
        placeMoreThanSecondShipCell();
        disposer.maskReservedArea(buildReservedArea());
    }
}