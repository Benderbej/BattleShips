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
        if(attackCell(chooseCellToAttack())){
            System.out.println("ggg");
            success = true;}
        System.out.println("success="+success);
        return success;
    }

    private FieldCellCoordinate chooseCellToAttack(){
        //Thread thread = new Thread(new ChooseCellFromInput());
        //thread.start();
        //fieldCellCoordinate


        FieldCellCoordinate fieldCellCoordinate = null;
        while(true){//TODO THREAD!
            if(cellIsSet){
                fieldCellCoordinate = choosenCell.getFieldCellCoordinate();
                cellIsSet = false;
                return fieldCellCoordinate;
            }
            System.out.println(";;");
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





    private class ListenersInit implements Runnable {
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
            while(true){
                if(cellIsSet){
                    fieldCellCoordinate = choosenCell.getFieldCellCoordinate();
                    cellIsSet = false;
                }
                System.out.println(";;");
            }
        }
    }
}