package ru.javabit;

import ru.javabit.report.ConsoleDialogue;
import ru.javabit.report.Report;
import ru.javabit.gameField.GameField;
import ru.javabit.report.UserDialogue;
import ru.javabit.ship.FleetsDisposal;
import ru.javabit.view.GameFieldRenderer;
import ru.javabit.ship.Fleet;

public class Game {

    private static Game game;
    UserDialogue dialogue;
    GameField gameField;
    FleetsDisposal fleetsDisposal;
    GameFieldRenderer gameFieldRenderer;
    Fleet fleet1;
    Fleet fleet2;
    TurnMaster turnMaster;

    private Game(){
        initGame();
    }

    public static Game getInstance(){
        if(game == null){
            game = new Game();
        }
        return game;
    }

    private void initGame(){
        meetUser();
        gameField = new GameField(11, 11,"computer 1", "computer 2");
        fleet1 = new Fleet();
        fleet2 = new Fleet();
        fleetsDisposal = new FleetsDisposal(gameField, fleet1, fleet2);
        fleetsDisposal.disposeAutoAuto();
        gameFieldRenderer = new GameFieldRenderer(gameField);
        gameFieldRenderer.renderGameField();
    }

    public void startGame(){//todo init gameprocess thread??
        turnMaster = TurnMaster.getInstance();
        turnMaster.initComputerVsComputer(gameField, "computer 1", "computer 2");
        turnMaster.startTurning();
    }

    private void meetUser(){
        dialogue = new ConsoleDialogue();
        dialogue.makeReport(Report.Greeting.getText());
    }

}

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