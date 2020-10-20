package com.charlezz.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExecBeforePageActivity extends AppCompatActivity implements View.OnClickListener {

    //현재 button6을 누르면 시각이 바뀜
    private Button real_start_button;  // 실행버튼
    private Button reset_button;  // 다시설정하기 버튼

    private Button button6;   // 설정한 눈운동주기 시간 나타내기
    private Button button4;  // 눈운동 시작 버튼

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("kk:mm");
    TextView mTextView;

    private Button go_eye;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exec_before_page);

        Intent intent = getIntent();
        int time = intent.getExtras().getInt("time");
        int time2 = intent.getExtras().getInt("time2");



        real_start_button = (Button) findViewById(R.id.real_start_button);
        button6 = (Button) findViewById(R.id.button6);
        button4 = (Button) findViewById(R.id.button4);
        reset_button = (Button) findViewById(R.id.reset_button);
        button6.setText(String.valueOf(time2));
        //bind view
        mTextView = (TextView) findViewById(R.id.start_time);
        mTextView.setText(getTime());


        TextView end_time = findViewById(R.id.end_time);



        real_start_button.setOnClickListener(this);
        button6.setOnClickListener(this);
        reset_button.setOnClickListener(this);

        go_eye = (Button) findViewById(R.id.button4);
        go_eye.setOnClickListener(this);
    }
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v.getId() == R.id.real_start_button) {
            intent = new Intent(getApplicationContext(), ExecutionPage.class);
            startActivity(intent);
        }else if(v.getId() == R.id.reset_button){
            intent = new Intent(getApplicationContext(), DistsancePage.class);
            startActivity(intent);
        }else if (v.getId() == R.id.button4) {
            intent = new Intent(this, videoList.class);
            startActivity(intent);
        }
    }


}