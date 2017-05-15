package com.example.dell.job;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by SURAJ SHAKYA.
 * shakyasuraj08@gmail.com
 */

public class MenuFragment extends Fragment {

    private View rootView;
    LinearLayout profileLayout, searchLayout, notificationLayout, aboutusLayout,
            change_passwordLayout, privacyPolicyLayout, termsLayout, logoutLayout;

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


        profileLayout = (LinearLayout)rootView.findViewById(R.id.profileLayout);
        searchLayout = (LinearLayout)rootView.findViewById(R.id.searchLayout);
        notificationLayout = (LinearLayout)rootView.findViewById(R.id.notificationLayout);
        aboutusLayout = (LinearLayout)rootView.findViewById(R.id.aboutusLayout);
        change_passwordLayout = (LinearLayout)rootView.findViewById(R.id.change_passwordLayout);
        privacyPolicyLayout = (LinearLayout)rootView.findViewById(R.id.privacyPolicyLayout);
        termsLayout = (LinearLayout)rootView.findViewById(R.id.termsLayout);
        logoutLayout = (LinearLayout)rootView.findViewById(R.id.logoutLayout);


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


    private void clicklistener(){

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}