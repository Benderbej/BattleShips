package ru.javabit.ship;

import ru.javabit.gameField.GameFieldCell;

public class BattleCruiser extends Ship {

    BattleCruiser(){
        super(4, "BattleCruiser");
    }

    @Override
    public void placeShip() {
        GameFieldCell fieldCell = placeStartShipCell();
        placeSecondShipCell(fieldCell);
        if (size > 2) {
            for (int i = 0; i < size; i++) {
                placeOtherShipCell();
            }
        }
        FleetAutoDisposal.maskReservedArea(buildReservedArea());
    }
}
