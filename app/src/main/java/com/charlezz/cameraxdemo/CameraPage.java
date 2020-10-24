package com.charlezz.cameraxdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CameraPage extends AppCompatActivity implements View.OnClickListener{

    private Button face_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_page);

        face_button = (Button)findViewById(R.id.face_button);

        face_button.setOnClickListener(this);

    }
    @Override
    public void onBackPressed()
    {

    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.face_button){
            Intent intent = new Intent(getApplicationContext(), DistsancePage.class);
            startActivity(intent);
        }

    }
}