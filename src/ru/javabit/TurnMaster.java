package ru.javabit;


import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * turn controller - controls and make turns
 */

public class TurnMaster {

    private static TurnMaster turnMaster;
    private static LinkedList<TurnActor> turnActors;

    private TurnMaster(){}

    public static TurnMaster getInstance(){
        if(turnMaster == null){
            turnMaster = new TurnMaster();
            turnActors = new LinkedList<TurnActor>();
        }
        return turnMaster;
    }

    public void initComputerVsComputer(String name1, String name2){
        addTurnActor(new TurnActor(TurnActorType.COMPUTER,name1));
        addTurnActor(new TurnActor(TurnActorType.COMPUTER,name2));
    }

    public void addTurnActor(TurnActor turnActor) {
        turnActors.add(turnActor);
    }

    public void startTurning() {//TODO отдельный поток
        int i=0;
        while (i<200){//неплохо бы ограничить цикл на всякий случай
            System.out.println("ход:"+i);//TODO вынести
            makeTurn(getCurrentTurnActor());
            if(i==5675){//добавить

                break;
            }
            i++;
        }
        System.out.println("КОНЕЦ ИГРЫ");//TODO вынести
    }

    private TurnActor getCurrentTurnActor () {
        TurnActor currentTurnActor = turnActors.iterator().next();
        return currentTurnActor;
    }

    public void makeTurn(TurnActor turnActor){
        switch (turnActor.getTurnActorType()){
            case COMPUTER:
                makeAutoTurn();
            case USER:
                makeUserTurn();
        }
        if (!turnActors.iterator().hasNext()){
            turnActors.iterator();
        } else {turnActors.iterator().next();}
    }

    private void makeAutoTurn () {

    }

    private void makeUserTurn () {

    }
}