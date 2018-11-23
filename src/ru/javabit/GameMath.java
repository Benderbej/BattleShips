package ru.javabit;

import ru.javabit.gameField.FieldCell;

import java.util.ArrayList;
import java.util.Random;

public class GameMath {

    private static Random random = new Random();

    public static int getRandomInt(int size) {
        int id = random.nextInt(size);
        return id;
    }

    public static FieldCell getFromPossiblePosotionsList(ArrayList<FieldCell> possiblePosotionsList) {
        return possiblePosotionsList.get(random.nextInt(possiblePosotionsList.size()));
    }

    public static boolean checkNotOutOfBounds(int x, int y, int maxX, int maxY) {
        if((0 < x && x < maxX)&&(0 < y && y < maxY)) {return true;}
        return false;
    }

    public static Random getRandom() {
        return random;
    }
}
