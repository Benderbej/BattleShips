package ru.javabit.ship;

import ru.javabit.gameField.GameFieldCell;

public class Cruiser extends Ship {

    Cruiser() {
        super(3, "Cruiser");
    }

    @Override
    public void placeShip() {
        GameFieldCell fieldCell = placeStartShipCell();
        //System.out.println(" "+fieldCell.getX()+" "+fieldCell.getY());
        placeSecondShipCell(fieldCell);
        if (size > 2) {
            for (int i = 0; i < size; i++) {
                placeOtherShipCell();
            }
        }
        FleetAutoDisposal.maskReservedArea(buildReservedArea());
    }
}
