package com.charlezz.cameraxdemo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ExecutionPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.execution_page);
    }

    //long millisInFuture	타이머가 실행될 총 지속 시간입니다 (예 : 미래의 어느 정도까지 타이머를 끝내기를 원하십니까). 밀리 초 단위. 1000밀리초 = 1초
    //long countDownInterval	타이머 업데이트를 받으려는 간격. 밀리 초 단위.
    //long millisUntilFinished	onTick() 남은 시간을 알려주는 onTick() 제공된 매개 변수입니다. 밀리 초 단위

    TextView textView = (TextView)findViewById(R.id.using_time_view);

    CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
        public void onTick(long millisUntilFinished) {
            textView.setText(String.format(Locale.getDefault(), "%d sec.", millisUntilFinished / 1000L));
        }

        public void onFinish() {
            textView.setText("Done.");
        }
    }.start();

}