package ru.javabit.gameField;

enum FieldState {
    FreeWater('.'), ShipPart('П'), WaterAttacked('I'), ShipDamaged('Ж');

    Character skin;

    FieldState(Character skin){
        this.skin = skin;
    }

    public Character getSkin() {
        return skin;
    }

}