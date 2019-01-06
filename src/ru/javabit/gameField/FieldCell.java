package ru.javabit.gameField;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * класс абстрактный так как обязывает нас выбрать либо gameFieldCell реализацию, либо MetaFieldCell реализацию
 * клетка FieldCell - это условная клетка на разлинованном игровом поле, включающая в себя также клетки в столбце и строке координат(Meta), так как
 * и те и другие клетки(meta и game) должны друг с другом соотносится на игровом поле, и те и другие имеют координаты(это как бы реальные координаты разметки, не обязательно игровые координаты)
 * координата реализована в виде отдельного класса так как представляет отдельную абстрактную сущность
 * так же есть локальная переменная skin отвечающая за отображение клетки игрового поля
 *
 * клетки сравниватся по координатам, другие параметры не важны, сравнение нужно для последующей реализации логики игры, когда твое игровое поле с кораблями соперника соответствует игровому полю врага,
 * и когда нужно сравнить клетки этих полей, чтобы а4 на одном оле было равно  а4 на другом.
 */

public abstract class FieldCell implements Serializable {

    private FieldCellCoordinate fieldCellCoordinate;
    private String skin;//лучше бы было здесь хранить Enum State и оттуда брать skin, точнее создать класс FieldState и хранить его экземпляр в экземпляре FieldState, а Enum оставить для скинов итд

    FieldCell (FieldCellCoordinate fieldCellCoordinate) {
        this.fieldCellCoordinate = fieldCellCoordinate;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public FieldCellCoordinate getFieldCellCoordinate() {
        return fieldCellCoordinate;
    }

    public static int getMinXCell(ArrayList<FieldCell> cells) {//похожий код, можно подумать о рефакторинге
        int  x = 11;
        for (FieldCell cell: cells) {
            if (cell.getFieldCellCoordinate().getX() < x){x = cell.getFieldCellCoordinate().getX();}
        }
        return x;
    }

    public static int getMaxXCell(ArrayList<FieldCell> cells) {//похожий код, можно подумать о рефакторинге
        int  x = 0;
        for (FieldCell cell: cells){
            if (cell.getFieldCellCoordinate().getX() > x){x = cell.getFieldCellCoordinate().getX();}
        }
        return x;
    }

    public static int getMinYCell(ArrayList<FieldCell> cells) {//похожий код, можно подумать о рефакторинге
        int  y = 11;
        for (FieldCell cell: cells){
            if (cell.getFieldCellCoordinate().getY() < y){y = cell.getFieldCellCoordinate().getY();}
        }
        return y;
    }

    public static int getMaxYCell(ArrayList<FieldCell> cells) {//похожий код, можно подумать о рефакторинге
        int  y = 0;
        for (FieldCell cell: cells){
            if (cell.getFieldCellCoordinate().getY() > y){y = cell.getFieldCellCoordinate().getY();}
        }
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        FieldCell fieldCell = (FieldCell) obj;
        if ((fieldCell.fieldCellCoordinate == this.fieldCellCoordinate)){
            return true;
        } else {
            return false;
        }
    }
}