package com.example.dell.job;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class EmployerActivity extends AppCompatActivity implements View.OnClickListener {
Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(),EmployerpackageActivity.class);
        startActivity(intent);
    }
}
