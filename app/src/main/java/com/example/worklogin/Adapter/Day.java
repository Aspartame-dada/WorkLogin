package com.example.worklogin.Adapter;

import android.graphics.Bitmap;

public class Day {
    private int bitmap;
    private String name;


    public Day(int bitmap, String name, String time) {
        this.bitmap = bitmap;
        this.name = name;
    }

    public Day(int bitmap, String s) {
        this.bitmap = bitmap;
        this.name = name;
    }


    public int getBitmap() {
        return bitmap;
    }

    public void setBitmap(int bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
