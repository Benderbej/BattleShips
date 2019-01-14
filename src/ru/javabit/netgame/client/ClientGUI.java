package ru.javabit.netgame.client;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameFieldCell;
import ru.javabit.turn.CellActionListener;
import ru.javabit.turn.HumanControl;
import ru.javabit.view.GameFieldRenderable;
import ru.javabit.view.GameFieldSwingRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ClientGUI extends JFrame {

    Client client;
    JTextArea outTextArea;
    JTextField inTextField;
    GameFieldSwingRenderer gameFieldRenderer;
    FieldCell choosenCell;

    private Thread listen;

    public static void main(String[] args) {
        ClientGUI clientGUI = new ClientGUI();
    }

    ClientGUI() {
        clientInit();

        clientStart();


    }

    private void clientInit() {
        client = new Client();
        client.meet();
        client.recieveGameField();
        client.recieveBattleSide();//active-passive atacker defender - needs to render correctly where your field or enemy

        System.out.println(" client.getGameField();");
        gameFieldRenderer = new GameFieldSwingRenderer(client.getGameField(), client.battleSide);
        gameFieldRenderer.renderGameField();
        ////listener

        //client.giveString();





    }

    private void clientStart(){

        listen = new Thread(new ListenersInit());
        listen.start();

        Thread p = new Thread(new GameProcess());
        p.start();

    }






    private class GameProcess implements Runnable {
        @Override
        public void run() {
            int i=0;
            while(true){// вертим цикл и с периодичностью 20мс проверяем не появилось ли значение в remotePlayersTurns


                System.out.println("BattleSide is "+client.battleSide);


                client.getCurrentTurnActorId();//отправляем запрос не мы ли текущий пользователь, то есть не наш ли ход

                if(client.currentTurnActorId == client.clientHandlerId){//если ход наш то скидываем FieldCell который мы выбрали, если не выбрали еще то ждем пока listener JFrame услышит клавишу
                    System.out.println("PLAYER TURN: currentTurnActorId = clientHandlerId");
                    if(choosenCell != null) {
                        System.out.println("CELL IS CHOOSEN!");
                        if(client.takeFieldCell(choosenCell)){
                            choosenCell = null;//если удалось успешно закинуть выбранную клетку на сервак, обнуляем ссылку
                            System.out.println();
                        }
                    }
                }

                System.out.println("clientGUI recieveGameField");
                client.recieveGameField();//запрашиваем обновленный gameField
                gameFieldRenderer.setGameField(client.getGameField());//отдаем его рендереру
                gameFieldRenderer.renderGameField();//обновляем поле
                    //TODO

                    //remotePlayersTurns.put(clientHandlerId, choosenCell);//на серваке в обработке takeFieldCell



                    //if(client.getGameOver()) {//спецзапрос к серваку на предмет конца игры - ответ 0 1 2 victory trigger
                    //    return;//если 1 2 конец игры выходим из цикла
                    //}



                try {
                    Thread.sleep(100);//чтобы сервер не затрахать до смерти
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }






    private class ListenersInit implements Runnable {//listeners are in different thread

        @Override
        public void run() {
            JPanel jPanel = gameFieldRenderer.getPl2Panel();
            int i=0; int j=0;
            for (FieldCell[] arr : client.getEnemyCellsArr()) {
                for (FieldCell cell : arr) {
                    JButton jButton = (JButton) jPanel.getComponent((i*(client.getEnemyCellsArr().length)+j));
                    jButton.addActionListener(new ClientCellActionListener(ClientGUI.this, cell));
                    j++;
                }
                j=0;
                i++;
            }
        }

    }








/*
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
*/




}



/*




    private ArrayList<GameFieldCell> enemyFieldCellsList;
    private FieldCell[][] fieldCells;//enemy's gamefield
    private GameField gameField;
    private JPanel panel;
    private Thread listen;

    public static FieldCell choosenCell;
    public static boolean cellIsSet;


    HumanControl(FieldCell[][] fieldCells, JPanel panel) {
        this.fieldCells = fieldCells;
        this.panel = panel;
        listen = new Thread(new ListenersInit());
        listen.start();
        fillFieldCellsList();
    }

    private void fillFieldCellsList() {
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

    private void chooseCellToAttack() {
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

                    jButton.addActionListener(new CellActionListener(cell));
                    j++;
                }
                j=0;
                i++;
            }
        }
    }

    private class ChooseCellFromInput implements Runnable {

        FieldCellCoordinate fieldCellCoordinate;

        @Override
        public void run() {
            int i=0;
            while(true){
                if(cellIsSet){
                    fieldCellCoordinate = choosenCell.getFieldCellCoordinate();
                    //System.out.println("cellis is"+choosenCell.getFieldCellCoordinate());
                    cellIsSet = false;
                    return;
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


















 */


