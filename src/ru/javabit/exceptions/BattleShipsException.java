package ru.javabit.exceptions;

public class BattleShipsException extends Exception {
    public BattleShipsException() {
        super("Something in BattleShips was wrong");
    }

    public BattleShipsException(String s) {
        super(s);
    }
}