package com.example.worklogin.Fragment;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.worklogin.Adapter.Day;
import com.example.worklogin.Adapter.DayAdapter;
import com.example.worklogin.Adapter.Job;
import com.example.worklogin.Adapter.Month;
import com.example.worklogin.Adapter.MonthAdapter;
import com.example.worklogin.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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


public class Fragment_day extends Fragment implements DayAdapter.OnItemClickListener{

    private ImageView mBtnBackDay;
    SQLiteDatabase db;
    Bitmap  bitmap,bitmap2;
    List<Day> list;
    DayAdapter adapter;
    private ImageView btnBackDay;
    private RecyclerView rvDay;
    int monthNow,yearNow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        monthNow = getArguments().getInt("Month");
        yearNow = getArguments().getInt("Year");

        bitmap=  getBitmapFromURL("https://pics.freeicons.io/uploads/icons/png/3691415891537355433" +
                "-512.png");
        initView(view);
        return view;
    }
    public  Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
    public void getImage(){


    }

    private void initView(View view) {
        Log.i(TAG, "initView:    ");
        mBtnBackDay = (ImageView) view.findViewById(R.id.btn_back_day);
        mBtnBackDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });
        rvDay = (RecyclerView) view.findViewById(R.id.rv_day);
        list=new ArrayList<>();
        adapter=new DayAdapter(getContext(),list,this);
        rvDay.setAdapter(adapter);
        rvDay.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }




    @Override
    public void onItemClick(int position) {

    }
}