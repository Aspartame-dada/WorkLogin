package com.example.worklogin.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.worklogin.R;

import androidx.fragment.app.Fragment;

public class ThirdFragment extends Fragment implements View.OnClickListener {


    private EditText etShow;
    private Button btnCredits;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {

        etShow = (EditText) view.findViewById(R.id.et_show);
        btnCredits = (Button) view.findViewById(R.id.btn_credits);
        btnCredits.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_credits){
            Uri uri = Uri.parse("https://www.miamioh.com");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}