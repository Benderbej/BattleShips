package ru.javabit.netgame.client;

import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame {

    Client client;
    JTextArea outTextArea;
    JTextField inTextField;

    public static void main(String[] args) {
        ClientGUI clientGUI = new ClientGUI();
    }

    ClientGUI() {
        client = new Client();
        windowConstruct();
    }


    private void windowConstruct(){
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        outTextArea = new JTextArea();
        add(outTextArea);
        inTextField = new JTextField();
        add(BorderLayout.SOUTH, inTextField);
        setVisible(true);
    }




}
