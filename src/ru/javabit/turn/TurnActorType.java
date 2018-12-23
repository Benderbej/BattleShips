package ru.javabit.turn;

public enum TurnActorType {

    HUMAN("Ходит игрок"), COMPUTER("Ходит компьютер");

    String turnName;

    TurnActorType(String turnName){
        this.turnName = turnName;
    }

    public String getTurnName() {
        return turnName;
    }
}
