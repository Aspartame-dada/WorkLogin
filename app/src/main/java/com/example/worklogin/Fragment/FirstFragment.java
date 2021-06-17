package com.example.worklogin.Fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.worklogin.Adapter.Imformation;
import com.example.worklogin.Adapter.JobInfo;
import com.example.worklogin.db.MyDbHelper;
import com.example.worklogin.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FirstFragment extends Fragment implements View.OnClickListener ,
        CompoundButton.OnCheckedChangeListener, Imformation.OnListItemClickListener{

    private EditText etGetNameOfJob;
    private CheckBox checkBox;
    private EditText editTextDate;
    private EditText etTimeHours;
    private RecyclerView rvGet;
    private Button btnAdd;
    private Button btnUpdate;
    private Button btnDelete;

    private boolean checked=true;
    private ArrayList<JobInfo> list,list2,list3;
    private Imformation imformation;
    private String NameOfJob;
    private String date;
    private String hours;
    private String oddHours;
    SQLiteDatabase db;
    MyDbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        db = SQLiteDatabase.openOrCreateDatabase(getContext().getFilesDir().toString() +
                "/sqltest2.db",null);
        db.execSQL("create table if not exists job(name text ,date text,hour text " +
                "(50), oddhours text (50),primary key(name))");
        Log.i("1111", "onCreate: 创建成功");

//        @SuppressLint("Recycle")
//        Cursor cursor = db.rawQuery("select name from sqlite_master where type='table' order by name", null);
//        while(cursor.moveToNext()){
//            //
//            String name = cursor.getString(0);
//            Log.i("System.out", name);
//        }
//
//        dbHelper = new MyDbHelper(getContext(),"sqltest2.db",null,1);
//
//        db = dbHelper.getWritableDatabase();
        initView(view);
        return view;
    }

    public void initView(View view) {

        etGetNameOfJob = (EditText) view.findViewById(R.id.et_get_name_of_job);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        editTextDate = (EditText) view.findViewById(R.id.editTextDate);
        etTimeHours = (EditText) view.findViewById(R.id.et_time_hours);
        rvGet = (RecyclerView) view.findViewById(R.id.rv_get);
        btnAdd = (Button) view.findViewById(R.id.btn_add);
        btnUpdate = (Button) view.findViewById(R.id.btn_update);
        btnDelete = (Button) view.findViewById(R.id.btn_delete);
        list=new ArrayList<>();
        imformation=new Imformation(this.getContext(),list,this);
        rvGet.setAdapter(imformation);
        rvGet.setLayoutManager(new LinearLayoutManager(this.getContext()));
        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(this);

    }
    public void initData(){
        Cursor cursor = db.query("job", null,"name=?",new String[]{NameOfJob +' '},null,null,null);
        getAll(cursor);

    }
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            oddHours="odd hours";
            checked=true;
        }
        else {
            oddHours="regular hours";
            checked=false;
        }
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_add){
            NameOfJob=etGetNameOfJob.getText().toString();
            if(checked){
                oddHours="odd hours";
            }else {
                oddHours="regular hours";
            }
            date=editTextDate.getText().toString();
            hours=etTimeHours.getText().toString();
            ContentValues contentValues=new ContentValues();
            contentValues.put("name",NameOfJob);
            contentValues.put("date",date);
            contentValues.put("hour",hours);
            contentValues.put("oddhours",oddHours);
            db.insert("job",null,contentValues);
            Toast.makeText(getContext(),"SUCCESS",Toast.LENGTH_SHORT).show();
//            db.close();
        }
        if(view.getId()==R.id.btn_update){
            initData();
            imformation.notifyDataSetChanged();

        }

        if(view.getId()==R.id.btn_delete){
            NameOfJob=etGetNameOfJob.getText().toString();
//            String sql="delete from job where name = name";
            db.delete("job","name=?",new String[]{NameOfJob+""});
//            db.execSQL(sql);
            Toast.makeText(getContext(),"DELETE",Toast.LENGTH_SHORT).show();
        }

}

    @Override
    public void onItemClick(int position) {

    }
public void getAll(Cursor cursor){
        List<JobInfo> l;
    Log.i("TAG", "getAll:  "+cursor.getCount());
        if(cursor.getCount()!=0){
            while (cursor.moveToNext()){
                JobInfo jobInfo=new JobInfo();
                jobInfo.setNameOfJob(cursor.getString(1));
                jobInfo.setDate(cursor.getString(2));
                jobInfo.setTimeHours(cursor.getString(3));
                jobInfo.setOffHours(cursor.getString(4));
                list.add(jobInfo);
            }



}



}}