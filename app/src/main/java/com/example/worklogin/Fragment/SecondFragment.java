package com.example.worklogin.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.example.worklogin.Adapter.Job;
import com.example.worklogin.Adapter.Year;
import com.example.worklogin.Adapter.YearAdapter;
import com.example.worklogin.R;

import java.io.Console;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class SecondFragment extends Fragment implements YearAdapter.OnItemClickListener{
private YearAdapter adapter;
private List<Year> list;
private  RecyclerView mRvYear;
    SQLiteDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        db = openOrCreateDatabase(getActivity().getFilesDir().toString()+ "/test.db", null);
        db.execSQL("DROP TABLE IF EXISTS DueTable");
        db.execSQL("create table if not exists DueTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "JobSite " +
                "VARCHAR,year INTEGER, month INTEGER,day INTEGER,hour INTEGER,minute " +
                "INTEGER,isOdd BOOLEAN)");
        initView(view);
        // 这边是开线程跑的
        new Thread(new Runnable() {
            @Override
            public void run() {

                GenerateWorkData(2020,4);

                // 这边这个post 相当于handler， 可以简单的回到主线程
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        initDta();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

        // 这个地方的init会先走，你生成数据的还没走完这个就走完了
//        initDta();
        Log.i(TAG, "onCreateView: 来了"+list.size());

        for ( int  i =  0 ;i < list.size();i++){
            Log.i(TAG, "onCreateView: 进来了");
            Log.i(TAG, "onCreateView: "+(list.get(i).getYear()));
            Log.i(TAG, "onCreateView: "+(list.get(i).getTotalTime()));


        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: 来了"+list.size());

        for ( int  i =  0 ;i < list.size();i++){
            Log.i(TAG, "onResume: 进来了");
            Log.i(TAG, "onResume "+(list.get(i).getYear()));
            Log.i(TAG, "onResume "+(list.get(i).getTotalTime()));


        }
    }

    private void initView(View view) {
        mRvYear = (RecyclerView) view.findViewById(R.id.rv_year);
        list=new ArrayList<>();
        adapter=new YearAdapter(getContext(),list,this);
        mRvYear.setAdapter(adapter);
        mRvYear.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }
    private void initDta(){
        Log.i(TAG, "initDta: ");
        int yearNow =2021;
        int totalHour=0;
        int totalMinute=0;
        for(int i=2021;i<2024;i++) {
            totalHour=0;
            totalMinute=0;
            @SuppressLint("Recycle")
            Cursor cursor = db.rawQuery("select year,hour,minute from DueTable where year=? ",
                    new String[]{String.valueOf(yearNow)});
            if (cursor.moveToNext() != true) {
                Log.i(TAG, "initDta: yearnow 填充失败 "+yearNow);

            } else {
                while (cursor.moveToNext()) {
                    totalHour += Integer.parseInt(cursor.getString(1));
                    totalMinute += Integer.parseInt(cursor.getString(2));
                }
                Log.i(TAG, "initDta: yearnow"+yearNow);
                Log.i(TAG, "initDta: totalHour"+totalHour);
                Log.i(TAG, "initDta: totalMinute"+totalMinute);




                yearNow++;

                cursor.close();
            }
            int b=0;
            int a = totalMinute;
            totalHour = totalHour + (totalMinute/60);
            totalMinute=a%60;
            list.add(new Year(yearNow-1, totalHour + ":" + totalMinute));
            Log.i(TAG, "initDta: yearnow"+yearNow);
            Log.i(TAG, "initDta: totalHour"+totalHour);
            Log.i(TAG, "initDta: totalMinute"+totalMinute);
        }

//            Cursor cursor = db.query("DueTable", new String[]{"JobSite,year,month,day,hour,minute,isOdd"},
//                    "id=?", new String[]{String.valueOf(yearNow)}, null, null, null);
//            cursor.moveToFirst();
//            if (cursor.moveToPosition(0) != true) {
//                Log.e("TAG", "moveToPosition return fails, maybe table not created!!!");
//
//            }else {
//                if(cursor.getString(cursor.getColumnIndex("year")).equals(String.valueOf(yearNow))){
//
//                    totalHour+=Integer.parseInt(cursor.getString(cursor.getColumnIndex("hour")));
//                    totalMinute+=Integer.parseInt(cursor.getString(cursor.getColumnIndex("minute")));
//
//
//                }else {
//                            yearNow++;
//
//                }
//                Log.i(TAG, "initDta: year"+yearNow);
//                Log.i(TAG, "initDta: "+(totalHour+":"+totalMinute));
//                list.add(new Year(yearNow,totalHour+":"+totalMinute));
//
//
            }




        public void GenerateWorkData(int startYear, int numYears) {

            Random rng = new Random(34);
            String[] sites = { "Walmart", "ACE", "Miami", "Dubois", "Chipolte" };

            for (int dy = 0; dy < numYears; dy++) {
                int year = startYear + dy;
                for (int month = 1; month <= 12; month++) {
                    int N = 3;
                    if (month == 1 || year == 2021)
                        N = 1;
                    Calendar calendarOut = new GregorianCalendar();
                    calendarOut.set(year, month + 1, 1);

                    for (int day = 1; day <= calendarOut.getActualMaximum(Calendar.DATE); day++) {
                        int numJobs = rng.nextInt(N) + 1;
                        Calendar calendarIn = new GregorianCalendar();
                        calendarIn.set(year, month + 1, day + 1);
                        //周六 周日
                        if (calendarIn.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendarIn.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                            numJobs = rng.nextInt(5) == 0 ? 1 : 0;
                        for (int job = 0; job < numJobs; job++) {
                            int hours = rng.nextInt(8);
                            int minutes = rng.nextInt(4) * 15;
                            String site = sites[rng.nextInt(sites.length)];
                            boolean isOdd = rng.nextInt(5) == 0
                                    || (calendarIn.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendarIn.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);

                            Job job1=new Job(site,calendarIn.get(1),month,
                                    calendarIn.get(5),hours,minutes,isOdd);
                            db.execSQL("INSERT INTO DueTable VALUES (NULL, ?, ?,?,?,?,?,?)",
                                    new Object[]{
                                    job1.getJobSite(),job1.getYear(),job1.getMonth(),
                                    job1.getDay(),job1.getHour(),job1.getMinute(),job1.isOdd()
                            });




//                            // Don’t print, but put it into a list (or DB).
//                            System.out.println("Job site: " + site);
//                            System.out.println("Date of job: " + calendarToString(calendarIn));
//
//                            System.out.println("Time worked: " + hours + ":" + minutes);
//                            System.out.println("Odd hours?: " + isOdd);
                        }
                    }
                }
                // System.out.println(ts);
            }
            Log.i("TAG", "GenerateWorkData: 填充成功");
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        switch (position){
            case 1:
                bundle.putInt("Year",2022);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_secondFragment_to_thirdFragment_month, bundle);
                break;
            case 0:
                bundle.putInt("Year",2021);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_secondFragment_to_thirdFragment_month, bundle);
                break;
            default:

                bundle.putInt("Year",2023);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_secondFragment_to_thirdFragment_month, bundle);
                break;
        }





    }
}

