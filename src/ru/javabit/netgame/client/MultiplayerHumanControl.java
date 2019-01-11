package ru.javabit.netgame.client;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.FieldCellCoordinate;
import ru.javabit.netgame.server.ClientHandler;
import ru.javabit.turn.CellActionListener;
import ru.javabit.turn.HumanControl;

import javax.swing.*;
import java.util.HashMap;

public class MultiplayerHumanControl extends HumanControl {

    private HashMap<Integer, FieldCell> remotePlayersTurns;
    private int clientHandlerId;
    private boolean activeness;


    public MultiplayerHumanControl(FieldCell[][] fieldCells, HashMap<Integer, FieldCell> remotePlayersTurns, int clientHandlerId, Boolean activeness) {
        super(fieldCells);
        this.remotePlayersTurns = remotePlayersTurns;
        this.clientHandlerId = clientHandlerId;
        this.activeness = activeness;
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

    /*
    public boolean attack() {
        boolean success = false;
        chooseCellToAttack();
        if(attackCell(choosenCell.getFieldCellCoordinate())){ success = true;}
        return success;
    }
    */

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