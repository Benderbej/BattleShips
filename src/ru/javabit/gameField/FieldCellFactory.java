package ru.javabit.gameField;

import java.io.Serializable;

public class FieldCellFactory implements Serializable {

    GameFieldCell createGameFieldCell(int x, int y) {
        GameFieldCell cell = new GameFieldCell(x, y);
        return cell;
    }

    MetaFieldCell createMetaFieldCell(int x, int y, String skin) {
        if(y==0 && skin.length()==1){skin = " "+skin;}
        MetaFieldCell cell = new MetaFieldCell(x, y, skin);
        return cell;
    }
}