package ru.javabit.ship;

/**
 * unar for 1-cell ships
 */
public enum ShipPosition {

    Horizontal(0), Vertical(1), Unar(2);

    int id;

    ShipPosition(int id){
        this.id = id;
    }

}
