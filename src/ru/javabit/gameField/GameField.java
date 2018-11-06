package ru.javabit.gameField;

import java.util.ArrayList;

/*
Кратские описания классов

GameField - класс содержит все объекты касающиеся игрового поля - пока - два объекта  игровых сеток, сетка со своими кораблями и сетка с кораблями противника
GameFieldGrid - класс части игрового поля, сетка с кораблями
FieldCell - клетка поля игрового, абстрактный класс
после рефакторинга обзавелся вспомогательными  статичскими методами, для реализации алгоритма предотвращения размещения кораблей вплотную друг к другу,
FieldCellCoordinate - координата, содержит x и y
FieldCellFactory - фабрика, - выстраивает клетки игрового поля(GameFieldCell) и клетки обозачений(MetaFieldCell)
GameFieldCell - клетка, содержит координату и статус клетки(корабль, поврежденный корабль, пограничная клетка, вода, и т д.)
MataFieldCell - клетка, содержит номер или букву столбца
GameFieldRulers - вспомогательный класс для построения разметки

ConsoleDialogue - класс для работы с консолью
Report - перечисление, содержит сообщения для вывода в консаль или на экран

BattleCruiser, Boat, Cruiser, Destroyer - классы кораблей, пока содержать только размер и ориентацию(вертикальную или горизонтальную - это необходимо при реализации автоматической расстановки кораблей на игровом поле)
Fleet -класс содержащий набор кораблей
FleetAutoDisposer - класс осуществляет автоматическую расстановку кораблей на поле, случайным образом, учитывает отступы от кораблей, чтобы не располагать их вплотную
FleetDisposable - интерфейс, который описывает ручную и автоматическую расстановку
Ship - абстрактный класс корабля
ShipPosition - перечисление описывает расположение корабля(вертикальное, горизонтальное(для удобочитаемости кода, чтобы не заменять цифрами, есть также unar - для одноклеточных кораблей)

CellState - перечисление, содержит строки различных эле chckoutментов для отображения в консоли
GemeFieldRenderer - производит отображение игры в консоль

 */






public class GameField {

    private GameFieldGrid playerFieldGrid;
    private GameFieldGrid enemyFieldGrid;
    private int columnNum;
    private int rowNum;

    public GameField(int rowNum, int columnNum){
        this.columnNum = columnNum;
        this.rowNum = rowNum;
        playerFieldGrid = new GameFieldGrid(rowNum, columnNum);
        enemyFieldGrid = new GameFieldGrid(rowNum, columnNum);
    }

    public GameFieldGrid getPlayerFieldGrid() {
        return playerFieldGrid;
    }

    public GameFieldGrid getEnemyFieldGrid() {
        return enemyFieldGrid;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public int getRowNum() {
        return rowNum;
    }
}