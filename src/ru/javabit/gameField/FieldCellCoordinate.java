package ru.javabit.gameField;

public class FieldCellCoordinate {

    private int x;
    private int y;

    public FieldCellCoordinate(int x, int y) {
        this.x = x;//real coordinates, include column row name cells (0-10)
        this.y = y;//real coordinates, include column row name cells (0-10)
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        FieldCellCoordinate fieldCellCoordinate = (FieldCellCoordinate) obj;
        if ((fieldCellCoordinate.getX() == this.getX())&&(fieldCellCoordinate.getY() == this.getY())){
            return true;
        } else {
            return false;
        }
    }
}