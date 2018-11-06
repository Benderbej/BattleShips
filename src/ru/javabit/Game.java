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
    UserDialogue dialogue;
    GameField gameField;
    GameFieldRenderer gameFieldRenderer;
    FleetAutoDisposer autoDisposer;
    Fleet fleet;

    Game(){
        meetUser();
        gameField = new GameField(11, 11);
        fleet = new Fleet();
        autoDisposer = new FleetAutoDisposer(fleet, gameField);
        gameFieldRenderer = new GameFieldRenderer(gameField);
        gameFieldRenderer.renderGameField();
    }

    public static Game getInstance(){
        if(game == null){
            game = new Game();
        }
        return game;
    }

    private void meetUser(){
        dialogue = new ConsoleDialogue();//???
        dialogue.makeReport(Report.Greeting.getText());//???
    }

}
