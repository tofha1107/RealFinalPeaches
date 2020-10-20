package com.charlezz.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.logging.LoggingMXBean;

public class EyePage extends AppCompatActivity implements View.OnClickListener {

    private Button button_choose2;
    final int[] time2 = new int[3];
    Button btn10;
    Button btn20;
    Button btn30;
    Button btn60;
    TextView t;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eye_page);

        //t = findViewById(R.id.textView10);


        button_choose2 = (Button)findViewById(R.id.button_choose2);

        button_choose2.setOnClickListener(this);

        Intent intent = getIntent();
        //time = intent.getExtras().getInt("time");
        //t.setText(String.valueOf(time));

        btn10 = findViewById(R.id.time10);
        btn20 = findViewById(R.id.time20);
        btn30 = findViewById(R.id.time30);
        btn60 = findViewById(R.id.time60);

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time2[0] = 5;
            }
        });

        btn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time2[0] = 10;
            }
        });

        btn30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time2[0] = 20;
            }
        });

        btn60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time2[0] = 60;
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_choose2) {
            Intent intent = new Intent(getApplicationContext(), ExecBeforePageActivity.class);

            intent.putExtra("time",time);
            intent.putExtra("time2",time2[0]);
            startActivity(intent);
        }
    }

}