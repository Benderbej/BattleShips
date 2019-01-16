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
    //JTextArea outTextArea;
    //JTextField inTextField;
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
    }

    private void clientStart() {
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
                //System.out.println("BattleSide is "+client.battleSide);
                updateStatus();
                ifOurTurnMakeIt();
                updateGameField();
                if(isGameOver()){return;}
                try {
                    Thread.sleep(10);//чтобы сервер не затрахать до смерти
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

    private Boolean isGameOver() {
        client.getWinnerId();
        if(client.winnerId!=0){
            if(client.winnerId == client.clientHandlerId){
                gameFieldRenderer.setGameStatus("поздравляем! вы победили!");
            } else {
                gameFieldRenderer.setGameStatus("победила дружба!");
            }
            System.out.println("конец игры!!");
            return true;
        }
        return false;
    }

    private void updateStatus() {
        client.getCurrentTurnActorId();//отправляем запрос не мы ли текущий пользователь, то есть не наш ли ход
        if(client.currentTurnActorId == client.clientHandlerId){
            gameFieldRenderer.setGameStatus("ваш ход, выберите клетку для атаки");
        } else {
            gameFieldRenderer.setGameStatus("ход противника...");
        }
    }

    private void ifOurTurnMakeIt() {
        if(client.currentTurnActorId == client.clientHandlerId){//если ход наш то скидываем FieldCell который мы выбрали, если не выбрали еще то ждем пока listener JFrame услышит клавишу
            if(choosenCell != null) {
                //System.out.println("CELL IS CHOOSEN!");
                if(client.takeFieldCell(choosenCell)){
                    choosenCell = null;//если удалось успешно закинуть выбранную клетку на сервак, обнуляем ссылку
                    System.out.println();
                }
            }
        }
    }

    private void updateGameField() {
        System.out.println("clientGUI recieveGameField");
        client.recieveGameField();//запрашиваем обновленный gameField
        gameFieldRenderer.setGameField(client.getGameField());//отдаем его рендереру
        gameFieldRenderer.renderGameField();//обновляем поле
    }
}