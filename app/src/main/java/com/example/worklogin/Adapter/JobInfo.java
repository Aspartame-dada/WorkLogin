package com.example.worklogin.Adapter;

import java.io.Serializable;

public class JobInfo implements Serializable {
    private String nameOfJob;
    private String offHours;
    private String date;
    private String timeHours;
    public JobInfo(){

    }

    public JobInfo(String nameOfJob,String date,String timeHours,String offHours){
        this.nameOfJob=nameOfJob;
        this.offHours=offHours;
        this.date=date;
        this.timeHours=timeHours;

    }
    public JobInfo(String date,String timeHours){
        this.date=date;
        this.timeHours=timeHours;

    }

    public String getNameOfJob() {
        return nameOfJob;
    }

    public void setNameOfJob(String nameOfJob) {
        this.nameOfJob = nameOfJob;
    }

    public String isOffHours(boolean b) {
        if(b){
            return "odd hours";
        }
        return "regular hours";
    }

    public void setOffHours(String offHours) {
        this.offHours = offHours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeHours() {
        return timeHours;
    }

    public void setTimeHours(String timeHours) {
        this.timeHours = timeHours;
    }

    public String ifOffHours(boolean b) {
        return "odd hours";
    }
}
