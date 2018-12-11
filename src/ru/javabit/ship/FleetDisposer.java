package ru.javabit.ship;

import ru.javabit.exceptions.BattleShipsException;
import ru.javabit.gameField.FieldCell;
import ru.javabit.gameField.FieldCellCoordinate;
import ru.javabit.gameField.GameFieldCell;

import java.util.ArrayList;

/**
 * FleetAutoDisposer
 * FleetManualDisposer - TODO
 */

public interface FleetDisposer {

    void disposeFleet(ArrayList<Ship> shipList) throws BattleShipsException; //получает флот и располагает корабли флота на игровом поле

    ArrayList<FieldCell> findPossiblePositionsForCell(FieldCell fieldCell);//ищет соседнюю координату определяет чтобы не было выхода за границы

    int getRowNum();//получить количество рядов поля

    int getColumnNum();//получить количество колонок поля

    FieldCell[][] getFieldCells();//получить массив с игровыми клетками

    void makeAllReservedCellsFreewater();//все клетки со статусом - зарезервировано(то есть пограничные клетки кораблей) меняет на статус -вода

    void maskReservedArea(ArrayList<FieldCellCoordinate> resFieldCellCoords);//присваивает клетке статус зарезервировано

    GameFieldCell getRandomPositiveCell();//получить ЛЮБУЮ случайную клетку поля(хоть с кораблем хоть без, лишь бы в рамках границ)

    GameFieldCell getRandomCell();//получить случайную клетку поля для данной имплементации(то есть для перельманова алгоритма это будут все прибрежные клетки (получается из подмножества того что дает getRandomPositiveCell()))
}