package ru.javabit.gameField;

public abstract class FieldCell {

    private int x;//real coordinates, include column row name cells (0-10)
    private int y;//real coordinates, include column row name cells (0-10)
    private String skin;

    FieldCell (int x, int y){
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

}