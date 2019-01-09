package ru.javabit.netgame.client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame {

    Client client;
    JTextArea outTextArea;
    JTextField inTextField;

    public static void main(String[] args) {
        ClientGUI clientGUI = new ClientGUI();
    }

    ClientGUI() {
        clientInit();
        windowConstruct();
    }

    private void clientInit() {
        client = new Client();
        client.meet();
        client.getGameField();

        //client.giveGameField();
        //client.giveString();
    }

    private void windowConstruct() {
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