package ru.javabit.report;

public class ConsoleDialogue implements UserDialogue  {

    public void makeReport(String s) {
        System.out.println(s);
    }
}