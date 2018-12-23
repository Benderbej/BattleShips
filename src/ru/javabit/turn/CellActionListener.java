package ru.javabit.turn;

import ru.javabit.gameField.FieldCell;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CellActionListener implements ActionListener {

    private ActionEvent event;
    private FieldCell cell;

    CellActionListener(FieldCell cell){
        System.out.println("CellActionListener");
        this.cell = cell;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(cell.getFieldCellCoordinate().getX()+" "+cell.getFieldCellCoordinate().getY());
    }
}
