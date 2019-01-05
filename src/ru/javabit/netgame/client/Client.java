package ru.javabit.netgame.client;

import java.io.IOException;
import java.net.Socket;

public class Client {

    String site;
    String port;

    Client(){
        site = "localhost";
        port = "8082";
    }

    private void meet() throws IOException {
        Socket socket = new Socket(site, Integer.parseInt(port));
    }

    private void takeId(){}

    private void takeTurn(){}

    private void giveGameField(){}

    private void sendRequestCode(int code){

    }

    private void sendRequest(int code){

    }
}