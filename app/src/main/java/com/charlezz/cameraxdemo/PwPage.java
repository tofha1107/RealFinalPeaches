package com.charlezz.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PwPage extends AppCompatActivity implements View.OnClickListener {

    private Button last_pw_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pw_page);

        last_pw_button = (Button)findViewById(R.id.last_pw_button);
        last_pw_button.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.last_pw_button) {
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }
    }
}