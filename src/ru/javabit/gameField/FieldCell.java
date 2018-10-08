package ru.javabit.gameField;

public abstract class FieldCell {

    private int x;//real coordinates, include column row name cells (0-10)
    private int y;//real coordinates, include column row name cells (0-10)
    private String skin;//лучше бы было здесь хранить Enum State и оттуда брать skin, точнее создать класс FieldState и хранить его экземпляр в экземпляре FieldState, а Enum оставить для скинов итд

    FieldCell (int x, int y){
        this.x = x;
        this.y = y;
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
        if ((fieldCell.getX() == this.x)&&(fieldCell.getY() == this.y)){
            return true;
        } else {
            return false;
        }
    }
}