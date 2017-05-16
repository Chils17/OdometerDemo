package com.webmyne.odometerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private NPSet npSet;
    private TextView tvOutPut;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        npSet = (NPSet) findViewById(R.id.odometer);
        tvOutPut = (TextView) findViewById(R.id.tvOutPut);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        clickListener();

    }

    private void clickListener() {

        btn_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_submit:

//                Log.e("tag", "tvOutPut: " + tvOutPut + "   arrayList:" + npSet.getFinalOdoMiterValue());

//                npSet.getFinalOdoMiterValue();

                tvOutPut.setText(npSet.getFinalOdoMiterValue());

                break;
        }

    }
}
