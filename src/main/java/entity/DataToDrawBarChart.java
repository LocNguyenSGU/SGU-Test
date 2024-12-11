package entity;

import java.time.LocalDate;

public class DataToDrawBarChart {
    private LocalDate dateStart;

    private double number;

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }


    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }
}
