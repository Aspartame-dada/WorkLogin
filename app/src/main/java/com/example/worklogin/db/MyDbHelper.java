package com.example.worklogin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


//SQLiteOpenHelper子类
public class MyDbHelper extends SQLiteOpenHelper {

    //构造函数
    public MyDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        // context：上下文对象
        // name：数据库的名称
        // factory：允许查询数据时返回一个自定义Cursor，一般传入null
        // version：数据库的版本号，可用于对数据库进行升级操作
        super(context, name, factory, version);
    }

    //第一次打开时创建数据表
    @Override
    public void onCreate(SQLiteDatabase db) {
        //在数据库中打开或创建数据表users，表中有两列："name"和"pswd"，这两列的数据类型为varchar(50)
        //varchar是一种可变长度的字符串，varchar(50)表示该字符串最长不超过50字符
        db.execSQL("create table if not exists users(name varchar(50),pswd varchar(50),primary key(name))");
    }

    //版本更新时被调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}


