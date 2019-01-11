package ru.javabit.view;

import javafx.geometry.VerticalDirection;
import ru.javabit.gameField.*;

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
    public JTextField gameStatus;
    private JFrame jFrame;
    private JPanel gameFieldPanel;

    private JPanel pl1Panel;
    private JPanel pl2Panel;
    private boolean renderInit;

    private Boolean isActivePlayer = true;//always true in singleplayer, false in multiplayer, means - second player, defender(true-first player,attacker)

    public GameFieldSwingRenderer(GameField gameField) {
        this.gameField = gameField;
        jFrameInit();
    }

    public GameFieldSwingRenderer(GameField gameField, Boolean isActivePlayer) {
        this.gameField = gameField;
        this.isActivePlayer = isActivePlayer;
        jFrameInit();
    }

    private GameFieldGrid getPlayerFieldGrid(){
        if(isActivePlayer) {
            return gameField.getPlayerFieldGrid();
        } else {
            return gameField.getEnemyFieldGrid();
        }
    }

    private GameFieldGrid getEnemyFieldGrid(){
        if(isActivePlayer) {
            return gameField.getEnemyFieldGrid();
        } else {
            return gameField.getPlayerFieldGrid();
        }
    }


    public void initAddCells() {
        System.out.println("initRenderGameField()");
        for (FieldCell[] arr : getPlayerFieldGrid().getCellsArr()) {
            for(FieldCell cell : arr){
                JButton jButton = new JButton(cell.getSkin());
                pl1Panel.add(jButton);
                jButton.setEnabled(false);
            }
        }
        for (FieldCell[] arr : getEnemyFieldGrid().getCellsArr()) {
            for(FieldCell cell : arr){
                JButton jButton = new JButton(cell.getSkin());
                pl2Panel.add(jButton);
                if(cell.getFieldCellCoordinate().getY()==0 || cell.getFieldCellCoordinate().getX()==0){jButton.setEnabled(false);}
            }
        }
        renderInit = true;
    }

    public void updateCellsData() {
        renderGrid(getPlayerFieldGrid(), pl1Panel);
        renderHiddenGrid(getEnemyFieldGrid(), pl2Panel);
    }


    public void showEnemyPositions(){
        int i=0; int j=0;
        renderGrid(getEnemyFieldGrid(), pl2Panel);
    }

    private void renderGrid(GameFieldGrid grid, JPanel panel){
        int i=0; int j=0;
        for (FieldCell[] arr : grid.getCellsArr()) {
            for (FieldCell cell : arr) {
                JButton jButton = (JButton) panel.getComponent((i*(gameField.getRowNum())+j));
                jButton.setText(cell.getSkin());
                j++;
            }
            j=0;
            i++;
        }
    }

    private void renderHiddenGrid(GameFieldGrid grid, JPanel panel){
        int i=0; int j=0;
        for (FieldCell[] arr : grid.getCellsArr()) {
            for (FieldCell cell : arr) {
                JButton jButton = (JButton) panel.getComponent((i*(gameField.getRowNum())+j));
                String s = cell.getSkin();
                if(((s != CellState.ShipDamaged.getSkin())&(s!=CellState.WaterAttacked.getSkin()))&(cell instanceof GameFieldCell)){s = CellState.Unknown.getSkin();}
                jButton.setText(s);
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

    private void componentsInit(){
        jFrame = new JFrame();
        gameFieldPanel = new JPanel();
        playerGridName = new JTextField(PLAYERGRID);
        enemyGridName = new JTextField(ENEMYGRID);
        gameStatus = new JTextField("старт игры");
        pl1Panel = new JPanel();
        pl2Panel = new JPanel();
    }

    private void setComponentsPars(){
        playerGridName.setEditable(false);
        enemyGridName.setEditable(false);
        gameStatus.setEditable(false);
        jFrame.setTitle("BattleShips");
        jFrame.setSize(gameField.getColumnNum()*70, gameField.getRowNum()*70);
        jFrame.setLayout(new BorderLayout());
        pl1Panel.setLayout(new GridLayout(gameField.getColumnNum(), gameField.getRowNum()));
        pl2Panel.setLayout(new GridLayout(gameField.getColumnNum(), gameField.getRowNum()));
    }

    private void constructJFrame(){
        gameFieldPanel.add(playerGridName);
        gameFieldPanel.add(pl1Panel);
        gameFieldPanel.add(enemyGridName);
        gameFieldPanel.add(pl2Panel);
        jFrame.add(gameFieldPanel, BorderLayout.CENTER);
        jFrame.add(gameStatus, BorderLayout.SOUTH);
    }

    private void jFrameInit(){//TODO оптимизировать
        componentsInit();
        setComponentsPars();
        initAddCells();
        constructJFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setGameStatus(String s) {
        gameStatus.setText(s);
    }

    public JPanel getPl2Panel() {
        return pl2Panel;
    }
}