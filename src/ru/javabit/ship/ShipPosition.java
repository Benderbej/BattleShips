package ru.javabit.ship;

public enum ShipPosition {

    Horizontal(0), Vertical(1), Unar(2);

    int id;

    ShipPosition(int id){
        this.id = id;
    }

}
