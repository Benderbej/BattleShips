package ru.javabit.netgame.client;

import ru.javabit.gameField.FieldCell;
import ru.javabit.turn.HumanControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientCellActionListener implements ActionListener {
    FieldCell cell;
    ClientGUI clientGUI;

    ClientCellActionListener(ClientGUI clientGUI, FieldCell cell) {
        this.clientGUI = clientGUI;
        this.cell = cell;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        clientGUI.choosenCell = cell;
        //super.actionPerformed(e);
    }
}
