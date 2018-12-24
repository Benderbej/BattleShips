package ru.javabit.turn;

import ru.javabit.GameMath;
import ru.javabit.gameField.*;
import ru.javabit.view.CellState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class HumanControl implements TurnControlled {
    private ArrayList<GameFieldCell> enemyFieldCellsList;
    private FieldCell[][] fieldCells;//enemy's gamefield
    private GameField gameField;
    private JPanel panel;
    private Thread listen;


    public static FieldCell choosenCell;
    public static boolean cellIsSet;


    HumanControl(FieldCell[][] fieldCells) {
        this.fieldCells = fieldCells;
        fillFieldCellsList();
    }

    HumanControl(FieldCell[][] fieldCells, JPanel panel) {
        this.fieldCells = fieldCells;
        this.panel = panel;
        listen = new Thread(new ListenersInit());
        listen.start();

        fillFieldCellsList();
    }

    private void fillFieldCellsList() {
        enemyFieldCellsList = new ArrayList<GameFieldCell>();
        for (FieldCell[] arr : fieldCells) {
            for(FieldCell cell : arr){
                if(cell instanceof GameFieldCell){
                    enemyFieldCellsList.add((GameFieldCell) cell);
                }
            }
        }
        System.out.println(enemyFieldCellsList.size());
    }

    public boolean attack() {
        boolean success = false;
        chooseCellToAttack();
        if(attackCell(choosenCell.getFieldCellCoordinate())){ success = true;}
        return success;
    }

    private void chooseCellToAttack(){
        ChooseCellFromInput c = new ChooseCellFromInput();
        Thread thread = new Thread(c);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    private void moveFromFieldCellsList(int x, int y){
        //enemyFieldCellsList.get()
    }





    private class ListenersInit implements Runnable {//listeners are in different thread
        @Override
        public void run() {
            int i=0; int j=0;
            for (FieldCell[] arr : fieldCells) {
                for (FieldCell cell : arr) {
                    JButton jButton = (JButton) panel.getComponent((i*(fieldCells.length)+j));

                    jButton.addActionListener(new CellActionListener(cell));
                    j++;
                }
                j=0;
                i++;
            }
        }
    }

    private class ChooseCellFromInput implements Runnable {

        FieldCellCoordinate fieldCellCoordinate;

        @Override
        public void run() {
            int i=0;
            while(true){
                if(cellIsSet){
                    fieldCellCoordinate = choosenCell.getFieldCellCoordinate();
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