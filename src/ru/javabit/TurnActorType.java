package ru.javabit;

public enum TurnActorType {

    USER("Ходит игрок"), COMPUTER("Ходит компьютер");

    String turnName;

    TurnActorType(String turnName){
        this.turnName = turnName;
    }

    public String getTurnName() {
        return turnName;
    }
}
