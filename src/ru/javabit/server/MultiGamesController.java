package ru.javabit.server;

/*
класс тесно связанный с GameServer только если сервер создает clienhandler-ы и управляет соединениями,
то MultiGamesController создает комнаты, стартует игры и содержит все игры на стороне сервера)то есть это контроллер+модель, больше модель
 */

import java.util.ArrayList;

public class MultiGamesController {

    private static MultiGamesController multiGamesController;


    MultiGamesController(){

    }

    public static MultiGamesController getInstance() {
        if(multiGamesController == null){
            multiGamesController = new MultiGamesController();
        }
        return multiGamesController;
    }






}
