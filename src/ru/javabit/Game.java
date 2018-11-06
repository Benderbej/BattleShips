package ru.javabit;

import ru.javabit.report.ConsoleDialogue;
import ru.javabit.report.Report;
import ru.javabit.gameField.GameField;
import ru.javabit.report.UserDialogue;
import ru.javabit.view.GameFieldRenderer;
import ru.javabit.ship.Fleet;
import ru.javabit.ship.FleetAutoDisposer;

public class Game {

    private static Game game;
    GameField gameField;
    GameFieldRenderer gameFieldRenderer;
    FleetAutoDisposer autoDisposal;
    Fleet fleet;

    Game(){
        UserDialogue dialogue = new ConsoleDialogue();//???
        dialogue.makeReport(Report.Greeting.getText());//???
        this.gameField = new GameField(11, 11);
        this.fleet = new Fleet();
        this.autoDisposal = new FleetAutoDisposer(fleet, gameField);
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
