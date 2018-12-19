package ru.javabit.view;

import javafx.geometry.VerticalDirection;
import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class GameFieldSwingRenderer implements GameFieldRenderable {

    private FieldCell[][] cellsArr;
    private FieldCell[][] enemiesCellsArr;
    GameField gameField;
    private final String FIELDHEADER = "***^^^^^^^^^(-+o^^o+-)^^^^^^^^^***";
    private final String FIELDFOOTER = "***^^^^^^^^^(-+o^^o+-)^^^^^^^^^***";
    private final String PLAYERGRID =  "***=========YOUR SHIPS=========***";
    private final String ENEMYGRID =   "***========ENEMY SHIPS=========***";
    private JTextField playerGridName;
    private JTextField enemyGridName;
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

    public void updateCellsData() {
        int i=0; int j=0;
        System.out.println(pl1Panel.getComponentCount());
            for (FieldCell[] arr : gameField.getPlayerFieldGrid().getCellsArr()) {
                for (FieldCell cell : arr) {
                    System.out.printf("i=" + i + " j=" + j + ", ");
                    JButton jButton = (JButton) pl1Panel.getComponent((i*(gameField.getRowNum())+j));
                    jButton.setText(cell.getSkin());
                    j++;
                }
                j=0;
                i++;
            }
        i=0; j=0;
            for (FieldCell[] arr : gameField.getEnemyFieldGrid().getCellsArr()) {
                for (FieldCell cell : arr) {
                    JButton jButton = (JButton) pl2Panel.getComponent((i*(gameField.getRowNum())+j));
                    jButton.setText(cell.getSkin());
                    j++;
                }
                j=0;
                i++;
            }
    }

    public void renderGameField(){
        if(renderInit == true){
            updateCellsData();
        }
        jFrame.setVisible(true);
    }


    private void jFrameInit(){
        playerGridName = new JTextField(PLAYERGRID);
        enemyGridName = new JTextField(ENEMYGRID);
        playerGridName.setEditable(false);
        enemyGridName.setEditable(false);

        jFrame = new JFrame();
        jFrame.setTitle("BattleShips");
        //jFrame.setLocationRelativeTo(null);
        jFrame.setSize(gameField.getColumnNum()*70, gameField.getRowNum()*70);
        //jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new BorderLayout());
        gameFieldPanel = new JPanel();
        pl1Panel = new JPanel();
        pl2Panel = new JPanel();
        pl1Panel.setLayout(new GridLayout(gameField.getColumnNum(), gameField.getRowNum()));
        pl2Panel.setLayout(new GridLayout(gameField.getColumnNum(), gameField.getRowNum()));
        initAddCells();

        gameFieldPanel.add(playerGridName);
        gameFieldPanel.add(pl1Panel);
        gameFieldPanel.add(enemyGridName);
        gameFieldPanel.add(pl2Panel);


        jFrame.add(playerGridName);
        jFrame.add(enemyGridName);


        jFrame.add(gameFieldPanel, BorderLayout.CENTER);


        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}