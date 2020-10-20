package com.charlezz.cameraxdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DistsancePage extends AppCompatActivity implements View.OnClickListener {

    private Button button_choose;
    Button btn10;
    Button btn20;
    Button btn30;
    Button btn60;
    final int[] time = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance_page);



        button_choose = (Button)findViewById(R.id.button_choose);
        button_choose.setOnClickListener(this);

        btn10 = findViewById(R.id.time10);
        btn20 = findViewById(R.id.time20);
        btn30 = findViewById(R.id.time30);
        btn60 = findViewById(R.id.time60);

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time[0] = 10;
            }
        });

        btn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time[0] = 20;
            }
        });

        btn30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time[0] = 30;
            }
        });

        btn60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time[0] = 60;
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_choose) {
            Intent intent = new Intent(getApplicationContext(), EyePage.class);
            intent.putExtra("time", time[0]);
            startActivity(intent);
        }
    }



}