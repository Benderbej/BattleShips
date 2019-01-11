package ru.javabit.netgame.server;

import ru.javabit.Game;
import ru.javabit.VictoryTrigger;
import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.GameField;
import ru.javabit.report.UserDialogue;
import ru.javabit.ship.Fleet;
import ru.javabit.ship.FleetsDisposal;
import ru.javabit.turn.MultiplayerTurnMaster;
import ru.javabit.turn.TurnMaster;
import ru.javabit.view.GameFieldRenderable;

import java.util.ArrayList;

public class MultiplayerGame implements Game {

    GameField gameField;
    FleetsDisposal fleetsDisposal;
    Fleet fleet1;
    Fleet fleet2;

    private MultiplayerTurnMaster turnMaster;
    VictoryTrigger victoryTrigger;
    ArrayList<Integer> clientsIdsList;

    public MultiplayerGame(ArrayList<Integer> clientsIdsList){
        this.clientsIdsList = clientsIdsList;
    }

    @Override
    public void initGame() throws BattleShipsException {

        System.out.println("init Multiplayer Game!");
        gameField = new GameField(11, 11,"computer 1", "computer 2");
        fleet1 = new Fleet();
        fleet2 = new Fleet();
        fleetsDisposal = new FleetsDisposal(gameField, fleet1, fleet2);
        fleetsDisposal.disposeAutoAuto();
    }

    @Override
    public void startGame() throws InterruptedException {
        victoryTrigger = new VictoryTrigger(fleet1, fleet2);
        turnMaster = new MultiplayerTurnMaster(clientsIdsList);
        //turnMaster.initComputerVsComputer(gameField, "human", "computer");
        turnMaster.initHumanVsHuman(gameField, "human", "computer");
        turnMaster.setVictoryTrigger(victoryTrigger);
        new Thread(turnMaster).start();
    }

    @Override
    public GameField getGameField() {
        return gameField;
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

    public MultiplayerTurnMaster getTurnMaster() {
        return turnMaster;
    }
}