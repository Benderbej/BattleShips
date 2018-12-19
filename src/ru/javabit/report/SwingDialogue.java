package ru.javabit.report;

import ru.javabit.view.GameFieldRenderable;

import javax.swing.*;

public class SwingDialogue implements UserDialogue  {

    JFrame frame;

    public SwingDialogue(JFrame frame ){
        this.frame = frame;
    }

    public void makeReport(String s) {
         JOptionPane.showMessageDialog(frame, s);
    }
}