package ru.javabit.ship;

import ru.javabit.gameField.FieldCell;

import java.util.ArrayList;

public interface FleetDisposable {

    void disposeFleet(ArrayList<FieldCell> shipCells);

}
