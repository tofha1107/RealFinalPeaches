package com.charlezz.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClosePage extends AppCompatActivity implements View.OnClickListener {

    private Button real_finish_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.close_page);

        real_finish_button = (Button)findViewById(R.id.real_finish_button);
        real_finish_button.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.real_finish_button) {
            Intent intent = new Intent(getApplicationContext(), PwPage.class);
            startActivity(intent);
        }
    }
}