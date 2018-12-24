package ru.javabit;

import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.GameField;
import ru.javabit.report.ConsoleDialogue;
import ru.javabit.report.Report;
import ru.javabit.ship.Fleet;
import ru.javabit.ship.FleetsDisposal;
import ru.javabit.turn.TurnMaster;
import ru.javabit.view.GameFieldSwingRenderer;

public interface Game {

    public abstract void initGame() throws BattleShipsException;
    public abstract void startGame() throws InterruptedException;
    public abstract Fleet getFleet1();
    public abstract Fleet getFleet2();
}
