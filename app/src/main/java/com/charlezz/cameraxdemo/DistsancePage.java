package com.charlezz.cameraxdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DistsancePage extends AppCompatActivity implements View.OnClickListener {

    private Button button_choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance_page);

        button_choose = (Button)findViewById(R.id.button_choose);
        button_choose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_choose) {
            Intent intent = new Intent(getApplicationContext(), EyePage.class);
            startActivity(intent);
        }
    }



}