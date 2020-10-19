package com.charlezz.cameraxdemo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ExecutionPage extends AppCompatActivity {

    //MILLISINFUTURE과 COUNT 함께 설정

    private static final int MILLISINFUTURE = 5*1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;

    private int count = 5;
    private TextView using_time_view ;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.execution_page);

        using_time_view = (TextView)findViewById(R.id.using_time_view);
        countDownTimer();
        countDownTimer.start();

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