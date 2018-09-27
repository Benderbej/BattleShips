package ru.javabit.ship;

public abstract class Ship {

    int size;
    String name;

    protected Ship(int size, String name){
        this.size = size;
    }

    abstract void placeToGameField();

}