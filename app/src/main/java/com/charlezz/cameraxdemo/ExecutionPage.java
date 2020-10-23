package com.charlezz.cameraxdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ExecutionPage extends AppCompatActivity implements View.OnClickListener {

    //MILLISINFUTURE과 COUNT 함께 설정
    private static int MILLISINFUTURE = 10*1000;
    private static int bun;
    private static int cho;
    private static final int COUNT_DOWN_INTERVAL = 1000;
    private int count = 10;
    private Button end_imd_button;
    private TextView using_time_view ;
    private CountDownTimer countDownTimer;
    private Timer timer, timer2, timer3;
    Intent intent;
    int time;
    int moveTime;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.execution_page);
//         거리값 가져오기
        intent = getIntent();
        time = intent.getExtras().getInt("distance");


        // 시간설정값 가져오기
//        intent = getIntent();
        //time = intent.getExtras().getInt("settingTime");
        time = timeResetClasss.count_down_receive;

        // 눈운동 주기 가져오기
        //moveTime = intent.getExtras().getInt("moveTime");
        moveTime = timeResetClasss.eye_exe_receive;

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                intent = new Intent(getApplicationContext(),ClosePage.class);
                startActivity(intent);
            }
        };


        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() { // TimerTask 2개 실행
                intent = new Intent(getApplicationContext(),exerciseCamera_left.class);
                startActivity(intent);
            }
        };


        TimerTask timerTask3 = new TimerTask() {
            @Override
            public void run() {
                Toast.makeText(ExecutionPage.this, "로그인실패 문구", Toast.LENGTH_LONG).show();
            }
        };


        timer = new Timer();
        timer2 = new Timer();
        timer3 = new Timer();

        timer.schedule(timerTask, time);

        if(moveTime == 0){


        }else {
            timer2.schedule(timerTask2, moveTime);
        }

//        timer2 = new Timer();
//        timer.schedule(timerTask, moveTime);
        Log.v("타이머","실행");

        // 카운트 다운 숫자랑 시간 설정하기 -> 보여지는 숫자 : count / 실제 시간 : MILLISINFUTURE / time 나누기 1000 = 초

        MILLISINFUTURE = time;
        count = time;
        Log.v("받아온시간",""+count);
        bun = (MILLISINFUTURE / (60*1000));
        cho = (MILLISINFUTURE % (60 * 1000)) / 1000;


        using_time_view = (TextView)findViewById(R.id.using_time_view);
        countDownTimer();
        countDownTimer.start();


        end_imd_button = (Button)findViewById(R.id.end_imd_button);
        end_imd_button.setOnClickListener(this);

    }


    public void onClick(View v) {
        if (v.getId() == R.id.end_imd_button) {
            Intent intent = new Intent(getApplicationContext(), PwPage.class);
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed()
    {

    }
    public void countDownTimer(){

        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                using_time_view.setText(bun+"분"+cho+"초");
//                bun = (count / (60*1000));
//                cho = (count % (60 * 1000)) / 1000;

                using_time_view.setText(bun+"분"+cho+"초");
                 if (cho == 0){
                 cho = 60;
                 bun--;}
                cho--;
            }
            public void onFinish() {
                using_time_view.setText(String.valueOf("시간이 끝났어요!"));
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