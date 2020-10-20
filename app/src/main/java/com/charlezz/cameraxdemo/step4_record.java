package com.charlezz.cameraxdemo;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;
import java.util.ArrayList;

public class step4_record extends AppCompatActivity implements AutoPermissionsListener {

    AlertDialog alertDialog;
    Ringtone rt;
    RingtoneManager rtm;
    private Button basic_sound;
    private Button record_sound;
    private TextView m_tvRingtoneUri;
    private String m_strRingToneUri;
    private TextView text;
    MediaRecorder recorder;
    MediaPlayer player;
    String filename;
    public static final int PERMISSION_ALL = 0;



    private final static String TAG = "MainActivity";
    private final static int REQUESTCODE_RINGTONE_PICKER = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step4_record);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
        int width = dm.widthPixels; //디바이스 화면 너비
        int height = dm.heightPixels;

//        Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
//        vib.vibrate(1000);

//        basic_sound = (Button) findViewById(R.id.basic_sound);
//        basic_sound.setOnClickListener(this);
//
//        record_sound = (Button) findViewById(R.id.record_sound);
//        record_sound.setOnClickListener(this);

        File myDirectory = new File(Environment.getExternalStorageDirectory(), "11zon");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }

        filename = myDirectory.getAbsolutePath() + File.separator  + "recorded.mp4";

        Log.v("filename", filename);
        AutoPermissions.Companion.loadAllPermissions(this, 101);

        basic_sound = findViewById(R.id.basic_btn);
        basic_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRingtonChooser();

//            case R.id.stop_play_ringtone:
//                this.releaseRingtone();
//               break;
            }

        });




//        cd = new CustomDialog(this);
//        WindowManager.LayoutParams wm = cd.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
//        wm.copyFrom(cd.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
//        wm.width = width / 2;  //화면 너비의 절반
//        wm.height = height / 2;  //화면 높이의 절반


        record_sound = findViewById(R.id.record_btn);
        record_sound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

//                AlertDialog.Builder ad = new AlertDialog.Builder(alertPage.this);
//
//                ad.setIcon(R.drawable.ipeach3);
//                ad.setTitle("녹음하기");

//                ad.setItems(R.array.LAN, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int pos) {
//
//                        String[] items = getResources().getStringArray(R.array.LAN);
//
//
//                        Toast.makeText(getApplicationContext(), items[pos], Toast.LENGTH_SHORT).show();
//                    }
//                });

//                ad.setNeutralButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        dialog.dismiss();
//                    }
//                });
//
//                ad.setNegativeButton("녹음하기", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        startRecording();
//                    }
//                });
//
//                ad.setPositiveButton("녹음완료", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//
//
//                        dialog.dismiss();
//                    }
//                });





//
//                AlertDialog dialog = ad.create();
//
//                dialog.show();
            }
        });

    }
    //@Override
//    public void onClick(View v) {
//        if(v.getId() == R.id.basic_sound){
////            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
////            rt = RingtoneManager.getRingtone(getApplicationContext(), notification);
////            rt.play(); //누르면 type_alarm 소리가 나옴
//        }else if(v.getId() == R.id.record_sound){

    //Intent intent = new Intent(this, audioRecord.class);
    //startActivity(intent);
//        }

//        switch( v.getId() ) {
//            case R.id.basic_sound: showRingtonChooser();
//                break;
////            case R.id.stop_play_ringtone:
////                this.releaseRingtone();
////                break;
//        }
//    }

//    private void startRingtone(Uri uriRingtone ) {
//        this.releaseRingtone();
//        try { mMediaPlayer = MediaPlayer.create(getApplicationContext(), uriRingtone );
//            if( mMediaPlayer == null ) {
//                throw new Exception( "Can't create player" );
//            }
//            // STREAM_VOICE_CALL, STREAM_SYSTEM, STREAM_RING, STREAM_MUSIC, STREAM_ALARM
//            // STREAM_NOTIFICATION, STREAM_DTMF
//            mMediaPlayer.setAudioStreamType( AudioManager.STREAM_ALARM );
//            mMediaPlayer.setAudioStreamType( AudioManager.STREAM_MUSIC );
//            //mMediaPlayer.setAudioAttributes();
//            mMediaPlayer.start();
//        }
//        catch( Exception e ) {
//            Toast.makeText( this, e.getMessage(), Toast.LENGTH_SHORT ).show();
//            Log.e(TAG, e.getMessage() );
//            e.printStackTrace();
//        }
//    }

    //-- 재생중인 링톤을 중지하는 함수
