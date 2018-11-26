package ru.javabit;

import ru.javabit.report.ConsoleDialogue;
import ru.javabit.report.Report;
import ru.javabit.gameField.GameField;
import ru.javabit.report.UserDialogue;
import ru.javabit.ship.FleetsDisposal;
import ru.javabit.turn.TurnMaster;
import ru.javabit.view.GameFieldRenderer;
import ru.javabit.ship.Fleet;

public class Game {

    private static Game game;
    UserDialogue dialogue;
    GameField gameField;
    FleetsDisposal fleetsDisposal;
    GameFieldRenderer gameFieldRenderer;
    Fleet fleet1;
    Fleet fleet2;
    TurnMaster turnMaster;
    VictoryTrigger victoryTrigger;

    private Game(){ }

    public static Game getInstance(){
        if(game == null){
            game = new Game();
        }
        return game;
    }

    public void initGame() {
        meetUser();
        gameField = new GameField(11, 11,"computer 1", "computer 2");
        //gameField = new GameField(25, 25,"computer 1", "computer 2");
        fleet1 = new Fleet();
        fleet2 = new Fleet();
        //fleet1 = new Fleet(2,0,1,0);
        //fleet2 = new Fleet(2,0,1,0);
        fleetsDisposal = new FleetsDisposal(gameField, fleet1, fleet2);
        fleetsDisposal.disposeAutoAuto();
        gameFieldRenderer = new GameFieldRenderer(gameField);
        gameFieldRenderer.renderGameField();
    }

    public void startGame() throws InterruptedException {//todo init gameprocess thread
        victoryTrigger = new VictoryTrigger(fleet1.shipList, fleet2.shipList);
        turnMaster = TurnMaster.getInstance();
        turnMaster.initComputerVsComputer(gameField, "computer 1", "computer 2");
        turnMaster.setVictoryTrigger(victoryTrigger);
        turnMaster.setGameFieldRenderer(gameFieldRenderer);
        turnMaster.startTurning();
    }

    private void meetUser() {
        dialogue = new ConsoleDialogue();
        dialogue.makeReport(Report.Greeting.getText());
    }

}