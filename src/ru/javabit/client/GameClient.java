package ru.javabit.client;

import ru.javabit.VictoryTrigger;
import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.GameField;
import ru.javabit.report.ConsoleDialogue;
import ru.javabit.report.Report;
import ru.javabit.ship.Fleet;
import ru.javabit.ship.FleetsDisposal;
import ru.javabit.turn.TurnMaster;
import ru.javabit.view.GameFieldSwingRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

public class GameClient extends JFrame implements Runnable {

    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private JTextArea outTextArea;
    private JTextField inTextField;

    public GameClient(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        super();
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;



        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                try {
                    dataOutputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        inTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dataOutputStream.writeUTF(inTextField.getText());
                    dataOutputStream.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                inTextField.setText("");
            }
        });

        setVisible(true);
        inTextField.requestFocus();
        new Thread(this).start();
    }


    public static void main(String[] args) {
        String site = "localhost";
        String port = "8082";

        Socket socket = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
//        new ChatClient(null, null, null);
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            new GameClient(socket, dataInputStream, dataOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            while (true) { // todo flag
                String line = dataInputStream.readUTF();
                outTextArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            inTextField.setVisible(false);
            validate();
        }
    }

    private void windowConstruct(){
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        outTextArea = new JTextArea();
        add(outTextArea);
        inTextField = new JTextField();
        add(BorderLayout.SOUTH, inTextField);
    }













//    1.подключаемся к серверу
//    2.регистрируемся на сервере
//    3.получаем от сервера расстановку своих кораблей и поле вражеских кораблей
//
//    получаем от сервера запрос на ход
//    ходим
//    передаем серверу ответ


















//    public static void main(String[] args) {
//
//    }
}
