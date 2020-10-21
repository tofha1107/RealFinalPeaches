package com.charlezz.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class ClosePage extends AppCompatActivity implements View.OnClickListener {

    private Button real_finish_button;
    private TimePicker time_picker;
    private AlarmManager alarm_manager;
    private int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.close_page);

        real_finish_button = (Button)findViewById(R.id.real_finish_button);
        real_finish_button.setOnClickListener(this);


    }

    public void onClick(View v) {
        if (v.getId() == R.id.real_finish_button) {
            Intent intent = new Intent(getApplicationContext(), PwPage.class);
            startActivity(intent);
        }
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