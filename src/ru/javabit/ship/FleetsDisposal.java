package ru.javabit.ship;

import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.GameField;

import java.util.ArrayList;

public class FleetsDisposal {

    Fleet fleet1;
    Fleet fleet2;
    FleetDisposer playerAutoDisposer;
    FleetDisposer enemyAutoDisposer;
    FleetDisposer playerManualDisposer;//TODO
    FleetDisposer enemyManualDisposer;//TODO
    GameField gameField;

    public FleetsDisposal(GameField gameField, Fleet fleet1, Fleet fleet2) {
        this.gameField = gameField;
        this.fleet1 = fleet1;
        this.fleet2 = fleet2;
    }

    public void disposeAutoAuto() throws BattleShipsException {

        playerAutoDisposer = new FleetPerelmanDisposer(gameField.getRowNum(), gameField.getColumnNum(), gameField.getPlayerFieldGrid().getCellsArr());
        //playerAutoDisposer = new FleetAutoDisposer(gameField.getRowNum(), gameField.getColumnNum(), gameField.getPlayerFieldGrid().getCellsArr());
        autoDisposeFleet(fleet1.shipList, playerAutoDisposer);
        enemyAutoDisposer = new FleetAutoDisposer(gameField.getRowNum(), gameField.getColumnNum(), gameField.getEnemyFieldGrid().getCellsArr());
        autoDisposeFleet(fleet2.shipList, enemyAutoDisposer);


    }

    private void autoDisposeFleet(ArrayList<Ship> shipList, FleetDisposer disposer) throws BattleShipsException {
        disposer.disposeFleet(shipList);
        disposer.makeAllReservedCellsFreewater();
    }

    private void manualDisposeFleet(ArrayList<Ship> shipList) {//TODO
    }
}
