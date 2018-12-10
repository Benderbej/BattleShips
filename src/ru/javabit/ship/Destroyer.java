package ru.javabit.ship;

import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.GameFieldCell;

public class Destroyer extends Ship {

    Destroyer() {
        super(2, "Destroyer");
    }

    @Override
    public void placeShip(FleetDisposer disposer) throws BattleShipsException {
        super.placeShip(disposer);
        GameFieldCell fieldCell = placeStartShipCell();
        placeSecondShipCell(fieldCell);
        disposer.maskReservedArea(buildReservedArea());
    }
}