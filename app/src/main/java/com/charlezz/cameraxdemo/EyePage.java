package com.charlezz.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EyePage extends AppCompatActivity implements View.OnClickListener {

    private Button button_choose2;

    private Button button11;
    private Button button12;
    private Button button13;
    private Button button14;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eye_page);

        button_choose2 = (Button)findViewById(R.id.button_choose2);
        button_choose2.setOnClickListener(this);

        button11 = (Button)findViewById(R.id.button11);
        button11.setOnClickListener(this);

        button12 = (Button)findViewById(R.id.button12);
        button12.setOnClickListener(this);

        button13 = (Button)findViewById(R.id.button13);
        button13.setOnClickListener(this);

        button14 = (Button)findViewById(R.id.button14);
        button14.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_choose2) {
            Intent intent = new Intent(getApplicationContext(), ExecBeforePageActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.button11) {
            //btn_choose10.setBackground(Drawable);
            button11.setBackground(this.getResources().getDrawable(R.drawable.dark_5));
            button12.setBackground(this.getResources().getDrawable(R.drawable.light_10));
            button13.setBackground(this.getResources().getDrawable(R.drawable.light_60));
            button14.setBackground(this.getResources().getDrawable(R.drawable.light_20));

        }

        if (v.getId() == R.id.button12) {
            //btn_choose10.setBackground(Drawable);
            button12.setBackground(this.getResources().getDrawable(R.drawable.dark_10));
            button11.setBackground(this.getResources().getDrawable(R.drawable.light_5));
            button13.setBackground(this.getResources().getDrawable(R.drawable.light_60));
            button14.setBackground(this.getResources().getDrawable(R.drawable.light_20));
        }

        if (v.getId() == R.id.button13) {
            //btn_choose10.setBackground(Drawable);
            button13.setBackground(this.getResources().getDrawable(R.drawable.dark_60));
            button11.setBackground(this.getResources().getDrawable(R.drawable.light_5));
            button14.setBackground(this.getResources().getDrawable(R.drawable.light_20));
            button12.setBackground(this.getResources().getDrawable(R.drawable.light_10));
        }

        if (v.getId() == R.id.button14) {
            //btn_choose10.setBackground(Drawable);
            button14.setBackground(this.getResources().getDrawable(R.drawable.dark_20));
            button12.setBackground(this.getResources().getDrawable(R.drawable.light_10));
            button11.setBackground(this.getResources().getDrawable(R.drawable.light_5));
            button13.setBackground(this.getResources().getDrawable(R.drawable.light_60));
        }
    }

}