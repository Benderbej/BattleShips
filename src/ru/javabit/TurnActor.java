package ru.javabit;

/**
 * turn actor is not player it is only turn actor, it may be some algorithm to do smth in code while game in process
 * turn actor maybe user, computer, or other
 */

public class TurnActor {

    private TurnActorType turnActorType;
    private String turnActorName;

    public TurnActor(TurnActorType turnActorType, String turnActorName) {
        this.turnActorType = turnActorType;
        this.turnActorName = turnActorName;
    }

    public TurnActorType getTurnActorType() {
        return turnActorType;
    }

    public String getTurnActorName() {
        return turnActorName;
    }
}