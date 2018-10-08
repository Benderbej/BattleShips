package ru.javabit.gameField;

public class GameFieldCell extends FieldCell {

    private CellState state;

    GameFieldCell(int x, int y){
        super(x, y);
        CellState state = CellState.FreeWater;
        this.state = state;
        this.setSkin(this.state.getSkin());
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }


}