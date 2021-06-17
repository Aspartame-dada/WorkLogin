package com.example.worklogin.Adapter;

public class Month {
    private String month;
    private String totaltimes;

    public Month(String month, String totaltimes) {
        this.month = month;
        this.totaltimes = totaltimes;
    }

    public String getMonth() {
     if(month.equals(1)){
         month="JANUARY";
     }
        if(month.equals(2)){
            month="FEBRUARY";
        }
        if(month.equals(3)){
            month="MARCH";
        }  if(month.equals(4)){
            month="APRIL";
        }  if(month.equals(5)){
            month="MAY";
        }  if(month.equals(6)){
            month="JUNE";
        }  if(month.equals(7)){
            month="JULY";
        }  if(month.equals(8)){
            month="AUGUST";
        }  if(month.equals(9)){
            month="SEPTEMBER";
        }  if(month.equals(10)){
            month="OCTOBER";
        }  if(month.equals(11)){
            month="NOVEMBER";
        }  if(month.equals(12)){
            month="DECEMBER";
        }
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotaltimes() {
        return totaltimes;
    }

    public void setTotaltimes(String totaltimes) {
        this.totaltimes = totaltimes;
    }
}
