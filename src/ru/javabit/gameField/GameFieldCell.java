package ru.javabit.gameField;

public class GameFieldCell extends FieldCell {

    private CellState state;

    GameFieldCell(int x, int y){
        super(x, y);
        CellState state = CellState.FreeWater;
        this.state = state;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public String getSkin() {
        return state.getSkin();
    }

}