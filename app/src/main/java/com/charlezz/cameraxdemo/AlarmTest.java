package com.charlezz.cameraxdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmTest extends AppCompatActivity {

    private Button btn_start, btn_finish;
    private TimePicker time_picker;
    private AlarmManager alarm_manager;
    private int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_test);

        time_picker = (TimePicker)findViewById(R.id.time_picker);
        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAlarm();
                Toast.makeText(getApplicationContext(),"알람을 시작합니다!",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void startAlarm(){
        Intent intent = new Intent(this, AlarmReciver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this,0,intent,0);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            hour = time_picker.getHour();
            minute = time_picker.getMinute();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        alarm_manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm_manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pIntent);


    }

    public void unregist(View view){
        Intent intent = new Intent(this, AlarmReciver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this,0,intent,0);
    }
}