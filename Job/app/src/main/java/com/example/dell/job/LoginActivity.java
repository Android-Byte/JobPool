package com.example.dell.job;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import adapter.SearchAdapter;
import utils.Constant;
import utils.RequestReceiver;
import utils.WebserviceHelper;

public class LoginActivity extends AppCompatActivity implements RequestReceiver{

    RequestReceiver receiver;
    TextView registerTxtView, forgotPassword;
    LinearLayout loginLayout;
    RelativeLayout parentLayout;
    EditText input_username, input_password;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
        clickListener();

    }

    public void init(){

        sharedPreferences = this.getSharedPreferences("loginstatus", Context.MODE_PRIVATE);
        receiver = this;
        registerTxtView = (TextView)findViewById(R.id.registerTxtView);
        forgotPassword = (TextView)findViewById(R.id.forgotPassword);
        loginLayout = (LinearLayout)findViewById(R.id.loginLayout);
        parentLayout = (RelativeLayout)findViewById(R.id.parentLayout);
        input_username = (EditText)findViewById(R.id.input_username);
        input_password = (EditText)findViewById(R.id.input_password);

    }

    public  void clickListener(){

            forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.forgot_password);
                final EditText email_id = (EditText)dialog.findViewById(R.id.email_id);
                LinearLayout submitLayout = (LinearLayout)dialog.findViewById(R.id.submitLayout);

                submitLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(email_id.getText().length()!=0){
                            Constant.EMAIL  = email_id.getText().toString();
                            forgotcallSerivice();
                            dialog.dismiss();
                        }else {
                            Snackbar.make(parentLayout,"Enter Email.!",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });

        loginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(input_username.getText().length()!=0){
                   if(input_password.getText().length()!=0){
                       Constant.EMAIL = input_username.getText().toString();
                       Constant.PASSWORD = input_password.getText().toString();
                       logincallSerivice();
                   }else {
                       Snackbar.make(parentLayout,"Enter Password.!",Snackbar.LENGTH_SHORT).show();
                   }
               }else {
                   Snackbar.make(parentLayout,"Enter UserName.!",Snackbar.LENGTH_SHORT).show();
               }
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

    public void forgotcallSerivice() {
        WebserviceHelper employer = new WebserviceHelper(receiver, LoginActivity.this);
        employer.setAction(Constant.FORGOTPASSWORD);
        employer.execute();
    }

    public void logincallSerivice() {
        WebserviceHelper employer = new WebserviceHelper(receiver, LoginActivity.this);
        employer.setAction(Constant.LOGIN);
        employer.execute();
    }


    @Override
    public void requestFinished(String[] result) throws Exception {
        if(result[0].equals("1")){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("status", "1");
            editor.putString("user_id", "" + Constant.USER_ID);
            editor.putString("email", "" + Constant.EMAIL);
            editor.putString("user_name", "" + Constant.USER_NAME);
            editor.putString("phone", "" + Constant.PHONE_NUMBER);
            editor.putString("location", "" + Constant.LOCATION);
            editor.commit();

            Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else {
            Snackbar.make(parentLayout,""+result[1],Snackbar.LENGTH_SHORT).show();
        }
    }
}
