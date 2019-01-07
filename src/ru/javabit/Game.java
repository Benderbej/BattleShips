package ru.javabit;

import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.GameField;

public interface Game {

    public abstract void initGame() throws BattleShipsException;
    public abstract void startGame() throws InterruptedException;
    public GameField getGameField();
    //public abstract Fleet getFleet1();
    //public abstract Fleet getFleet2();
}