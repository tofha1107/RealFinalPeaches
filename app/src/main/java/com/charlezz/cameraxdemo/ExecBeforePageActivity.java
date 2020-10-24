package com.charlezz.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class ExecBeforePageActivity extends AppCompatActivity implements View.OnClickListener {

    //현재 button6을 누르면 시각이 바뀜
    private Button real_start_button;  // 실행버튼
    private Button reset_button;  // 다시설정하기 버튼
    private Button button5;
    private Button button6;   // 설정한 눈운동주기 시간 나타내기
    private Button button4;  // 눈운동 시작 버튼

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("kk:mm");
    TextView mTextView, endTextView, end_time;

    private Button go_eye;
    private Timer timer;
    Intent intent;

    String distance;
    int blink;
    int settingTime; // 사용시간
    int moveTime; // 눈운동 주기
    int moveTime_bun; // 눈 운동 주기 분으로 바꾼거

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exec_before_page);

        intent = getIntent();
        distance = intent.getStringExtra("distance");
        blink = intent.getExtras().getInt("blink");

        Log.d("","누적" + blink +"완료");

        settingTime = timeResetClasss.count_down_receive;
        moveTime = timeResetClasss.eye_exe_receive;

        long reservationTime = System.currentTimeMillis() + settingTime ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm");
        String curTime = dateFormat.format(new Date(reservationTime));
//        Log.v("timetimetime",settingTime+"ㅠㅠ");

        button6 = (Button) findViewById(R.id.button6);
        button5 = (Button) findViewById(R.id.button5);
        button4 = (Button) findViewById(R.id.button4);
        end_time = findViewById(R.id.end_time);
        reset_button = (Button) findViewById(R.id.reset_button);
        real_start_button = (Button) findViewById(R.id.real_start_button);


        moveTime_bun = moveTime/(60*1000);

        button5.setText(String.valueOf(distance+"\nCM"));

        if(moveTime_bun == 0){
            button6.setText(String.valueOf("선택\n없음"));
        }else{
            button6.setText(String.valueOf(moveTime_bun + "분 후"));
        }


        mTextView = (TextView) findViewById(R.id.start_time);
        endTextView = (TextView) findViewById(R.id.end_time);

        mTextView.setText(getTime());
        endTextView.setText(curTime);

        real_start_button.setOnClickListener(this);
        button6.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    @Override
    public void onBackPressed()
    {    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.real_start_button) {
            // 시간설정
            intent = new Intent(getApplicationContext(), ExecutionPage.class);
            intent.putExtra("distance", distance);
            intent.putExtra("blink", blink);
            startActivity(intent);

        }else if(v.getId() == R.id.reset_button){
            intent = new Intent(getApplicationContext(), DistsancePage.class);
            intent.putExtra("distance", distance);
            intent.putExtra("blink", blink);
            startActivity(intent);
        }
        else if (v.getId() == R.id.button4) {
            intent = new Intent(this, videoList.class);
            intent.putExtra("distance", distance);
            intent.putExtra("blink", blink);
            startActivity(intent);
        }
    }


}