package ru.javabit.turn;

import ru.javabit.GameMath;
import ru.javabit.gameField.*;
import ru.javabit.view.CellState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

/*

класс связывающий механику ходов на сервере с информацией от клиентов, - ждет поступлений новой информации о ходах игроков
преобразование статусов элементов массива fieldCells происходит тут

attack - значит преобразовывать статус выбранной FielCell в fieldCells[][]
 */

public class HumanControl implements TurnControlled {
    protected ArrayList<GameFieldCell> enemyFieldCellsList;
    protected FieldCell[][] fieldCells;//enemy's gamefield
    private GameField gameField;
    private JPanel panel;
    private Thread listen;

    public FieldCell choosenCell;
    public boolean cellIsSet;

    public HumanControl(FieldCell[][] fieldCells) {
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

    protected void fillFieldCellsList() {
        enemyFieldCellsList = new ArrayList<GameFieldCell>();
        for (FieldCell[] arr : fieldCells) {
            for(FieldCell cell : arr) {
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

    protected void chooseCellToAttack() {
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

                    jButton.addActionListener(new CellActionListener(HumanControl.this, cell));
                    j++;
                }
                j=0;
                i++;
            }
        }
    }

    private class ChooseCellFromInput implements Runnable {

        @Override
        public void run() {
            int i=0;
            while(true){
                if(cellIsSet){
                    //FieldCellCoordinate fieldCellCoordinate = choosenCell.getFieldCellCoordinate();
                    //System.out.println("cellis is"+choosenCell.getFieldCellCoordinate());
                    cellIsSet = false;
                    return;//выходим из ожидания когда cellIsSet оказывается true, то есть когда слушатель кнопки нам это сделает
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