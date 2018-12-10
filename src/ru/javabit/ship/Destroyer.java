package ru.javabit.ship;

import ru.javabit.gameField.GameFieldCell;

public class Destroyer extends Ship {

    Destroyer() {
        super(2, "Destroyer");
    }

    @Override
    public void placeShip(FleetDisposer disposer) {
        super.placeShip(disposer);
        GameFieldCell fieldCell = placeStartShipCell();
        placeSecondShipCell(fieldCell);
        disposer.maskReservedArea(buildReservedArea());
    }

    @Override
    public void placeShipToCoast(FleetDisposer disposer, GameFieldCell cell) {
        super.placeShipToCoast(disposer, cell);
        GameFieldCell fieldCell = placeStartShipCellToCoast(cell);
        placeSecondShipCellToCoast(fieldCell);
        disposer.maskReservedArea(buildReservedArea());
    }
}