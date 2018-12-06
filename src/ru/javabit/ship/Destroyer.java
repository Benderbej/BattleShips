package ru.javabit.ship;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameFieldCell;

public class Destroyer extends Ship {

    Destroyer() {
        super(2, "Destroyer");
    }

    @Override
    public void placeShip(FleetAutoDisposer disposer) {
        super.placeShip(disposer);
        GameFieldCell fieldCell = placeStartShipCell();
        placeSecondShipCell(fieldCell);
        disposer.maskReservedArea(buildReservedArea());
    }

    @Override
    public void placeShipToCoast(FleetAutoDisposer disposer, GameFieldCell cell) {
        super.placeShip(disposer);
        GameFieldCell fieldCell = placeStartShipCellToCoast(cell);
        placeSecondShipCellToCoast(fieldCell);
        disposer.maskReservedArea(buildReservedArea());
    }
}
