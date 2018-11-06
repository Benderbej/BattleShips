package ru.javabit.ship;

import ru.javabit.gameField.FieldCell;

import java.util.ArrayList;

/**
 * FleetAutoDisposer
 * FleetManualDisposer - TODO
 */

public interface FleetDisposable {

    void disposeFleet(ArrayList<Ship> shipList);

}