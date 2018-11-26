package ru.javabit.report;

public enum Report {

    //TODO заменить на реализацию коллекцией

    Greeting("Приветствуем! Добро пожаловать в игру BattleShips!"), Finishing(" Спасибо за игру!"), AskingNAme(" Можно поинтересоваться как вас зовут?");

    String text;

    Report(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
