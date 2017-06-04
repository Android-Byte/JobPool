package com.example.dell.job;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import utils.Constant;
import utils.Global;
import utils.RequestReceiver;
import utils.WebserviceHelper;

public class EditCandidateActivity extends Activity implements RequestReceiver {

    Button saveandcontinue;
    RequestReceiver receiver;
    LinearLayout submitNowlayout;
    EditText nameEditTxt, phoneEditTxt, locationEdit, experienceEditTxt, skillEditTxt,strenghtEdit, salaryEdit,addressEditTxt,objectiveEdit,briefDesEdit,email_idEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_complete_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
        clickListener();
    }

    public void init(){
        receiver = this;
        submitNowlayout = (LinearLayout)findViewById(R.id.submitNowlayout);
        nameEditTxt = (EditText)findViewById(R.id.nameEditTxt);
        phoneEditTxt = (EditText)findViewById(R.id.phoneEditTxt);
        locationEdit = (EditText)findViewById(R.id.locationEdit);
        experienceEditTxt = (EditText)findViewById(R.id.experienceEditTxt);
        skillEditTxt = (EditText)findViewById(R.id.skillEditTxt);
        strenghtEdit = (EditText)findViewById(R.id.strenghtEdit);
        salaryEdit = (EditText)findViewById(R.id.salaryEdit);
        addressEditTxt = (EditText)findViewById(R.id.addressEditTxt);
        objectiveEdit = (EditText)findViewById(R.id.objectiveEdit);
        briefDesEdit = (EditText)findViewById(R.id.briefDesEdit);
        email_idEditTxt = (EditText)findViewById(R.id.email_idEditTxt);

        setprofileData();
    }

    public void updateprofile() {
        WebserviceHelper getprofile = new WebserviceHelper(receiver, EditCandidateActivity.this);
        getprofile.setAction(Constant.UPDATE_CANDIDATE_PROFILE);
        getprofile.execute();
    }

    public  void setprofileData(){
        try {

            nameEditTxt.setText(Global.candidatelist.get(0).getName());
            phoneEditTxt.setText(Global.candidatelist.get(0).getPhone());
            locationEdit.setText(Global.candidatelist.get(0).getLocation());
            experienceEditTxt.setText(Global.candidatelist.get(0).getExperience());
            skillEditTxt.setText(Global.candidatelist.get(0).getSkill());
            strenghtEdit.setText(Global.candidatelist.get(0).getStrength());
            salaryEdit.setText(Global.candidatelist.get(0).getExpected_salary());
            addressEditTxt.setText(Global.candidatelist.get(0).getAddress());
            objectiveEdit.setText(Global.candidatelist.get(0).getObjective());
            briefDesEdit.setText(Global.candidatelist.get(0).getBrief_description());
            email_idEditTxt.setText(Global.candidatelist.get(0).getEmail());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickListener(){

        submitNowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constant.USER_NAME = nameEditTxt.getText().toString();
                Constant.PHONE_NUMBER = phoneEditTxt.getText().toString();
                Constant.LOCATION = locationEdit.getText().toString();
                Constant.EXPERIENCE = experienceEditTxt.getText().toString();
                Constant.SKILLES = skillEditTxt.getText().toString();
                Constant.STRENGHT = strenghtEdit.getText().toString();
                Constant.EXP_SALARY= salaryEdit.getText().toString();
                Constant.ADDRESS = addressEditTxt.getText().toString();
                Constant.OBJECTIVE = objectiveEdit.getText().toString();
                Constant.BRIEFDESCRIPTION = briefDesEdit.getText().toString();
                Constant.EMAIL= email_idEditTxt.getText().toString();
                updateprofile();
            }
        });

    }

    @Override
    public void requestFinished(String[] result) throws Exception {
        if (result[0].equals("1")){
            final Dialog dialog = new Dialog(EditCandidateActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.alertpopup);
            TextView massageTxtView = (TextView) dialog.findViewById(R.id.massageTxtView);
            massageTxtView.setText(result[1]);
            LinearLayout submitLayout = (LinearLayout)dialog.findViewById(R.id.submitLayout);
            submitLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            dialog.show();
        }
    }
}
