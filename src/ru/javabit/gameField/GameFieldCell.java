package ru.javabit.gameField;

import ru.javabit.view.CellState;
/**
 * клетка игрового поля на которой может быть размещен корабль
 * имеет поле состояние state, от вида этого состяние зависит внешний вид клетки skin, поэтому переопределен getSkin в
 */



public class GameFieldCell extends FieldCell {

    private CellState state;

    GameFieldCell(int x, int y){
        super(new FieldCellCoordinate(x,y));
        CellState state = CellState.FreeWater;
        this.state = state;
        this.setSkin(this.state.getSkin());
    }

    @Override
    public String getSkin() {
        return state.getSkin();
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
        this.setSkin(state.getSkin());
    }

    public static boolean checkIfCellOccupied(GameFieldCell fieldCell){
        if ((fieldCell.getState() == CellState.Reserved) || (fieldCell.getState() == CellState.ShipPart)){return true;}
        return false;
    }

    public static void setCellOccupied(GameFieldCell fieldCell){
        fieldCell.setState(CellState.ShipPart);
    }
}