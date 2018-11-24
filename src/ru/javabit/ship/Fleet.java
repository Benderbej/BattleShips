package ru.javabit.ship;

import java.util.ArrayList;

public class Fleet {

    public ArrayList<Ship> shipList;

    public Fleet() {
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

    public Fleet(int boats, int destroyers, int cruisers, int battlecruisers){
        shipList = new ArrayList<Ship>();
        for (int i = 0; i < boats; i++){
            shipList.add(new Boat());
        }
        for(int i=0; i < destroyers; i++){
            shipList.add(new Destroyer());
        }
        for(int i=0; i < cruisers; i++){
            shipList.add(new Cruiser());
        }
        for(int i=0; i < battlecruisers; i++){
            shipList.add(new BattleCruiser());
        }
    }

}
