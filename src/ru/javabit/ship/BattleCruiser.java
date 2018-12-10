package ru.javabit.ship;

import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.GameFieldCell;

public class BattleCruiser extends Ship {

    BattleCruiser(){
        super(4, "BattleCruiser");
    }

    @Override
    public void placeShip(FleetDisposer disposer) throws BattleShipsException {
        super.placeShip(disposer);
        GameFieldCell fieldCell = placeStartShipCell();
        placeSecondShipCell(fieldCell);
        placeMoreThanSecondShipCell();
        disposer.maskReservedArea(buildReservedArea());
    }
}