package ru.javabit;

import ru.javabit.ship.Fleet;
import ru.javabit.ship.Ship;

import java.util.ArrayList;

public class VictoryTrigger {

    //one of possible triggers..
    private int shipCellsCount1;
    private int shipCellsCount2;
    ArrayList<Ship> shipList1;
    ArrayList<Ship> shipList2;
    private boolean isFinished = false;

    private int winerPlayerNum = 0;//0 - no winner

    public VictoryTrigger(Fleet fleet1, Fleet fleet2) {
        shipCellsCount1 = fleet1.getShipListCellsCount();
        shipCellsCount2 = fleet2.getShipListCellsCount();
    }

    public void minusCell(int turnActorId) {
        switch (turnActorId){
            case 1: shipCellsCount1--; break;
            case 2: shipCellsCount2--; break;
        }
        winCheck();
    }

    private void winCheck() {
        if(conditionCheck(shipCellsCount1)){
            isFinished = true;
            winerPlayerNum = 1;
        }
        if(conditionCheck(shipCellsCount2)){
            isFinished = true;
            winerPlayerNum = 2;
        }
    }//TODO interface where this should be main method if win circumstances different

    private boolean conditionCheck(int shipCellsCount) {
        boolean victory = false;
        if(shipCellsCount == 0){
            victory = true;
        }
        return victory;
    }

    private void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public int getWinerPlayerNum() {
        return winerPlayerNum;
    }

    public int getShipCellsCount1() {
        return shipCellsCount1;
    }

    public int getShipCellsCount2() {
        return shipCellsCount2;
    }
}