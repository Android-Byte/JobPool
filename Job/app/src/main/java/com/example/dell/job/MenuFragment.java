package com.example.dell.job;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.TagLostException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import utils.Constant;
import utils.RequestReceiver;
import utils.WebserviceHelper;

/**
 * Created by SURAJ SHAKYA.
 * shakyasuraj08@gmail.com
 */

public class MenuFragment extends Fragment implements RequestReceiver{

    private View rootView;
    LinearLayout profileLayout, searchLayout, notificationLayout, aboutusLayout,
            change_passwordLayout, privacyPolicyLayout, termsLayout, logoutLayout;
    TextView userNameTxt;
    RequestReceiver receiver;
    SharedPreferences sharedPreferences;

    public static Fragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.sliding_menu_items, container, false);
        sharedPreferences = getActivity().getSharedPreferences("loginstatus", Context.MODE_PRIVATE);
        receiver = this;
        profileLayout = (LinearLayout)rootView.findViewById(R.id.profileLayout);
        searchLayout = (LinearLayout)rootView.findViewById(R.id.searchLayout);
        notificationLayout = (LinearLayout)rootView.findViewById(R.id.notificationLayout);
        aboutusLayout = (LinearLayout)rootView.findViewById(R.id.aboutusLayout);
        change_passwordLayout = (LinearLayout)rootView.findViewById(R.id.change_passwordLayout);
        privacyPolicyLayout = (LinearLayout)rootView.findViewById(R.id.privacyPolicyLayout);
        termsLayout = (LinearLayout)rootView.findViewById(R.id.termsLayout);
        logoutLayout = (LinearLayout)rootView.findViewById(R.id.logoutLayout);
        userNameTxt = (TextView)rootView.findViewById(R.id.userNameTxt);


        userNameTxt.setText(sharedPreferences.getString("user_name",""));

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        clicklistener();
    }

    public void logoutcallSerivice() {
        WebserviceHelper employer = new WebserviceHelper(receiver, getActivity());
        employer.setAction(Constant.LOGOUT);
        employer.execute();
    }

    private void clicklistener(){

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProfileActivity.class);
                startActivity(intent);
            }
        });

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.EMAIL = sharedPreferences.getString("email","");
                logoutcallSerivice();
            }
        });
    }

    @Override
    public void requestFinished(String[] result) throws Exception {
        if(result[0].equals("1")){
            sharedPreferences =  getActivity().getSharedPreferences("loginstatus",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(getActivity(),""+result[1], Toast.LENGTH_SHORT).show();
        }
    }
}