package ru.javabit.ship;

import ru.javabit.gameField.FieldCell;

public class Boat extends Ship {

    Boat(){
        super(1, "Boat");
        this.shipPosition = ShipPosition.Unar;
    }

    @Override
    public void placeShip(FleetAutoDisposer disposer) {
        super.placeShip(disposer);
        placeStartShipCell();
        disposer.maskReservedArea(buildReservedArea());
    }
}
