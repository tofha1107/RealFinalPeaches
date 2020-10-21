package com.charlezz.cameraxdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ExecutionPage extends AppCompatActivity implements View.OnClickListener {

    //MILLISINFUTURE과 COUNT 함께 설정

    private static final int MILLISINFUTURE = 10*1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;
    private int count = 10;
    private Button end_imd_button;
    private TextView using_time_view ;
    private CountDownTimer countDownTimer;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.execution_page);

        Intent intent = getIntent();

        // 시간설정값
        int time = intent.getIntExtra("settingTime",0);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),ClosePage.class);
                startActivity(intent);
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, time);
        Log.v("타이머","실행");

        using_time_view = (TextView)findViewById(R.id.using_time_view);
        countDownTimer();
        countDownTimer.start();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(ExecutionPage.this, ClosePage.class);
//                startActivity(intent);
//            }
//        }, 10*1000);

        end_imd_button = (Button)findViewById(R.id.end_imd_button);
        end_imd_button.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.end_imd_button) {
            Intent intent = new Intent(getApplicationContext(), ClosePage.class);
            startActivity(intent);
        }
    }

    public void countDownTimer(){

        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                using_time_view.setText(String.valueOf(count));
                count --;
            }
            public void onFinish() {
                using_time_view.setText(String.valueOf("시간 끝~~!!"));
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            countDownTimer.cancel();
        } catch (Exception e) {}
        countDownTimer=null;
    }



}