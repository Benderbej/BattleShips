package ru.javabit.gameField;

public class Party {

    private String name;
    private GameFieldGrid grid;

    public Party(String name, int rowNum, int colNum){
        this.name = name;
        grid = new GameFieldGrid(rowNum, colNum);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameFieldGrid getGrid() {
        return grid;
    }

    public void setGrid(GameFieldGrid grid) {
        this.grid = grid;
    }
}
