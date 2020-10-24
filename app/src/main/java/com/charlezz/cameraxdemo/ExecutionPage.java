package com.charlezz.cameraxdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class ExecutionPage extends AppCompatActivity implements View.OnClickListener {

    //MILLISINFUTURE과 COUNT 함께 설정
//    private static int MILLISINFUTURE;
    private static int bun;
    private static int cho;
    private static final int COUNT_DOWN_INTERVAL = 1000;
//    private int count = 10;
    private Button end_imd_button;
    private TextView using_time_view ;
    private CountDownTimer cdt;
    private Timer timer;
    Intent intent;
    int blink;
    int static_blink;
    int time;
    int moveTime;
    private String distance;
    TextView blink_count;
//    String a = "0";

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() { // TimerTask 1번째 : ClosePage로 가기
            intent = new Intent(getApplicationContext(),ClosePage.class);
            startActivity(intent);
        }
    };

    TimerTask timerTask2 = new TimerTask() {
        @Override
        public void run() { // TimerTask 2번째 : 눈운동 페이지로 가기
            intent = new Intent(getApplicationContext(), exerciseCamera_left.class);
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.execution_page);

        using_time_view = (TextView)findViewById(R.id.using_time_view);

        static_blink = timeResetClasss.blink_static_cnt;
        blink_count = (TextView) findViewById(R.id.blink_cnt);
        blink_count.setText(Integer.toString(static_blink));

        time = timeResetClasss.count_down_receive;
        moveTime = timeResetClasss.eye_exe_receive;

        timer = new Timer();

        timer.schedule(timerTask, time);
        if(moveTime != 0){
            timer.schedule(timerTask2, moveTime);
        }

//        카운트 다운 숫자랑 시간 설정하기 -> 보여지는 숫자 : count / 실제 시간 : MILLISINFUTURE / time 나누기 1000 = 초

        bun = (time / (60*1000));
        cho = (time % (60 * 1000)) / 1000;

        countDownTimer();
        cdt.start();

//        if (a.equals("1")){
//        Intent intent3 = getIntent();
//        a = intent3.getStringExtra("cam");
            timeResetClasss.count_down_receive = time;
//            cdt.cancel();
//            cdt.onFinish();
//        }
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

        cdt = new CountDownTimer(timeResetClasss.count_down_receive, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {

                using_time_view.setText(bun+"분"+cho+"초");

                    if (cho == 0){
                        cho = 60;
                        bun--;
                    }
                    Log.v("초",""+cho+ ":" );
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
            cdt.cancel();
        } catch (Exception e) {}
        cdt =null;
    }



}