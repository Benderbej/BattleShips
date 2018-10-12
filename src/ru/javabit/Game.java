package ru.javabit;

import ru.javabit.Report.Report;
import ru.javabit.gameField.GameField;
import ru.javabit.view.GameFieldRenderer;
import ru.javabit.ship.Fleet;
import ru.javabit.ship.FleetAutoDisposal;

public class Game {

    GameField gameField;
    GameFieldRenderer gameFieldRenderer;
    FleetAutoDisposal autoDisposal;
    Fleet fleet;

    Game(){
        System.out.println(Report.Greeting.getText());
        this.gameField = GameField.getInstance(11, 11);
        this.fleet = new Fleet();
        this.autoDisposal = new FleetAutoDisposal(fleet, gameField);
        gameFieldRenderer = new GameFieldRenderer(gameField);
        gameFieldRenderer.renderGameField();
    }


}
