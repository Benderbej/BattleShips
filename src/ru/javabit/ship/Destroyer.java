package ru.javabit.ship;

import ru.javabit.gameField.GameFieldCell;

public class Destroyer extends Ship {

    Destroyer() {
        super(2, "Destroyer");
    }

    @Override
    public void placeShip() {
        GameFieldCell fieldCell = placeStartShipCell();
        placeSecondShipCell(fieldCell);
        FleetAutoDisposer.maskReservedArea(buildReservedArea());
    }
}
