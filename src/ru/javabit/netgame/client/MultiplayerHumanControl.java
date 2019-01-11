package ru.javabit.netgame.client;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.FieldCellCoordinate;
import ru.javabit.turn.HumanControl;

public class MultiplayerHumanControl extends HumanControl {

    public FieldCell choosenCell;
    public boolean cellIsSet;

    MultiplayerHumanControl(FieldCell[][] fieldCells) {
        super(fieldCells);
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

    private class ChooseCellFromClient implements Runnable {
        @Override
        public void run() {
            int i=0;
            while(true){
                if(cellIsSet){
                    //FieldCellCoordinate fieldCellCoordinate = choosenCell.getFieldCellCoordinate();
                    //System.out.println("cellis is"+choosenCell.getFieldCellCoordinate());
                    cellIsSet = false;
                    return;
                }
                i++;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
