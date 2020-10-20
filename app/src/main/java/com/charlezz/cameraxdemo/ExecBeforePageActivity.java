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
    private Button real_start_button, button6;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("kk:mm");
    TextView mTextView;

    private Button go_eye;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exec_before_page);



        real_start_button = (Button) findViewById(R.id.real_start_button);
        button6 = (Button) findViewById(R.id.button6);
        //bind view
        mTextView = (TextView) findViewById(R.id.start_time);
        mTextView.setText(getTime());

        real_start_button.setOnClickListener(this);
        button6.setOnClickListener(this);

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
        }else if(v.getId() == R.id.button6){
            mTextView.setText(getTime());
        }else if (v.getId() == R.id.button4) {
            intent = new Intent(this, videoList.class);
            startActivity(intent);
        }
    }


}