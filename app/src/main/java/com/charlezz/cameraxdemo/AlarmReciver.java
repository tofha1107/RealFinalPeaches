package com.charlezz.cameraxdemo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "아빠~!!! 일어나!!!",Toast.LENGTH_SHORT).show();
        Log.e("Alarm","알람입니다.");
//        SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
//        int soundId = soundPool.load(context,R.raw.sasa,1);
//        soundPool.play(soundId,1,1,0,0,1);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context,AlarmTest.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

       // PendingIntent pendingI = PendingIntent.getActivities(context,0,notificationIntent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남


            String channelName ="매일 알람 채널";
            String description = "매일 정해진 시간에 알람합니다.";
            int importance = NotificationManager.IMPORTANCE_HIGH; //소리와 알림메시지를 같이 보여줌

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if (notificationManager != null) {
                // 노티피케이션 채널을 시스템에 등록
                notificationManager.createNotificationChannel(channel);
            }
        }else builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남


        builder.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())

                .setTicker("{Time to watch some cool stuff!}")
                .setContentTitle("ISafe")
                .setContentText("눈건강을 챙기셔요")
                .setContentInfo("INFO");
              //  .setContentIntent(pendingI);

        if (notificationManager != null) {

            // 노티피케이션 동작시킴
            notificationManager.notify(1234, builder.build());

            Calendar nextNotifyTime = Calendar.getInstance();

            // 내일 같은 시간으로 알람시간 결정
            nextNotifyTime.add(Calendar.DATE, 1);

            //  Preference에 설정한 값 저장
            SharedPreferences.Editor editor = context.getSharedPreferences("daily alarm", Context.MODE_PRIVATE).edit();
            editor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis());
            editor.apply();

            Date currentDateTime = nextNotifyTime.getTime();
            String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
            Toast.makeText(context.getApplicationContext(),"다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
            Log.d("test", "액티비티-서비스 시작버튼클릭");
            Intent intent1 = new Intent(
                    context,//현재제어권자
                    MyService.class); // 이동할 컴포넌트
            context.startService(intent1); // 서비스 시작


        }
    }
}
