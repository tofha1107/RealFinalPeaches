package com.charlezz.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class ExecBeforePageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button real_start_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exec_before_page);

        real_start_button = (Button) findViewById(R.id.real_start_button);

        real_start_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v.getId() == R.id.real_start_button) {
            intent = new Intent(getApplicationContext(), ExecutionPage.class);
            startActivity(intent);
        }
    }

}