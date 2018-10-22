package ru.javabit.gameField;

import java.util.ArrayList;

public class GameFieldRulers {

    private char[] alphabet = "aabcdefghijklmnopqrstuvwxyz".toCharArray();
    private ArrayList<String> fieldRowsSkins = new ArrayList<>();
    private ArrayList<String> fieldColsSkins = new ArrayList<>();

    public GameFieldRulers(int colNum, int rowNum){
        fieldRowsSkins = new ArrayList<>();
        fieldColsSkins = new ArrayList<>();
        setFieldColsSkins(colNum);
        setFieldRowsSkins(rowNum);
        //System.out.println("setFieldRowsSkins"+fieldRowsSkins.get(5));
    }

    public void setFieldRowsSkins(int colNum) {
        for(int i =0; i < colNum; i++){
            fieldColsSkins.add(String.valueOf(alphabet[i]));
        }
    }

    public void setFieldColsSkins(int rowNum) {
        for(int i =0; i < rowNum+1; i++){
            fieldRowsSkins.add(String.valueOf(i));
        }
    }

    public String getFieldRowsSkin(int num) {
        String fieldRowsSkin = fieldRowsSkins.get(num);
        return fieldRowsSkin;
    }

    public String getFieldColsSkin(int num) {
        String fieldColsSkin = fieldColsSkins.get(num);
        return fieldColsSkin;
    }

    public ArrayList<String> getFieldRowsSkins() {
        return fieldRowsSkins;
    }

    public ArrayList<String> getGetFieldColsSkins() {
        return fieldColsSkins;
    }
}