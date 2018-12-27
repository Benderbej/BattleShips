package ru.javabit.client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class GameClient extends JFrame implements Runnable {

    private String site;
    private String port;

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
//    private JTextArea outTextArea;
//    private JTextField inTextField;

    private int clientServantId;

    public GameClient() {
        super();
        site = "localhost";
        port = "8082";
        try {
            socket = new Socket(site, Integer.parseInt(port));
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));


        Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    meet();
                    getClientServantId();
                    closeConnections();
                }
            });
        t.start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            validate();
        }

//      addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosed(WindowEvent e) {
//                super.windowClosed(e);
//                try {
//                    dataOutputStream.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//                try {
//                    socket.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });
//
//        inTextField.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    dataOutputStream.writeUTF(inTextField.getText());
//                    dataOutputStream.flush();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//                inTextField.setText("");
//            }
//        });
//
//        setVisible(true);
//        inTextField.requestFocus();
//        new Thread(this).start();
    }

    private void closeConnections() {
        try {
            dataInputStream.close();
            dataOutputStream.close();
            //socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getClientServantId() {
        System.out.println("getClientServantId();");
        clientServantId = getServerIntResponse();
    }

    private void meet() {//first data
        System.out.println("meet()");
        sendCode(0);
    }

    private void sendTurn() {//data o turn
        sendCode(1);
    }

    private void sendCode(int code){
        System.out.println("sendCode()");
        try {
            dataOutputStream.writeInt(code);
            dataOutputStream.flush();
            //dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int getServerIntResponse(){
        System.out.println("getServerIntResponse()");
        int response = -1;
        try {
            if(dataInputStream==null){System.out.println("dataInputStream=null");}
            response = dataInputStream.readInt();
            System.out.println(response);
            clientServantId = response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void main(String[] args) {
        GameClient client = new GameClient();
        client.startGame();




//        try {
//            socket = new Socket(site, Integer.parseInt(port));
//            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
//            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
//            new GameClient(socket, dataInputStream, dataOutputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//            try {
//                if (dataOutputStream != null) {
//                    dataOutputStream.close();
//                }
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            try {
//                if (socket != null) {
//                    socket.close();
//                }
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
    }

    private void startGame() {
        while (true){

        }
    }


    private void firstConnect(){
//        clientServantId
    }





    @Override
    public void run() {
//        try {
//            while (true) { // todo flag
//                String line = dataInputStream.readUTF();
//                outTextArea.append(line + "\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            inTextField.setVisible(false);
//            validate();
//        }
    }

    private void windowConstruct(){
//        setSize(400, 500);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//        outTextArea = new JTextArea();
//        add(outTextArea);
//        inTextField = new JTextField();
//        add(BorderLayout.SOUTH, inTextField);
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
