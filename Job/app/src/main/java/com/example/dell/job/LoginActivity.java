package com.example.dell.job;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView registerTxtView, forgotPassword;
    LinearLayout loginLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        clickListener();
    }

    public void init(){
        registerTxtView = (TextView)findViewById(R.id.registerTxtView);
        forgotPassword = (TextView)findViewById(R.id.forgotPassword);
        loginLayout = (LinearLayout)findViewById(R.id.loginLayout);
    }

    public  void clickListener(){

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Login Coming soon!",Toast.LENGTH_LONG).show();
            }
        });

        loginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Login Coming soon!",Toast.LENGTH_LONG).show();
            }
        });

        registerTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
