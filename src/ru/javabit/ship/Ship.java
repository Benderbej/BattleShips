package ru.javabit.ship;

import ru.javabit.gameField.FieldCell;

import java.util.ArrayList;

public abstract class Ship {

    int size;
    String name;
    ArrayList<FieldCell> cells;
    ShipPosition shipPosition;

    protected Ship(int size, String name){
        this.size = size;
        this.cells = new ArrayList<>(size);
    }

    abstract void placeToGameField();

}