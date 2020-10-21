package com.charlezz.cameraxdemo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class step4_record extends AppCompatActivity {

    AlertDialog alertDialog;
    Ringtone rt;
    RingtoneManager rtm;
    private Button record_btn, basic_btn, set_finish_button;
    private TextView m_tvRingtoneUri;
    private String m_strRingToneUri;
    private TextView text;
    MediaRecorder recorder;
    MediaPlayer player;
    String filename;
    public static final int PERMISSION_ALL = 0;

    private final static String TAG = "alterFunc";
    private final static int REQUESTCODE_RINGTONE_PICKER = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step4_record);

        text = findViewById(R.id.textView);

        basic_btn = findViewById(R.id.basic_btn);
        basic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRingtonChooser();
            }

        });

        record_btn = findViewById(R.id.record_btn);
        record_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(step4_record.this, audioRecord.class));
            }
        });

        set_finish_button = findViewById(R.id.set_finish_button);
        set_finish_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(step4_record.this, ExecBeforePageActivity.class));
            }
        });


    }

    @Override
    public void onBackPressed()
    {

    }
    private void showRingtonChooser() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "기본 알림음을 선택하세요!" );
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALL);

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
                if (ring != null) {

                    //this.startRingtone(ring);
                } else {m_strRingToneUri = null;
                    m_tvRingtoneUri.setText( "Choose ringtone" );
                }
            }
        }
    }






}