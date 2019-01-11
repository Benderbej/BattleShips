package ru.javabit.turn;

import ru.javabit.gameField.FieldCell;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CellActionListener implements ActionListener {

    private ActionEvent event;
    private FieldCell cell;
    private HumanControl humanControl;


    CellActionListener(HumanControl humanControl, FieldCell cell){
        this.humanControl = humanControl;
        this.cell = cell;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("shoot!");
        System.out.println(cell.getFieldCellCoordinate().getX()+" "+cell.getFieldCellCoordinate().getY());
        humanControl.cellIsSet = true;
        humanControl.choosenCell = cell;
    }

}
