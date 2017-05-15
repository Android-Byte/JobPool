package com.example.dell.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by chauhan on 5/12/2017.
 */

public class CondidateRegisterActivity extends Activity {

    LinearLayout registerNowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.condidate_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        clickListener();
    }

    public  void init(){
        registerNowLayout = (LinearLayout)findViewById(R.id.registerNowLayout);
    }

    public void clickListener(){
        registerNowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CondidateRegisterActivity.this, CompleteProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
