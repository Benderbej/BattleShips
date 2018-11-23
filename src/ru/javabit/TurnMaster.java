package ru.javabit;


import ru.javabit.gameField.GameField;

import java.util.*;

/**
 * turn controller - controls and make turns
 */

public class TurnMaster {

    private static TurnMaster turnMaster;
    private static LinkedList<TurnActor> turnActors;
    private static ListIterator<TurnActor> actorIterator;
    public GameField gameField;

    private TurnMaster() {}

    public static TurnMaster getInstance() {
        if(turnMaster == null){
            turnMaster = new TurnMaster();
            turnActors = new LinkedList<TurnActor>();
        }
        return turnMaster;
    }

    public void initComputerVsComputer(GameField gameField, String name1, String name2){
        addTurnActor(new TurnActor(TurnActorType.COMPUTER,name1));
        addTurnActor(new TurnActor(TurnActorType.COMPUTER,name2));
        this.gameField = gameField;
        PlayerComputerAI computer1AI = new PlayerComputerAI(gameField.getPlayerFieldGrid().getCellsArr());
        PlayerComputerAI computer2AI = new PlayerComputerAI(gameField.getEnemyFieldGrid().getCellsArr());
    }

    public void addTurnActor(TurnActor turnActor) {
        turnActors.add(turnActor);
    }

    public void startTurning() {//TODO отдельный поток
        int i=0;
        TurnActor actor;
        System.out.println("turnActors size"+turnActors.size());
        actorIterator = turnActors.listIterator();

        while (i<10){//неплохо бы ограничить цикл на всякий случай
            if (actorIterator.hasNext()){
                actor = actorIterator.next();
                makeTurn(actor);
                System.out.println(actor.getTurnActorName());//TODO вынести
            } else {
                actorIterator = turnActors.listIterator();
            }

            if(i==5675){//добавить

                break;
            }
            i++;
        }
        System.out.println("КОНЕЦ ИГРЫ");//TODO вынести
    }

    private TurnActor getCurrentTurnActor() {
        TurnActor currentTurnActor = turnActors.iterator().next();

        return currentTurnActor;
    }

    public void makeTurn(TurnActor turnActor) {
        switch (turnActor.getTurnActorType()){
            case COMPUTER:
                makeAutoTurn();
            case USER:
                makeUserTurn();
        }
    }

    private void makeAutoTurn () {
        System.out.println("turn");
    }

    private void makeUserTurn () {

    }
}