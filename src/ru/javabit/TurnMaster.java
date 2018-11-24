package ru.javabit;


import ru.javabit.gameField.GameField;
import ru.javabit.view.GameFieldRenderer;

import java.util.*;

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

    private TurnMaster() {}

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
        System.out.println("turnActors size"+turnActors.size());
        actorIterator = turnActors.listIterator();
        int turnLimit = (gameField.getColumnNum()+1)*(gameField.getRowNum()+1)*2;
        while (i<=turnLimit){
            System.out.println("ход: "+i);
            Thread.sleep(10);
            if (actorIterator.hasNext()){
                if(actor != null){System.out.println(actor.getTurnActorName());}else { System.out.println("actor is null"); }//TODO вынести
                actor = actorIterator.next();
                makeTurn(actor);
            } else {
                i--;
                actorIterator = turnActors.listIterator();
            }
            if(checkVictory()){
                victoryTrigger.getWinerPlayerNum();
                System.out.println("Выиграл"+actor.getTurnActorName());
                break;
            }
            i++;
        }
        System.out.println("КОНЕЦ ИГРЫ");//TODO вынести
        System.out.println("turnActors size"+turnActors.size());
    }

    private TurnActor getCurrentTurnActor() {
        TurnActor currentTurnActor = turnActors.iterator().next();

        return currentTurnActor;
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
        //turnActor.getComputerAI().attack();
        if(turnActor.getComputerAI().attack()){
            //turnActors.po
            victoryTrigger.minusCell(turnActor.getTurnActorId());
            System.out.println(">>>HIT!<<<");
        }
        System.out.println("turn");
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
}