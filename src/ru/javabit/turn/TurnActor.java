package ru.javabit.turn;

/**
 * turn actor is not player it is only turn actor, it may be some algorithm to do smth in code while game in process
 * turn actor maybe user, computer, or other
 */

public class TurnActor {

    private TurnActorType turnActorType;
    private String turnActorName;
    private int turnActorId;
    private PlayerComputerAI computerAI;

    public TurnActor(TurnActorType turnActorType, String turnActorName, PlayerComputerAI computerAI, int turnActorId) {
        this.turnActorType = turnActorType;
        this.turnActorName = turnActorName;
        this.computerAI = computerAI;
        this.turnActorId = turnActorId;
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

    public PlayerComputerAI getComputerAI() {
        return computerAI;
    }
}