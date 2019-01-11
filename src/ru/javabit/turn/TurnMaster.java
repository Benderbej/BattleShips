package ru.javabit.turn;


import ru.javabit.VictoryTrigger;
import ru.javabit.gameField.GameField;
import ru.javabit.report.ConsoleDialogue;
import ru.javabit.view.GameFieldRenderable;
import ru.javabit.view.GameFieldRenderer;
import ru.javabit.view.GameFieldSwingRenderer;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * turn controller - controls and make turns
 */

public class TurnMaster implements Runnable {

    private static TurnMaster turnMaster;
    protected static LinkedList<TurnActor> turnActors;
    protected static ListIterator<TurnActor> actorIterator;
    public GameField gameField;
    protected GameFieldRenderable gameFieldRenderer;
    public static VictoryTrigger victoryTrigger;
    protected ConsoleDialogue consoleDialogue;

    public TurnMaster() {
        consoleDialogue = new ConsoleDialogue();
        turnActors = new LinkedList<TurnActor>();
    }

    public void initComputerVsComputer(GameField gameField, String name1, String name2) {
        TurnControlled computer1AI = new PlayerComputerAI(gameField.getEnemyFieldGrid().getCellsArr());
        TurnControlled computer2AI = new PlayerComputerAI(gameField.getPlayerFieldGrid().getCellsArr());
        addTurnActor(new TurnActor(TurnActorType.COMPUTER, name1, computer1AI, 1));//0 - is reserved for nowinner
        addTurnActor(new TurnActor(TurnActorType.COMPUTER, name2, computer2AI, 2));
        this.gameField = gameField;
    }

    public void initHumanVsComputer(GameField gameField, String name1, String name2) {
        TurnControlled humanAI = new HumanControl(gameField.getEnemyFieldGrid().getCellsArr(), ((GameFieldSwingRenderer) gameFieldRenderer).getPl2Panel());
        TurnControlled computerAI = new PlayerComputerAI(gameField.getPlayerFieldGrid().getCellsArr());
        addTurnActor(new TurnActor(TurnActorType.HUMAN, name1, humanAI, 1));//0 - is reserved for nowinner
        addTurnActor(new TurnActor(TurnActorType.COMPUTER, name2, computerAI, 2));
        this.gameField = gameField;
    }

    public void addTurnActor(TurnActor turnActor) {
        turnActors.add(turnActor);
    }

    public void makeTurn(TurnActor turnActor) {
        if (turnActor.getTurnControlled().attack()) {
            victoryTrigger.minusCell(turnActor.getTurnActorId());
        }
        gameFieldRenderer.renderGameField();
    }

    //private void

    protected boolean checkVictory() {
        boolean victory = false;
        if (victoryTrigger.isFinished()) {
            victory = true;
        }
        return victory;
    }

    public void setGameFieldRenderer(GameFieldRenderable gameFieldRenderer) {
        this.gameFieldRenderer = gameFieldRenderer;
    }

    public void setVictoryTrigger(VictoryTrigger victoryTrigger) {
        this.victoryTrigger = victoryTrigger;
    }

    protected void makeReport(String s) {
        consoleDialogue = new ConsoleDialogue();//TODO ConsoleDialoge maybe singleton with static makeReport
        consoleDialogue.makeReport(s);
    }

    @Override
    public void run() {
        int i = 0;
        TurnActor actor = null;
        actorIterator = turnActors.listIterator();
        int turnLimit = (gameField.getColumnNum() + 1) * (gameField.getRowNum() + 1) * 2;
        while (i <= turnLimit) {
            //Thread.currentThread().wait();

            if (i < turnActors.size()) {
                System.out.println("turnActors.size()=" + turnActors.size());
            }

            if (actorIterator.hasNext()) {
                if (actor != null) {
                    makeReport("ходит " + actor.getTurnActorName());
                    gameFieldRenderer.setGameStatus("ходит " + actor.getTurnActorName());
                }
                actor = actorIterator.next();
                makeTurn(actor);
            } else {
                i--;
                actorIterator = turnActors.listIterator();
            }
            if (checkVictory()) {
                victoryTrigger.getWinerPlayerNum();
                makeReport("Выиграл " + actor.getTurnActorName());
                gameFieldRenderer.setGameStatus("Выиграл " + actor.getTurnActorName());
                gameFieldRenderer.showEnemyPositions();
                break;
            }
            i++;
        }
        makeReport("КОНЕЦ ИГРЫ");
    }
}