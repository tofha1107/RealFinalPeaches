package com.charlezz.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EyePage extends AppCompatActivity implements View.OnClickListener {

    private Button button_choose2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eye_page);

        button_choose2 = (Button)findViewById(R.id.button_choose2);

        button_choose2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_choose2) {
            Intent intent = new Intent(getApplicationContext(), ExecBeforePageActivity.class);
            startActivity(intent);
        }
    }

}