package com.charlezz.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class ClosePage extends AppCompatActivity implements View.OnClickListener {

    private Button real_finish_button;
    private TimePicker time_picker;
    private AlarmManager alarm_manager;
    TextView blink_count2;
    private int hour, minute;
    Intent intent;
    int static_blink;
    private int using_time, bun2, cho2;
    TextView timerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.close_page);

        blink_count2 = (TextView) findViewById(R.id.blink_cnt2);

        audioRecord audioActivity = new audioRecord();
        audioActivity.startPlay();

        real_finish_button = (Button)findViewById(R.id.real_finish_button);
        real_finish_button.setOnClickListener(this);

        using_time = timeResetClasss.count_down_receive;
        bun2 = (using_time / (60*1000));
        cho2 = (using_time % (60 * 1000)) / 1000;

        timerText = (TextView) findViewById(R.id.timerText);
        if(bun2 == 0){
            timerText.setText(cho2+"초");
        }else {
            timerText.setText(bun2 + "분" + cho2 + "초");
        }
        intent = getIntent();

        static_blink = timeResetClasss.blink_static_cnt;
//        Log.d("","누적 회");
         blink_count2.setText(Integer.toString(static_blink));

    }

    public void onClick(View v) {
        if (v.getId() == R.id.real_finish_button) {
            Intent intent = new Intent(getApplicationContext(), PwPage.class);
            startActivity(intent);
        }
    }
    @Override
    public void onBackPressed()
    {

    }
    public void startAlarm(){
        Intent intent = new Intent(this, ClosePage.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this,0,intent,0);



        Uri audioUri;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //filepath
//            hour = time_picker.getHour();
//            minute = time_picker.getMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        alarm_manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm_manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pIntent);


    }




}