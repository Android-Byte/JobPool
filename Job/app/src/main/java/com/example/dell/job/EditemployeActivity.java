package com.example.dell.job;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import utils.Constant;
import utils.Global;
import utils.RequestReceiver;
import utils.WebserviceHelper;

public class EditemployeActivity extends AppCompatActivity implements RequestReceiver{

    RequestReceiver receiver;
    EditText nameCompanyEditTxt, contactPersonEditTxt, email_idEditTxt, phoneEditTxt,
            current_requirementEditTxt, experienceEditTxt, skillEditTxt, job_roleEditTxt, locationEditTxt,
            addressEditTxt;
    CheckBox termsCondiationCheck;
    LinearLayout SubmiTLayout;
    ScrollView parentLayout;
    SharedPreferences sharedPreferences;
    MenuFragment menuFragment;
    EditProfileActivity editProfileActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_employer);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
        clickListener();
    }

    public void init(){

        receiver = this;

        editProfileActivity = new EditProfileActivity();
        sharedPreferences = this.getSharedPreferences("loginstatus", Context.MODE_PRIVATE);
        nameCompanyEditTxt = (EditText)findViewById(R.id.nameCompanyEditTxt);
        contactPersonEditTxt = (EditText)findViewById(R.id.contactPersonEditTxt);
        email_idEditTxt = (EditText)findViewById(R.id.email_idEditTxt);
        phoneEditTxt = (EditText)findViewById(R.id.phoneEditTxt);

        current_requirementEditTxt = (EditText)findViewById(R.id.current_requirementEditTxt);
        experienceEditTxt = (EditText)findViewById(R.id.experienceEditTxt);
        skillEditTxt = (EditText)findViewById(R.id.skillEditTxt);
        job_roleEditTxt = (EditText)findViewById(R.id.job_roleEditTxt);
        locationEditTxt = (EditText)findViewById(R.id.locationEditTxt);
        addressEditTxt = (EditText)findViewById(R.id.addressEditTxt);

        parentLayout = (ScrollView)findViewById(R.id.parentLayout);
        termsCondiationCheck = (CheckBox)findViewById(R.id.termsCondiationCheck);
        SubmiTLayout = (LinearLayout)findViewById(R.id.SubmiTLayout);
        setCompanyData();
    }

    public  void clickListener(){

        SubmiTLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Constant.COMPANY_NAME =  nameCompanyEditTxt.getText().toString();
               Constant.CONTACTPERSON = contactPersonEditTxt.getText().toString();
               Constant.EMAIL = email_idEditTxt.getText().toString();
               Constant.PHONE_NUMBER = phoneEditTxt.getText().toString();
               Constant.CURRENT_REQUIRMENT = current_requirementEditTxt.getText().toString();
               Constant.EXPERIENCE = experienceEditTxt.getText().toString();
               Constant.SKILLES = skillEditTxt.getText().toString();
               Constant.JOBROLL = job_roleEditTxt.getText().toString();
               Constant.LOCATION = locationEditTxt.getText().toString();
               Constant.ADDRESS = addressEditTxt.getText().toString();
               callSerivice();
            }
        });
    }

    public void setCompanyData(){
        try{
            nameCompanyEditTxt.setText(Global.companylist.get(0).getCompany_name());
            contactPersonEditTxt.setText(Global.companylist.get(0).getContact_person());
            email_idEditTxt.setText(Global.companylist.get(0).getEmail());
            phoneEditTxt.setText(Global.companylist.get(0).getPhone());
            current_requirementEditTxt.setText(Global.companylist.get(0).getCurrent_requirment());
            experienceEditTxt.setText(Global.companylist.get(0).getExperience());
            skillEditTxt.setText(Global.companylist.get(0).getSkill());
            job_roleEditTxt.setText(Global.companylist.get(0).getJob_role());
            locationEditTxt.setText(Global.companylist.get(0).getLocation());
            addressEditTxt.setText(Global.companylist.get(0).getAddress());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void callSerivice() {
        WebserviceHelper employer = new WebserviceHelper(receiver, EditemployeActivity.this);
        employer.setAction(Constant.UPDATE_EMPLOYER_PROFILE);
        employer.execute();
    }

    @Override
    public void requestFinished(String[] result) throws Exception {
            if(result[0].equals("1")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("company_name", "" + Global.companylist.get(0).getCompany_name());
                editor.commit();
                menuFragment.updateName(EditemployeActivity.this);
                final Dialog dialog = new Dialog(EditemployeActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alertpopup);
                dialog.show();
                TextView massageTxtView = (TextView) dialog.findViewById(R.id.massageTxtView);
                massageTxtView.setText(result[1]);
                LinearLayout submitLayout = (LinearLayout)dialog.findViewById(R.id.submitLayout);
                submitLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                editProfileActivity.setcompanyData(EditemployeActivity.this);
                dialog.dismiss();
                finish();
                    }
                });

            }
    }
}
