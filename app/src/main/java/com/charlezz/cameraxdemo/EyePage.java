package com.charlezz.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EyePage extends AppCompatActivity implements View.OnClickListener {

    private Button button_choose2;

    private Button time10;
    private Button time20;
    private Button time5;
    private Button time60;
    private Intent intent;
    private int settingTime;
    private int moveTime;
    private String distance;
    int blink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eye_page);

        button_choose2 = (Button)findViewById(R.id.button_choose2);
        button_choose2.setOnClickListener(this);

        time10 = (Button)findViewById(R.id.time10);
        time10.setOnClickListener(this);

        time20 = (Button)findViewById(R.id.time20);
        time20.setOnClickListener(this);

        time5 = (Button)findViewById(R.id.time5);
        time5.setOnClickListener(this);

        time60 = (Button)findViewById(R.id.time60);
        time60.setOnClickListener(this);

        intent = getIntent();
        distance = intent.getStringExtra("distance");
        blink = intent.getExtras().getInt("blink");
        moveTime = 0;

        //moveTime = intent.getExtras().getInt("moveTime");

//        Log.v("settingTime",settingTime+",");
//        Toast.makeText(this,"설정한 시간 " + settingTime,Toast.LENGTH_SHORT).show()

    }

    @Override
    public void onBackPressed()
    {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_choose2) {

            intent = new Intent(getApplicationContext(), step4_record.class);
            intent.putExtra("distance",distance);
            intent.putExtra("blink",blink);

            timeResetClasss.eye_exe_receive = moveTime;
            Log.v("eye_exe_receive",timeResetClasss.eye_exe_receive+",");
            startActivity(intent);

        }

        if (v.getId() == R.id.time5) {
            //btn_choose10.setBackground(Drawable);
            time5.setBackground(this.getResources().getDrawable(R.drawable.dark_5));
            time10.setBackground(this.getResources().getDrawable(R.drawable.light_10));
            time60.setBackground(this.getResources().getDrawable(R.drawable.light_60));
            time20.setBackground(this.getResources().getDrawable(R.drawable.light_20));
            moveTime = 10*1000;
        }

        if (v.getId() == R.id.time10) {
            //btn_choose10.setBackground(Drawable);
            time10.setBackground(this.getResources().getDrawable(R.drawable.dark_10));
            time5.setBackground(this.getResources().getDrawable(R.drawable.light_5));
            time60.setBackground(this.getResources().getDrawable(R.drawable.light_60));
            time20.setBackground(this.getResources().getDrawable(R.drawable.light_20));
            moveTime = 1000*60;

        }

        if (v.getId() == R.id.time60) {
            //btn_choose10.setBackground(Drawable);
            time60.setBackground(this.getResources().getDrawable(R.drawable.dark_60));
            time5.setBackground(this.getResources().getDrawable(R.drawable.light_5));
            time20.setBackground(this.getResources().getDrawable(R.drawable.light_20));
            time10.setBackground(this.getResources().getDrawable(R.drawable.light_10));
            moveTime = 60*60*1000;
        }

        if (v.getId() == R.id.time20) {
            //btn_choose10.setBackground(Drawable);
            time20.setBackground(this.getResources().getDrawable(R.drawable.dark_20));
            time10.setBackground(this.getResources().getDrawable(R.drawable.light_10));
            time5.setBackground(this.getResources().getDrawable(R.drawable.light_5));
            time60.setBackground(this.getResources().getDrawable(R.drawable.light_60));
            moveTime = 20*60*1000;
        }
    }

}