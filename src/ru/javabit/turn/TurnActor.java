package ru.javabit.turn;

/**
 * turn actor is not player it is only turn actor, it may be some algorithm to do smth in code while game in process
 * turn actor maybe human, computer, or other
 */

public class TurnActor {

    private TurnActorType turnActorType;
    private String turnActorName;
    private int turnActorId;
    //private PlayerComputerAI computerAI;

    private TurnControlled turnControlled;

    public TurnActor(TurnActorType turnActorType, String turnActorName, TurnControlled turnControlled, int turnActorId) {
        this.turnActorType = turnActorType;
        this.turnActorName = turnActorName;
        this.turnActorId = turnActorId;
        this.turnControlled = turnControlled;
    }

    public TurnActorType getTurnActorType() {
        return turnActorType;
    }

    public String getTurnActorName() {
        return turnActorName;
    }

    public int getTurnActorId() {
        return turnActorId;
    }

//    public PlayerComputerAI getComputerAI() {
//        return computerAI;
//    }

    public TurnControlled getTurnControlled() {
        return turnControlled;
    }
}