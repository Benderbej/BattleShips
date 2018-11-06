package ru.javabit.ship;

public class Boat extends Ship {

    Boat(){
        super(1, "Boat");
        this.shipPosition = ShipPosition.Unar;
    }

    @Override
    public void placeShip() {
        placeStartShipCell();
        FleetAutoDisposer.maskReservedArea(buildReservedArea());
    }
}
