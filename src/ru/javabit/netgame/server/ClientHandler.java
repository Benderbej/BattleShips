package ru.javabit.netgame.server;

import ru.javabit.GameMath;

public class ClientHandler {

    private int clientServantId;

    ClientHandler (){
        setClientServantId();
        System.out.println(getClientServantId());
    }

    public int getClientServantId() {
        return clientServantId;
    }

    public void setClientServantId() {
        clientServantId = GameMath.getRandomInt(1, Integer.MAX_VALUE);
    }
}