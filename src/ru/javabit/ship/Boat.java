package ru.javabit.ship;

import ru.javabit.gameField.GameFieldCell;

public class Boat extends Ship {

    Boat(){
        super(1, "Boat");
        this.shipPosition = ShipPosition.Unar;
    }

}
