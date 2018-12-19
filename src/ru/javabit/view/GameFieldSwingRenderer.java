package ru.javabit.view;

import javafx.geometry.VerticalDirection;
import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;

import javax.swing.*;
import java.awt.*;

public class GameFieldSwingRenderer implements GameFieldRenderable {

    private FieldCell[][] cellsArr;
    private FieldCell[][] enemiesCellsArr;
    GameField gameField;
    private final String FIELDHEADER = "***^^^^^^^^^(-+o^^o+-)^^^^^^^^^***";
    private final String FIELDFOOTER = "***^^^^^^^^^(-+o^^o+-)^^^^^^^^^***";
    private final String PLAYERGRID =  "***=========YOUR SHIPS=========***";
    private final String ENEMYGRID =   "***========ENEMY SHIPS=========***";
    private JFrame jFrame;
    private JPanel gameFieldPanel;
    JPanel pl1Panel;
    JPanel pl2Panel;
    private boolean renderInit;


    public GameFieldSwingRenderer(GameField gameField) {
        this.gameField = gameField;
        jFrameInit();


    }

    public void initAddCells() {
        System.out.println("initRenderGameField()");
        for (FieldCell[] arr : gameField.getPlayerFieldGrid().getCellsArr()) {
            for(FieldCell cell : arr){
                JButton jButton = new JButton(cell.getSkin());
                pl1Panel.add(jButton);
                if(cell.getFieldCellCoordinate().getY()==0 || cell.getFieldCellCoordinate().getX()==0){jButton.setEnabled(false);}
            }
        }
        for (FieldCell[] arr : gameField.getEnemyFieldGrid().getCellsArr()) {
            for(FieldCell cell : arr){
                JButton jButton = new JButton(cell.getSkin());
                pl2Panel.add(jButton);
                jButton.setEnabled(false);
            }
        }
        renderInit = true;
    }

    public void addCells() {
        if(renderInit) {
            for (FieldCell[] arr : gameField.getPlayerFieldGrid().getCellsArr()) {
                for (FieldCell cell : arr) {
                    JButton jButton = new JButton();
                    pl1Panel.add(jButton);
                }
            }
            for (FieldCell[] arr : gameField.getEnemyFieldGrid().getCellsArr()) {
                for (FieldCell cell : arr) {
                    JButton jButton = new JButton();
                    pl2Panel.add(jButton);
                }
            }
        } else {
            initAddCells();
        }
    }

    public void renderGameField(){
        jFrame.setVisible(true);
    }


    private void jFrameInit(){
        jFrame = new JFrame();
        jFrame.setTitle("BattleShips");
        //jFrame.setLocationRelativeTo(null);
        jFrame.setSize(500, 300);
        //jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new BorderLayout());
        gameFieldPanel = new JPanel();

        pl1Panel = new JPanel();
        pl2Panel = new JPanel();
        pl1Panel.setLayout(new GridLayout(gameField.getColumnNum(), gameField.getRowNum()));
        pl2Panel.setLayout(new GridLayout(gameField.getColumnNum(), gameField.getRowNum()));
        addCells();
        gameFieldPanel.add(pl1Panel);
        gameFieldPanel.add(pl2Panel);

        jFrame.add(gameFieldPanel, BorderLayout.CENTER);
        //gameFieldPanel.add(new JButton("gh"));
        //jFrame.add(new JButton("df"));
        jFrame.add(gameFieldPanel, BorderLayout.CENTER);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}