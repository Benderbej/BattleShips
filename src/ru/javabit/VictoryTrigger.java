package ru.javabit;

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

    VictoryTrigger(ArrayList<Ship> shipList1, ArrayList<Ship> shipList2) {
        this.shipList1 = shipList1;
        this.shipList2 = shipList2;
        for (Ship ship: shipList1) {
            shipCellsCount1 += ship.getSize();
        }
        for (Ship ship: shipList2) {
            shipCellsCount2 += ship.getSize();
        }
    }

    public void minusCell(int turnActorId) {
        switch (turnActorId){
            case 1: shipCellsCount1--; break;
            case 2: shipCellsCount2--; break;
        }
        System.out.println("enemyShipCellsCount1="+shipCellsCount1);
        System.out.println("enemyShipCellsCount2="+shipCellsCount2);
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
}
