package com.example.autoschool11.core.data.local.entities;

// класс для создания графика интенсивности
public class IntensityClass {
    String date;
    int result;

    public IntensityClass(String date, int result) {
        this.date = date;
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
