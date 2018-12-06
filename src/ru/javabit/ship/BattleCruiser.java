package ru.javabit.ship;

import ru.javabit.gameField.GameFieldCell;

public class BattleCruiser extends Ship {

    BattleCruiser(){
        super(4, "BattleCruiser");
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
        super.placeShip(disposer);
        GameFieldCell fieldCell = placeStartShipCellToCoast(cell);
        placeSecondShipCellToCoast(fieldCell);
        placeMoreThanSecondShipCell();
        disposer.maskReservedArea(buildReservedArea());
    }
}