//    private void releaseRingtone() {
//        if( mMediaPlayer != null ) {
//            if( mMediaPlayer.isPlaying() ) {
//                mMediaPlayer.stop(); } mMediaPlayer.release();
//            mMediaPlayer = null;
//        }
//    }

    private void showRingtonChooser() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "기본 알림음을 선택하세요!" );
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALL);


        //-- 알림 선택창이 떴을 때, 기본값으로 선택되어질 ringtone설정
//        if( m_strRingToneUri != null && m_strRingToneUri.isEmpty() ) {
//            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse( m_strRingToneUri ));
//        }
        this.startActivityForResult( intent, REQUESTCODE_RINGTONE_PICKER );
    }

    //-- 알림선택창에서 넘어온 데이터를 처리하는 코드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == REQUESTCODE_RINGTONE_PICKER ) {
            if (resultCode == RESULT_OK) {
                // -- 알림음 재생하는 코드 --
                Uri ring = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                if (ring != null) {m_strRingToneUri = ring.toString();
                    m_tvRingtoneUri.setText(ring.toString());
                    //this.startRingtone(ring);
                } else {m_strRingToneUri = null;
                    m_tvRingtoneUri.setText( "Choose ringtone" );
                }
            }
        }
    }
    //-- 눌러진 버튼에 따라 처리하는 함수
//     @Override
//     public void onClick(View v) {
//        switch( v.getId() ) {
//            case R.id.show_ringtone_chooser: showRingtonChooser();
//                break;
//            case R.id.stop_play_ringtone:
//                this.releaseRingtone();
//                break;
//        }
//    }

    //여기서부터 녹음

    public void startRecording() {
        try {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(filename);
            recorder.prepare();
            recorder.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        if (recorder == null) {
            return;
        }

        recorder.stop();
        recorder.release();
        recorder = null;

        ContentValues values = new ContentValues(10);

        values.put(MediaStore.MediaColumns.TITLE, "Recorded");
        values.put(MediaStore.Audio.Media.ALBUM, "Audio Album");
        values.put(MediaStore.Audio.Media.ARTIST, "Mike");
        values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Audio");
        values.put(MediaStore.Audio.Media.IS_RINGTONE, 1);
        values.put(MediaStore.Audio.Media.IS_MUSIC, 1);
        values.put(MediaStore.MediaColumns.DATE_ADDED,
                System.currentTimeMillis()/1000);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4");
        values.put(MediaStore.Audio.Media.DATA, filename);

        Uri audioUri = getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
        if (audioUri == null) {
            Log.d("SampleAudioRecorder", "Audio insert failed.");
            return;
        }
    }

    public void startPlay() {
        killMediaPlayer();

        try {
            player = new MediaPlayer();
            player.setDataSource("file://" + filename);
            player.prepare();
            player.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void killMediaPlayer() {
        if (player != null) {
            try {
                player.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //runtime permission
    public boolean checkPermission() {
        int RECORD_AUDIO_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int WRITE_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        ArrayList<String> PERMISSION_LIST =new ArrayList<>();
        if((RECORD_AUDIO_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            PERMISSION_LIST.add(Manifest.permission.RECORD_AUDIO);
        }
        if((WRITE_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            PERMISSION_LIST.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!PERMISSION_LIST.isEmpty()) {
            ActivityCompat.requestPermissions(this, PERMISSION_LIST.toArray(new String[PERMISSION_LIST.size()]), PERMISSION_ALL);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean record = false,storage =  false;
        switch (requestCode) {
            case  PERMISSION_ALL: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(Manifest.permission.RECORD_AUDIO)) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                record = true;
                            } else {
                                Toast.makeText(getApplicationContext(), "Please allow Microphone permission", Toast.LENGTH_LONG).show();
                            }
                        } else if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                storage = true;
                            } else {
                                Toast.makeText(getApplicationContext(), "Please allow Storage permission", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
                if (record && storage) {
                    //
                }
            }
        }
    }

    //release mediarecorder
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recorder != null) {
            recorder.release();
        }
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions denied : " + permissions.length,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }
}