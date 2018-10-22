package ru.javabit;

import ru.javabit.report.ConsoleDialogue;
import ru.javabit.report.Report;
import ru.javabit.gameField.GameField;
import ru.javabit.view.GameFieldRenderer;
import ru.javabit.ship.Fleet;
import ru.javabit.ship.FleetAutoDisposal;

public class Game {

    private static Game game;
    GameField gameField;
    GameFieldRenderer gameFieldRenderer;
    FleetAutoDisposal autoDisposal;
    Fleet fleet;

    Game(){
        ConsoleDialogue.makeReportToConsole(Report.Greeting.getText());
        this.gameField = new GameField(11, 11);
        this.fleet = new Fleet();
        this.autoDisposal = new FleetAutoDisposal(fleet, gameField);
        gameFieldRenderer = new GameFieldRenderer(gameField);
        gameFieldRenderer.renderGameField();
    }

    public static Game getInstance(){
        if(game == null){
            game = new Game();
        }
        return game;
    }

}
