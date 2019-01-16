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
import java.util.HashMap;

public class MultiplayerGame implements Game {

    GameField gameField;
    FleetsDisposal fleetsDisposal;
    Fleet fleet1;
    Fleet fleet2;
    private MultiplayerTurnMaster turnMaster;
    VictoryTrigger victoryTrigger;
    ArrayList<Integer> clientsIdsList;
    private HashMap<Integer, Boolean> playerBattleSide;//clientIds->true if active

    public MultiplayerGame(ArrayList<Integer> clientsIdsList){
        this.clientsIdsList = clientsIdsList;
    }

    @Override
    public void initGame() throws BattleShipsException {
        System.out.println("init Multiplayer Game!");
        gameField = new GameField(5, 5,"computer 1", "computer 2");
        fleet1 = new Fleet(0,0,1,0);
        fleet2 = new Fleet(0,0,1,0);
        fleetsDisposal = new FleetsDisposal(gameField, fleet1, fleet2);
        fleetsDisposal.disposeAutoAuto();
        setPlayerActiveness();
    }

    @Override
    public void startGame() throws InterruptedException {
        victoryTrigger = new VictoryTrigger(fleet1, fleet2, clientsIdsList);
        turnMaster = new MultiplayerTurnMaster(clientsIdsList, playerBattleSide);
        //turnMaster.initComputerVsComputer(gameField, "human", "computer");
        turnMaster.initHumanVsHuman(gameField, "human", "computer");
        turnMaster.setVictoryTrigger(victoryTrigger);
        new Thread(turnMaster).start();
    }

    private void setPlayerActiveness(){
        playerBattleSide = new HashMap<Integer, Boolean>();
        playerBattleSide.put(clientsIdsList.get(0), true);
        playerBattleSide.put(clientsIdsList.get(1), false);
    }

    public Boolean getBattleSide(int clientHandlerId){
        return playerBattleSide.get(clientHandlerId);
    }

    @Override
    public GameField getGameField() {
        return gameField;
    }

    public MultiplayerTurnMaster getTurnMaster() {
        return turnMaster;
    }
}