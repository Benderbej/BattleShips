package ru.javabit.gameField;

public class MetaFieldCell extends FieldCell {

    MetaFieldCell(int x, int y, String skin) {
        super(new FieldCellCoordinate(x,y));
        this.setSkin(skin);
    }

}