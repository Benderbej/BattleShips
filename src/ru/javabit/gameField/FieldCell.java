package ru.javabit.gameField;

public abstract class FieldCell  {

    private FieldCellCoordinate fieldCellCoordinate;
    private int x;//real coordinates, include column row name cells (0-10)
    private int y;//real coordinates, include column row name cells (0-10)
    private String skin;//лучше бы было здесь хранить Enum State и оттуда брать skin, точнее создать класс FieldState и хранить его экземпляр в экземпляре FieldState, а Enum оставить для скинов итд

    //FieldCell (int x, int y){
    //    this.x = x;
    //    this.y = y;
    //}

    FieldCell (FieldCellCoordinate fieldCellCoordinate) {
        this.x = fieldCellCoordinate.getX();
        this.y = fieldCellCoordinate.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    @Override
    public boolean equals(Object obj) {
        FieldCell fieldCell = (FieldCell) obj;
        if ((fieldCell.getX() == this.getX())&&(fieldCell.getY() == this.getY())){
            return true;
        } else {
            return false;
        }
    }
}