package ru.javabit.turn;


import ru.javabit.VictoryTrigger;
import ru.javabit.gameField.GameField;
import ru.javabit.report.ConsoleDialogue;
import ru.javabit.view.GameFieldRenderer;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * turn controller - controls and make turns
 */

public class TurnMaster {

    private static TurnMaster turnMaster;
    private static LinkedList<TurnActor> turnActors;
    private static ListIterator<TurnActor> actorIterator;
    public GameField gameField;
    private GameFieldRenderer gameFieldRenderer;
    public static VictoryTrigger victoryTrigger;
    private ConsoleDialogue consoleDialogue;

    private TurnMaster() {
        consoleDialogue = new ConsoleDialogue();
    }

    public static TurnMaster getInstance() {
        if(turnMaster == null){
            turnMaster = new TurnMaster();
            turnActors = new LinkedList<TurnActor>();
        }
        return turnMaster;
    }

    public void initComputerVsComputer(GameField gameField, String name1, String name2){
        PlayerComputerAI computer1AI = new PlayerComputerAI(gameField.getEnemyFieldGrid().getCellsArr());
        PlayerComputerAI computer2AI = new PlayerComputerAI(gameField.getPlayerFieldGrid().getCellsArr());
        addTurnActor(new TurnActor(TurnActorType.COMPUTER,name1,computer1AI,1));//0 - is reserved for nowinner
        addTurnActor(new TurnActor(TurnActorType.COMPUTER,name2,computer2AI,2));
        this.gameField = gameField;

    }

    public void addTurnActor(TurnActor turnActor) {
        turnActors.add(turnActor);
    }

    public void startTurning() throws InterruptedException {//TODO отдельный поток
        int i=0;
        TurnActor actor = null;
        actorIterator = turnActors.listIterator();
        int turnLimit = (gameField.getColumnNum()+1)*(gameField.getRowNum()+1)*2;
        while (i<=turnLimit){
            Thread.sleep(10);
            if (actorIterator.hasNext()){
                if(actor != null){makeReport("ходит "+actor.getTurnActorName());}
                actor = actorIterator.next();
                makeTurn(actor);
            } else {
                i--;
                actorIterator = turnActors.listIterator();
            }
            if(checkVictory()){
                victoryTrigger.getWinerPlayerNum();
                makeReport("Выиграл "+actor.getTurnActorName());
                break;
            }
            i++;
        }
        makeReport("КОНЕЦ ИГРЫ");
    }

    public void makeTurn(TurnActor turnActor) {
        switch (turnActor.getTurnActorType()){
            case COMPUTER:
                makeAutoTurn(turnActor);
            case USER:
                makeUserTurn(turnActor);
        }
        gameFieldRenderer.renderGameField();
    }

    private void makeAutoTurn (TurnActor turnActor) {
        if(turnActor.getComputerAI().attack()){
            victoryTrigger.minusCell(turnActor.getTurnActorId());
        }
    }

    private void makeUserTurn (TurnActor turnActor) { }

    private boolean checkVictory() {
        boolean victory = false;
        if(victoryTrigger.isFinished()){victory = true;}
        return victory;
    }

    public void setGameFieldRenderer(GameFieldRenderer gameFieldRenderer) {
        this.gameFieldRenderer = gameFieldRenderer;
    }

    public void setVictoryTrigger(VictoryTrigger victoryTrigger) {
        this.victoryTrigger = victoryTrigger;
    }

    private void makeReport(String s) {
        consoleDialogue = new ConsoleDialogue();//TODO ConsoleDialoge maybe singleton with static makeReport
        consoleDialogue.makeReport(s);
    }
}