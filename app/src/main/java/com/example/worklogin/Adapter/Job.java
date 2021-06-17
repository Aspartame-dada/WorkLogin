package com.example.worklogin.Adapter;

/**
 * date：2021/6/16 on 14:24
 * author: 老鬼
 * e-mail: 3625988453@qq.com
 * description:
 */
public class Job {
    private String JobSite;
    private int year;
    private  int month;
    private  int day;
    private int hour;
    private int minute;
    private boolean isOdd;

    public Job() {
    }

    public Job(String jobSite, int year, int month, int day, int hour, int minute, boolean isOdd) {
        JobSite = jobSite;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.isOdd = isOdd;
    }

    public String getJobSite() {
        return JobSite;
    }

    public void setJobSite(String jobSite) {
        JobSite = jobSite;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isOdd() {
        return isOdd;
    }

    public void setOdd(boolean odd) {
        isOdd = odd;
    }
}
