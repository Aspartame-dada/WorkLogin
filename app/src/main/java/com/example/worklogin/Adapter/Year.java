package com.example.worklogin.Adapter;

public class Year {
    private int year;
    private String totalTime;

    public Year(int year, String  totalTime) {
        this.year = year;
        this.totalTime = totalTime;
    }

    public String  getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String  totalTime) {
        this.totalTime = totalTime;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
