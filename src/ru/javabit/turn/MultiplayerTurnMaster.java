package ru.javabit.turn;

public class MultiplayerTurnMaster extends TurnMaster {

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
                Thread thread = new Thread(new ServeClient(actor));
                thread.start();
                makeTurn(actor);
                thread.interrupt();

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
        TurnControlled humanAI = new HumanControl(gameField.getEnemyFieldGrid().getCellsArr(), ((GameFieldSwingRenderer) gameFieldRenderer).getPl2Panel());
        TurnControlled human2AI = new HumanControl(gameField.getPlayerFieldGrid().getCellsArr(), ((GameFieldSwingRenderer) gameFieldRenderer).getPl2Panel());
        addTurnActor(new TurnActor(TurnActorType.HUMAN, name1, humanAI, 1));//0 - is reserved for nowinner
        addTurnActor(new TurnActor(TurnActorType.HUMAN, name2, human2AI, 2));
        this.gameField = gameField;
    }

    private boolean searchClients(){

        return false;
    }

    private class ServeClient implements Runnable {

        ServeClient(TurnActor actor){

        }

        @Override
        public void run() {

        }
    }
}
