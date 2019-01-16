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
    ArrayList<Integer> clientsIdsList;
    private int winerPlayerNum = 0;//0 - no winner

    public VictoryTrigger(Fleet fleet1, Fleet fleet2) {//SingleGame
        shipCellsCount1 = fleet1.getShipListCellsCount();
        shipCellsCount2 = fleet2.getShipListCellsCount();
        clientsIdsList = new ArrayList<Integer>();
        clientsIdsList.add(1);
        clientsIdsList.add(2);
    }

    public VictoryTrigger(Fleet fleet1, Fleet fleet2, ArrayList<Integer> clientsIdsList) {
        this(fleet1, fleet2);
        this.clientsIdsList = clientsIdsList;
    }

    public void minusCell(int turnActorId) {
        if(turnActorId == clientsIdsList.get(0)){shipCellsCount1--;}
        if(turnActorId == clientsIdsList.get(1)){shipCellsCount2--;}
        winCheck();
    }

    private void winCheck() {
        if(conditionCheck(shipCellsCount1)){
            isFinished = true;
            winerPlayerNum = clientsIdsList.get(0);
        }
        if(conditionCheck(shipCellsCount2)){
            isFinished = true;
            winerPlayerNum = clientsIdsList.get(1);
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