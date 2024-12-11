package entity;

import entity.Enum.QuestionLevel;

public class DataToDrawPieChart {
    private int number;
    private QuestionLevel level;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public QuestionLevel getLevel() {
        return level;
    }

    public void setLevel(QuestionLevel level) {
        this.level = level;
    }
}
