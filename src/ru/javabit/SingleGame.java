package ru.javabit;

import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.GameField;
import ru.javabit.report.ConsoleDialogue;
import ru.javabit.report.Report;
import ru.javabit.report.UserDialogue;
import ru.javabit.ship.Fleet;
import ru.javabit.ship.FleetsDisposal;
import ru.javabit.turn.TurnMaster;
import ru.javabit.view.GameFieldRenderable;
import ru.javabit.view.GameFieldSwingRenderer;

public class SingleGame implements Game {

    private static Game game;
    UserDialogue dialogue;
    private GameField gameField;
    FleetsDisposal fleetsDisposal;
    GameFieldRenderable gameFieldRenderer;
    //GameFieldRenderer gameFieldRenderer;
    //GameFieldSwingRenderer gameFieldSwingRenderer;
    public static Fleet fleet1;
    public static Fleet fleet2;
    TurnMaster turnMaster;
    VictoryTrigger victoryTrigger;

    public SingleGame(){ }

    /*
        public void initGame() throws BattleShipsException {
            meetUser();
            gameField = new GameField(11, 11,"computer 1", "computer 2");
            //gameField = new GameField(10, 8,"computer 1", "computer 2"); //CAN CATCH RUNTIME
            //gameField = new GameField(6, 6,"computer 1", "computer 2"); //CAN CATCH EXCEPTION

            //gameField = new GameField(25, 25,"computer 1", "computer 2");
            fleet1 = new Fleet();
            fleet2 = new Fleet();
            //fleet1 = new Fleet(2,0,1,0);
            //fleet2 = new Fleet(2,0,1,0);
            fleetsDisposal = new FleetsDisposal(gameField, fleet1, fleet2);
            fleetsDisposal.disposeAutoAuto();
            gameFieldRenderer = new GameFieldRenderer(gameField);
            gameFieldRenderer.renderGameField();
        }

        public void startGame() throws InterruptedException {//todo init gameprocess thread
            victoryTrigger = new VictoryTrigger(fleet1, fleet2);
            turnMaster = new TurnMaster();
            turnMaster.initComputerVsComputer(gameField, "computer 1", "computer 2");
            turnMaster.setVictoryTrigger(victoryTrigger);
            turnMaster.setGameFieldRenderer(gameFieldRenderer);
            new Thread(turnMaster).start();
        }
    */
    public void initGame() throws BattleShipsException {
        meetUser();
        gameField = new GameField(11, 11,"computer 1", "computer 2");
        fleet1 = new Fleet();
        fleet2 = new Fleet();
        fleetsDisposal = new FleetsDisposal(gameField, fleet1, fleet2);
        fleetsDisposal.disposeAutoAuto();
        gameFieldRenderer = new GameFieldSwingRenderer(gameField);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        gameFieldRenderer.renderGameField();
    }

    public void startGame() throws InterruptedException {//todo init gameprocess thread
        victoryTrigger = new VictoryTrigger(fleet1, fleet2);
        turnMaster = new TurnMaster();
        //turnMaster.initComputerVsComputer(gameField, "computer 1", "computer 2");
        turnMaster.setGameFieldRenderer(gameFieldRenderer);
        turnMaster.initHumanVsComputer(gameField, "human", "computer");
        turnMaster.setVictoryTrigger(victoryTrigger);
        new Thread(turnMaster).start();
    }

    private void meetUser() {
        dialogue = new ConsoleDialogue();
        dialogue.makeReport(Report.Greeting.getText());
    }

    public GameField getGameField() {
        return gameField;
    }










    /*

    private Game(){ }

    public static Game getInstance(){
        if(game == null){
            game = new Game();
        }
        return game;
    }

    public void initGame() throws BattleShipsException {
        meetUser();
        gameField = new GameField(11, 11,"computer 1", "computer 2");
        //gameField = new GameField(10, 8,"computer 1", "computer 2"); //CAN CATCH RUNTIME
        //gameField = new GameField(6, 6,"computer 1", "computer 2"); //CAN CATCH EXCEPTION

        //gameField = new GameField(25, 25,"computer 1", "computer 2");
        fleet1 = new Fleet();
        fleet2 = new Fleet();
        //fleet1 = new Fleet(2,0,1,0);
        //fleet2 = new Fleet(2,0,1,0);
        fleetsDisposal = new FleetsDisposal(gameField, fleet1, fleet2);
        fleetsDisposal.disposeAutoAuto();
        gameFieldRenderer = new GameFieldRenderer(gameField);
        gameFieldRenderer.renderGameField();
    }

    public void startGame() throws InterruptedException {//todo init gameprocess thread
        victoryTrigger = new VictoryTrigger(fleet1, fleet2);
        turnMaster = TurnMaster.getInstance();
        turnMaster.initComputerVsComputer(gameField, "computer 1", "computer 2");
        turnMaster.setVictoryTrigger(victoryTrigger);
        turnMaster.setGameFieldRenderer(gameFieldRenderer);
        new Thread(turnMaster).start();
    }

    public void initGame2() throws BattleShipsException {
        meetUser();
        gameField = new GameField(11, 11,"computer 1", "computer 2");
        fleet1 = new Fleet();
        fleet2 = new Fleet();
        fleetsDisposal = new FleetsDisposal(gameField, fleet1, fleet2);
        fleetsDisposal.disposeAutoAuto();
        gameFieldRenderer = new GameFieldSwingRenderer(gameField);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        gameFieldRenderer.renderGameField();
    }

    public void startGame2() throws InterruptedException {//todo init gameprocess thread
        victoryTrigger = new VictoryTrigger(fleet1, fleet2);
        turnMaster = TurnMaster.getInstance();
        //turnMaster.initComputerVsComputer(gameField, "computer 1", "computer 2");
        turnMaster.setGameFieldRenderer(gameFieldRenderer);
        turnMaster.initHumanVsComputer(gameField, "human", "computer");
        turnMaster.setVictoryTrigger(victoryTrigger);
        new Thread(turnMaster).start();
    }

    private void meetUser() {
        dialogue = new ConsoleDialogue();
        dialogue.makeReport(Report.Greeting.getText());
    }

    public Fleet getFleet1() {
        return fleet1;
    }

    public Fleet getFleet2() {
        return fleet2;
    }



     */


}
