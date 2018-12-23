package ru.javabit.turn;

import ru.javabit.GameMath;
import ru.javabit.gameField.*;
import ru.javabit.view.CellState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HumanControl implements TurnControlled {
    private ArrayList<GameFieldCell> enemyFieldCellsList;
    private FieldCell[][] fieldCells;//enemy's gamefield
    private GameField gameField;
    private JPanel panel;

    HumanControl(FieldCell[][] fieldCells) {
        this.fieldCells = fieldCells;
        fillFieldCellsList();
    }

    HumanControl(FieldCell[][] fieldCells, JPanel panel) {
        this.fieldCells = fieldCells;
        this.panel = panel;
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
        if(attackCell(chooseCellToAttack())){success = true;}
        return success;
    }

    private FieldCellCoordinate chooseCellToAttack() {
        GameFieldCell gameFieldCell = enemyFieldCellsList.get(GameMath.getRandomInt(enemyFieldCellsList.size()));
        FieldCellCoordinate fieldCellCoordinate = gameFieldCell.getFieldCellCoordinate();
        return fieldCellCoordinate;
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

    private class ChooseCellToAttackByHumanPlayer implements Runnable{

        @Override
        public void run() {
                int i=0; int j=0;
                for (FieldCell[] arr : fieldCells) {
                    for (FieldCell cell : arr) {
                        JButton jButton = (JButton) panel.getComponent((i*(gameField.getRowNum())+j));
                        jButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                            }
                        });
                        j++;
                    }
                    j=0;
                    i++;
                }




        }
    }

}
