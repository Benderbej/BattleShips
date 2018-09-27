package ru.javabit.Report;

enum Report {

    Greeting("Приветствуем! Добро пожаловать в игру BattleShips!"), Finishing(" Спасибо за игру!"), AskingNAme(" Можно поинтересоваться как вас зовут?");

    String text;

    Report(String text){
        this.text = text;
    }

}
