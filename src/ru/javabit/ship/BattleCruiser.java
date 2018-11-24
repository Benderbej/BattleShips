package ru.javabit.ship;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameFieldCell;

public class BattleCruiser extends Ship {

    BattleCruiser(){
        super(4, "BattleCruiser");
    }

    @Override
    public void placeShip(FleetAutoDisposer disposer) {
        super.placeShip(disposer);
        GameFieldCell fieldCell = placeStartShipCell();
        placeSecondShipCell(fieldCell);
        placeMoreThanSecondShipCell();
        disposer.maskReservedArea(buildReservedArea());
    }
}
