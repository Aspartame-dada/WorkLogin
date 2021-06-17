package com.example.worklogin.Fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.worklogin.Adapter.Job;
import com.example.worklogin.Adapter.Month;
import com.example.worklogin.Adapter.MonthAdapter;
import com.example.worklogin.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

public class Fragment_month extends Fragment  implements MonthAdapter.OnItemClickListener{
    int year;
    SQLiteDatabase db;
    private ImageView btnBackMonth;
    private RecyclerView tvMonth;
    List<Month> list;
    MonthAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        year = getArguments().getInt("Year");
        db = openOrCreateDatabase(getActivity().getFilesDir().toString() + "/test.db", null);
        db.execSQL("DROP TABLE IF EXISTS DueTable");
        db.execSQL("create table if not exists DueTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "JobSite " +
                "VARCHAR,year INTEGER, month INTEGER,day INTEGER,hour INTEGER,minute " +
                "INTEGER,isOdd BOOLEAN)");
        initView(view);
        new Thread(new Runnable() {
            @Override
            public void run() {

                GenerateWorkData(2020,4);

                // 这边这个post 相当于handler， 可以简单的回到主线程
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        initdata();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

        return view;
    }

    private void initView(View view) {
        btnBackMonth = (ImageView) view.findViewById(R.id.btn_back_month);
        tvMonth = (RecyclerView) view.findViewById(R.id.rv_day);
        list=new ArrayList<>();
        adapter=new MonthAdapter(getContext(),list,this);
        tvMonth.setAdapter(adapter);
        tvMonth.setLayoutManager(new LinearLayoutManager(this.getContext()));

        btnBackMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });
    }
private void initdata(){
    Log.i(TAG, "initDta: ");
    int monthNow =1;
    int totalHour=0;
    int totalMinute=0;
for (int i=0;i<12;i++){


        @SuppressLint("Recycle")
        Cursor cursor = db.query("DueTable", new String[]{"_id,JobSite,year,month, hour,minute," +
                        "isOdd"},
                "year=? AND month=?", new String[]{String.valueOf(year),String.valueOf(monthNow)}, null,
                null, null);

                cursor.moveToFirst();
             if (cursor.moveToNext() != true) {
            Log.i(TAG, "initDta: monthNow 填充失败 "+monthNow);
            break;

        } else {
            while (cursor.moveToNext() ){
            monthNow=Integer.parseInt(cursor.getString(cursor.getColumnIndex("month")));
                   totalHour += Integer.parseInt(cursor.getString(cursor.getColumnIndex("hour")));
                   totalMinute += Integer.parseInt(cursor.getString(cursor.getColumnIndex("minute")));
               }}

               int a = totalMinute;
               totalHour = totalHour + (totalMinute/60);
               totalMinute=a%60;
               list.add(new Month(getMonth(monthNow), year+"  "+totalHour + ":" + totalMinute));
               monthNow++;
             cursor.close();

           }


            Log.i(TAG, "initDta: monthNow"+monthNow);
            Log.i(TAG, "initDta: totalHour"+totalHour);
            Log.i(TAG, "initDta: totalMinute"+totalMinute);
            monthNow++;
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

                        Job job1=new Job(site,calendarIn.get(1),calendarIn.get(4),
                                calendarIn.get(5),hours,minutes,isOdd);
                        db.execSQL("INSERT INTO DueTable VALUES (NULL, ?, ?,?,?,?,?,?)",
                                new Object[]{
                                        job1.getJobSite(),job1.getYear(),month,
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
            case 0:
                bundle.putInt("Month",1);
                bundle.putInt("Year",year);

                Navigation.findNavController(getView())
                        .navigate(R.id.action_thirdFragment_month_to_thirdFragment_day, bundle);
                break;
            case 1:
                bundle.putInt("Month",2);
                bundle.putInt("Year",year);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_thirdFragment_month_to_thirdFragment_day, bundle);
                break;
            case 2:
                bundle.putInt("Month",3);

                bundle.putInt("Year",year);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_thirdFragment_month_to_thirdFragment_day, bundle);
                break;
            case 3:
                bundle.putInt("Month",4);
                bundle.putInt("Year",year);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_thirdFragment_month_to_thirdFragment_day, bundle);
                break;
            case 4:
                bundle.putInt("Month",5);
                bundle.putInt("Year",year);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_thirdFragment_month_to_thirdFragment_day, bundle);
                break;
            case 5:
                bundle.putInt("Month",6);
                bundle.putInt("Year",year);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_thirdFragment_month_to_thirdFragment_day, bundle);
                break;
            case 6:
                bundle.putInt("Month",7);
                bundle.putInt("Year",year);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_thirdFragment_month_to_thirdFragment_day, bundle);
                break;
            case 7:
                bundle.putInt("Month",8);
                bundle.putInt("Year",year);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_thirdFragment_month_to_thirdFragment_day, bundle);
                break;
            case 8:
                bundle.putInt("Month",9);
                bundle.putInt("Year",year);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_thirdFragment_month_to_thirdFragment_day, bundle);
                break;
            case 9:
                bundle.putInt("Month",10);
                bundle.putInt("Year",year);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_thirdFragment_month_to_thirdFragment_day, bundle);
                break;
            case 10:
                bundle.putInt("Month",11);
                bundle.putInt("Year",year);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_thirdFragment_month_to_thirdFragment_day, bundle);
                break;
            case 11:
                bundle.putInt("Month",12);
                bundle.putInt("Year",year);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_thirdFragment_month_to_thirdFragment_day, bundle);
                break;
            default:

                bundle.putInt("Month",12);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_thirdFragment_month_to_thirdFragment_day, bundle);
                break;

    }
}
    public String getMonth(int i) {
        String month=" ";
        if(i==1){
            month="JANUARY";
        }
        if(i==2){
            month="FEBRUARY";
        }
        if(i==3){
            month="MARCH";
        }  if(i==4){
            month="APRIL";
        }  if(i==5){
            month="MAY";
        }  if(i==6){
            month="JUNE";
        }  if(i==7){
            month="JULY";
        }  if(i==8){
            month="AUGUST";
        }  if(i==9){
            month="SEPTEMBER";
        }  if(i==10){
            month="OCTOBER";
        }  if(i==11){
            month="NOVEMBER";
        }  if(i==12){
            month="DECEMBER";
        }
        return month;
    }
}