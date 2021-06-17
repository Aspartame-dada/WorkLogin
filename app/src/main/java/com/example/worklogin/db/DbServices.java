package com.example.worklogin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbServices extends SQLiteOpenHelper {

    public final static int version = 1;
    public final static String dbName = "Test";

    public DbServices(Context context){
        super(context,dbName,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.beginTransaction();
        //创建邮件表
        String create_mail_sql = "CREATE TABLE if not exists [Test]"+
                "(_id integer primary key autoincrement,job text)";
        db.execSQL(create_mail_sql);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
