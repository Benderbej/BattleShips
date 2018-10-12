package ru.javabit.view;

public enum CellState {

    FreeWater("^"), ShipPart("O"), WaterAttacked("v"), ShipDamaged("&"), Reserved("#");

    private String skin;

    CellState(String skin){
        this.skin = skin;
    }

    public String getSkin() {
        return skin;
    }



    //public Character getEventName(int id) { return CellState.values[id].getName(); }

}