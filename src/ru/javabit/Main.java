package ru.javabit;

/*


NB! алгоритм расстановки кораблей получился слишком сложен, и разделив его на класс корабля и класс растановщика выяснилось,
что слишком много есть общей логики расстановки для всех кораблей, то есть
оказалось что много конкретной логики может содержать класс "корабль" и корабль таким образом не может быть интерфейсом.
Можно попробовать создать интерфейс Disposable например, который будет описывать только ту логику
в кораблях, которай касается их расстановки(но пока это 90% всей их логики)
Тогда, если абстрактный класс "корабль" будет имплементировать Disposable,- на игровую карту можно будет размещать не только "корабли",
но и делать какие-то сложные интересные карты, например, с береговыми объектами и т.д.

Задумка также была обыграть интерфейсы на примере FleetDisposer - AutoDisposer и MunualDisposer
авто и ручной расстановщик по-разному бы выполняли disposeFleet() интерфейса FleetDisposer
но до ручного я пока не добрался, закопавшись в логике растановки и логике ходов
получился недо-овер-инжиниринг =/
хотел сделать наворочено, и забыл про идею урока - интерфейсы!!! в итоге тему не раскрыл. Надеюсь получится на следующих примерах!
 *

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
Report - перечисление, содержит сообщения для вывода в консоль или на экран(при другой реализации GameFieldRenderable)

BattleCruiser, Boat, Cruiser, Destroyer - классы кораблей, пока содержать только размер и ориентацию(вертикальную или горизонтальную - это необходимо при реализации автоматической расстановки кораблей на игровом поле)
Fleet -класс содержащий набор кораблей
FleetsDisposal - класс который вызывает расстановщики(авто или ручные)
FleetAutoDisposer - авторасстановщик, класс осуществляет автоматическую расстановку кораблей на поле, случайным образом, учитывает отступы от кораблей, чтобы не располагать их вплотную, класс оперирует основными данными - массивами FieldCells[][]
FleetDisposer - интерфейс, который описывает ручную и автоматическую расстановку
GameMath - контейнер для Random + статические вспомогательные методы
Ship - абстрактный класс корабля, класс содержат также часть логикипо расстановке кораблей(связан с Disposer,(хотя связь минимизирована))
ShipPosition - перечисление описывает расположение корабля(вертикальное, горизонтальное(для удобочитаемости кода, чтобы не заменять цифрами, есть также unar - для одноклеточных кораблей)

CellState - перечисление, содержит строки различных эле chckoutментов для отображения в консоли
GemeFieldRenderer - производит отображение игры в консоль

TurnActor - класс - агент осуществляющий ход(пользователь или комп)
TurnMaster - класс запускающий процесс игры(ходов)

PlayerComputerAI - класс содержащий логику компьютерного игрока(список вариантов для атаки, методы по выбору клетки для атаки)
 */

import ru.javabit.exceptions.BattleShipsException;

public class Main {

    public static void main(String[] args) throws InterruptedException, BattleShipsException {
        Game singleGame = SingleGame.getInstance();
        singleGame.initGame();
        singleGame.startGame();
    }


    /*
    Написать программу (начальный прототип Морского Боя), которая будет:

1. Приветствовать игрока (выводя сообщения консоль).
2. Запрашивать его имя/ник
3. Содержать константы, которые в будущем могут пригодиться для игры (количество кораблей одного типа и другого и третьего, общее количество кораблей и т.д.)
4. Разработать отдельный класс для корабля, у которого есть поля класса специфичные для кораблей (тип корабля, координаты, если нужны)
5. Создать нужное для морского боя количество объектов-кораблей и вывести сообщение о том, что корабли созданы.
6. Вывести сообщение о победителе (пока в качестве заглушки можно использовать функцию для генерирования случайных чисел, или же выбирать всегда одного победителя, если пока не знаете, как случайные числа получать)
7. Помним о правилах по оформлению кода.
    Второй шаг:
    Программа должна вывести на экран в текстовом виде поле игрока и поле компьютера.
    Примерно в таком виде:
    А B C D ...
            1 | | | | |
            2 | | | | |
            3 | | | | |
    Доп. задание:
    Написать метод (подумайте, кому он должен принадлежать, и кто его будет вызывать) по добавлению всех кораблей на поле случайным образом, даже если они пересекаются друг с другом.
    Учтите план на будущее: возможно когда-то потом потребуется реализовать еще и метод по ручному добавлению кораблей (сам этот метод пока делать не нужно).
    Серьезное усложнение: а теперь добавьте проверку на то, чтобы корабли не пересекались при добавлении на поле, то есть корабли не могут располагаться вплотную друг к другу.


    Доп. задание
    Реализуйте переключатель в программе, между автоматическим размещением кораблей и ручным.
    Автоматическая расстановка жизненно необходима для тестирования всей логики работы программы,
    ручная же расстановка является всего лишь дополнительной опцией.
    Ручную расстановку лучше пока не делать, так как текстом это вводить очень долго в консоли,
    а в графическом интерфейсе придется все равно переделывать.
    Реализуйте метод автоматической стрельбы: как для пользователя, так и для компьютера — ничего не запрашивает,
    просто стреляет, выводит результат и обновленное поле.
    Метод ручной стрельбы тоже не имеет большого смысла делать в консольном варианте,
    лучше сразу в графическом. Но если хотите, попробуйте.
    Задание со звездочкой: так как в дальнейшем планируется перенос игры с текстового движка на графический,
    поэтому постарайтесь разделить логику программы и визуальное отображение.
    В этом вам поможет знание интерфейсов и абстрактных классов.
    Для этого создайте интерфейс (или абстрактный класс),
    который бы содержал в себе все методы вывода чего-либо вообще на экран.
    В самой логике программы не должно остаться вызовов System.out.println() вообще.
    Вместо такого вывода вы должны вызывать соответствующие методы из экземпляра, реализующего этот интерфейс
    (абстрактный класс). Тем самым все классы, которые у вас отвечают за логику работы программы станут тем,
     что называется Моделью в паттерне Model-View-Controller. https://ru.wikipedia.org/wiki/Model-View-Controlle...
    */
}