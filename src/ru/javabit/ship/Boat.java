package ru.javabit.ship;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameFieldCell;

public class Boat extends Ship {

    Boat() {
        super(1, "Boat");
        this.shipPosition = ShipPosition.Unar;
    }

    @Override
    public void placeShip(FleetAutoDisposer disposer) {
        super.placeShip(disposer);
        placeStartShipCell();
        disposer.maskReservedArea(buildReservedArea());
    }

    @Override
    public void placeShipToCoast(FleetAutoDisposer disposer, GameFieldCell cell) {
        super.placeShip(disposer);
        placeStartShipCellToCoast(cell);
        disposer.maskReservedArea(buildReservedArea());
    }
}
