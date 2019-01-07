package ru.javabit.netgame.server;

import ru.javabit.VictoryTrigger;
import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.GameField;
import ru.javabit.report.UserDialogue;
import ru.javabit.ship.Fleet;
import ru.javabit.ship.FleetsDisposal;
import ru.javabit.turn.TurnMaster;
import ru.javabit.view.GameFieldRenderable;

public class MultiplayerGame implements Game {

    private static Game game;
    UserDialogue dialogue;
    GameField gameField;
    FleetsDisposal fleetsDisposal;
    GameFieldRenderable gameFieldRenderer;
    //GameFieldRenderer gameFieldRenderer;
    //GameFieldSwingRenderer gameFieldSwingRenderer;
    Fleet fleet1;
    Fleet fleet2;
    TurnMaster turnMaster;
    VictoryTrigger victoryTrigger;

    public MultiplayerGame(){ }

    @Override
    public void initGame() throws BattleShipsException {
        System.out.println("1");
        gameField = new GameField(11, 11,"computer 1", "computer 2");
        fleet1 = new Fleet();
        fleet2 = new Fleet();
        fleetsDisposal = new FleetsDisposal(gameField, fleet1, fleet2);
        fleetsDisposal.disposeAutoAuto();
        System.out.println("2");

        //gameFieldRenderer = new GameFieldSwingRenderer(gameField);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //gameFieldRenderer.renderGameField();

    }

    @Override
    public void startGame() throws InterruptedException {
        System.out.println("3");
        victoryTrigger = new VictoryTrigger(fleet1, fleet2);
        turnMaster = new MultiplayerTurnMaster();
        turnMaster.initComputerVsComputer(gameField, "human", "computer");
        turnMaster.setVictoryTrigger(victoryTrigger);
        new Thread(turnMaster).start();
        System.out.println("4");
    }

    @Override
    public GameField getGameField() {
        return null;
    }

//    @Override
//    public Fleet getFleet1() {
//        return fleet1;
//    }
//
//    @Override
//    public Fleet getFleet2() {
//        return fleet2;
//    }

}
