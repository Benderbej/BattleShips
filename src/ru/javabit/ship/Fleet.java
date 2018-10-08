package ru.javabit.ship;

import java.util.ArrayList;

public class Fleet {

    ArrayList<Ship> shipList;

    public Fleet(){
        shipList = new ArrayList<Ship>();
        shipList.add(new BattleCruiser());
        shipList.add(new Cruiser());
        shipList.add(new Cruiser());
        shipList.add(new Destroyer());
        shipList.add(new Destroyer());
        shipList.add(new Destroyer());
        shipList.add(new Boat());
        shipList.add(new Boat());
        shipList.add(new Boat());
        shipList.add(new Boat());
    }

}
