package com.charlezz.cameraxdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class DistsancePage extends AppCompatActivity implements View.OnClickListener {

    private Button button_choose;
    private Button btn_choose10;
    private Button button12;
    private Button button13;
    private Button button14;
    private int time;
    private Intent intent;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance_page);

        button_choose = (Button)findViewById(R.id.button_choose);
        button_choose.setOnClickListener(this);

        btn_choose10 = (Button)findViewById(R.id.btn_choose10);
        btn_choose10.setOnClickListener(this);

        button12 = (Button)findViewById(R.id.button12);
        button12.setOnClickListener(this);

        button13 = (Button)findViewById(R.id.button13);
        button13.setOnClickListener(this);

        button14 = (Button)findViewById(R.id.button14);
        button14.setOnClickListener(this);

        intent = getIntent();

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_choose10) {
            //btn_choose10.setBackground(Drawable);
            btn_choose10.setBackground(this.getResources().getDrawable(R.drawable.dark_10));
            button12.setBackground(this.getResources().getDrawable(R.drawable.light_20));
            button13.setBackground(this.getResources().getDrawable(R.drawable.light_60));
            button14.setBackground(this.getResources().getDrawable(R.drawable.light_30));

            //10분을 밀리초로 환산한 값을 설정할 것.
            time = 1000*30; //30초
        }

        if (v.getId() == R.id.button_choose) {
            intent = new Intent(getApplicationContext(), EyePage.class);
            intent.putExtra("settingTime", time);
            startActivity(intent);
        }

        else if (v.getId() == R.id.button12) {
            //btn_choose10.setBackground(Drawable);
            button12.setBackground(this.getResources().getDrawable(R.drawable.dark_20));
            btn_choose10.setBackground(this.getResources().getDrawable(R.drawable.light_10));
            button13.setBackground(this.getResources().getDrawable(R.drawable.light_60));
            button14.setBackground(this.getResources().getDrawable(R.drawable.light_30));
            time = 20;
        }

        else if (v.getId() == R.id.button13) {
            //btn_choose10.setBackground(Drawable);
            button13.setBackground(this.getResources().getDrawable(R.drawable.dark_60));
            btn_choose10.setBackground(this.getResources().getDrawable(R.drawable.light_10));
            button14.setBackground(this.getResources().getDrawable(R.drawable.light_30));
            button12.setBackground(this.getResources().getDrawable(R.drawable.light_20));
            time = 60;
        }

        else if (v.getId() == R.id.button14) {
            //btn_choose10.setBackground(Drawable);
            button14.setBackground(this.getResources().getDrawable(R.drawable.dark_30));
            button12.setBackground(this.getResources().getDrawable(R.drawable.light_20));
            btn_choose10.setBackground(this.getResources().getDrawable(R.drawable.light_10));
            button13.setBackground(this.getResources().getDrawable(R.drawable.light_60));
            time = 30;
        }


    }





}