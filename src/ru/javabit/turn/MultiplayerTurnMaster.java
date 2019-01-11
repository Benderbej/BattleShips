package ru.javabit.turn;

import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.GameField;
import ru.javabit.netgame.client.MultiplayerHumanControl;
import ru.javabit.report.ConsoleDialogue;
import ru.javabit.view.GameFieldSwingRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class MultiplayerTurnMaster extends TurnMaster {


    TurnControlled humanAI;
    TurnControlled human2AI;
    private final ArrayList<Integer> clientsIdsList;
    private HashMap<Integer, Boolean> playerActiveness;//clientIds->true if active
    private HashMap<Integer, FieldCell> remotePlayersTurns;//field cells chosen by remote players (0 -attacker 11-defender)
    private int currentTurnClientHandlerId;

    public MultiplayerTurnMaster(ArrayList<Integer> clientsIdsList, HashMap<Integer, Boolean> playerActiveness) {
        super();
        this.clientsIdsList = clientsIdsList;
        this.playerActiveness = playerActiveness;
    }

    public void makeTurn(TurnActor turnActor) {
        if (turnActor.getTurnControlled().attack()) {
            victoryTrigger.minusCell(turnActor.getTurnActorId());
        }
        //gameFieldRenderer.renderGameField();
    }

    @Override
    public void run() {
        int i = 0;
        TurnActor actor = null;
        actorIterator = turnActors.listIterator();
        int turnLimit = (gameField.getColumnNum() + 1) * (gameField.getRowNum() + 1) * 2;

        while (i <= turnLimit) {

            if (actorIterator.hasNext()) {

                if (actor != null) {
                    makeReport("ходит " + actor.getTurnActorName());
                    //gameFieldRenderer.setGameStatus("ходит " + actor.getTurnActorName());
                    System.out.println("ходит " + actor.getTurnActorName());
                }

                actor = actorIterator.next();
                currentTurnClientHandlerId = actor.getTurnActorId();
                makeTurn(actor);

            } else {
                i--;
                actorIterator = turnActors.listIterator();
            }
            if (checkVictory()) {
                victoryTrigger.getWinerPlayerNum();
                makeReport("Выиграл " + actor.getTurnActorName());
                //gameFieldRenderer.setGameStatus("Выиграл " + actor.getTurnActorName());
                //gameFieldRenderer.showEnemyPositions();
                System.out.println("Выиграл " + actor.getTurnActorName());
                break;
            }
            i++;
        }
        makeReport("КОНЕЦ ИГРЫ");
    }

    public void initHumanVsHuman(GameField gameField, String name1, String name2) {
        humanAI = new MultiplayerHumanControl(gameField.getEnemyFieldGrid().getCellsArr(), remotePlayersTurns, clientsIdsList.get(0), playerActiveness.get(clientsIdsList.get(0)));//1 attacker
        human2AI = new MultiplayerHumanControl(gameField.getPlayerFieldGrid().getCellsArr(), remotePlayersTurns, clientsIdsList.get(1), playerActiveness.get(clientsIdsList.get(1)));//2 - defender
        addTurnActor(new TurnActor(TurnActorType.HUMAN, name1, humanAI, clientsIdsList.get(0)));//0 - is reserved for nowinner
        addTurnActor(new TurnActor(TurnActorType.HUMAN, name2, human2AI, clientsIdsList.get(1)));
        this.gameField = gameField;


//        TurnControlled humanAI = new HumanControl(gameField.getEnemyFieldGrid().getCellsArr(), ((GameFieldSwingRenderer) gameFieldRenderer).getPl2Panel());
//        TurnControlled human2AI = new HumanControl(gameField.getPlayerFieldGrid().getCellsArr(), ((GameFieldSwingRenderer) gameFieldRenderer).getPl2Panel());
//        addTurnActor(new TurnActor(TurnActorType.HUMAN, name1, humanAI, 1));//0 - is reserved for nowinner
//        addTurnActor(new TurnActor(TurnActorType.HUMAN, name2, human2AI, 2));
//        this.gameField = gameField;
    }

    private boolean searchClients(){

        return false;
    }

    public void setRemotePlayerTurn(Integer clientHandlerId, FieldCell fieldCell){//если в remotePlayersTurns уже есть что-то то запрос просто игнорится
        if(remotePlayersTurns.get(clientHandlerId) == null){
            remotePlayersTurns.put(clientHandlerId, fieldCell);
        }
    }

    public int getCurrentTurnClientHandlerId() {
        return currentTurnClientHandlerId;
    }

}
