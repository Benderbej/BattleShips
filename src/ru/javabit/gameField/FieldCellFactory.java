package ru.javabit.gameField;

public class FieldCellFactory {

    GameFieldCell createGameFieldCell(int x, int y){
        GameFieldCell cell = new GameFieldCell(x, y);
        return cell;
    }

    MetaFieldCell createMetaFieldCell(int x, int y, String skin){
        MetaFieldCell cell = new MetaFieldCell(x, y, skin);
        return cell;
    }


}
