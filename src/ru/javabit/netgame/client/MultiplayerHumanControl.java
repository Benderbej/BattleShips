package ru.javabit.netgame.client;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.FieldCellCoordinate;
import ru.javabit.gameField.GameFieldCell;
import ru.javabit.netgame.server.ClientHandler;
import ru.javabit.turn.CellActionListener;
import ru.javabit.turn.HumanControl;
import ru.javabit.view.CellState;

import javax.swing.*;
import java.util.HashMap;

public class MultiplayerHumanControl extends HumanControl {

    private HashMap<Integer, FieldCell> remotePlayersTurns;
    private int clientHandlerId;
    private boolean battleSize;


    public MultiplayerHumanControl(FieldCell[][] fieldCells, HashMap<Integer, FieldCell> remotePlayersTurns, int clientHandlerId, Boolean battleSize) {
        super(fieldCells);
        this.remotePlayersTurns = remotePlayersTurns;
        this.clientHandlerId = clientHandlerId;
        this.battleSize = battleSize;
    }

    protected void chooseCellToAttack() {

        ChooseCellFromClient c = new ChooseCellFromClient();

        Thread thread = new Thread(c);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    //***************************
    public boolean attack() {
        boolean success = false;
        chooseCellToAttack();
        if(attackCell(choosenCell.getFieldCellCoordinate())){ success = true;}
        return success;
    }

    private boolean attackCell(FieldCellCoordinate coordinate) {
        boolean success = false;
        GameFieldCell gameFieldCell = (GameFieldCell) fieldCells[coordinate.getX()][coordinate.getY()];
        switch (gameFieldCell.getState()){
            case FreeWater:
                gameFieldCell.setState(CellState.WaterAttacked);
                success = false;
                break;
            case ShipPart:
                gameFieldCell.setState(CellState.ShipDamaged);
                success = true;
                break;
        }
        enemyFieldCellsList.remove(gameFieldCell);
        return success;
    }
    //***********************************


    private boolean cellIsSet(){
        if (remotePlayersTurns.get(clientHandlerId) != null) {
            return true;
        }
        return false;
    }

    private class ChooseCellFromClient implements Runnable {
        @Override
        public void run() {
            int i=0;
            while(true){// вертим цикл и с периодичностью 20мс проверяем не появилось ли значение в remotePlayersTurns
                if(cellIsSet()){
                    System.out.println("cellIsSet = true");
                    choosenCell = remotePlayersTurns.get(clientHandlerId);
                    remotePlayersTurns.put(clientHandlerId, null);
                    //FieldCellCoordinate fieldCellCoordinate = choosenCell.getFieldCellCoordinate();
                    //System.out.println("cellis is"+choosenCell.getFieldCellCoordinate());
                    return;
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}