package com.example.worklogin.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.worklogin.Adapter.JobInfo;
import com.example.worklogin.db.Dbhelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class JobServices {
    Context context;
    public JobServices(Context context){
        this.context=context;
    }

    public void saveObject(JobInfo jobInfo) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(jobInfo);
            objectOutputStream.flush();
            byte data[] = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();
            Dbhelper dbhelper = Dbhelper.getInstens(context);
            SQLiteDatabase database = dbhelper.getWritableDatabase();
            database.execSQL("insert into classtable (classtabledata) values(?)", new Object[] { data });
            database.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public JobInfo getObject() {
        JobInfo jobInfo = null;
        Dbhelper dbhelper = Dbhelper.getInstens(context);
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from classtable", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                byte data[] = cursor.getBlob(cursor.getColumnIndex("classtabledata"));
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
                    jobInfo = (JobInfo) inputStream.readObject();
                    inputStream.close();
                    arrayInputStream.close();
                    break;//这里为了测试就取一个数据
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        return jobInfo;

    }

}
