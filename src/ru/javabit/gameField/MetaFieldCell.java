package ru.javabit.gameField;

public class MetaFieldCell extends FieldCell {

    MetaFieldCell(int x, int y, String skin) {
        super(x, y);
        this.setSkin(skin);
    }

}