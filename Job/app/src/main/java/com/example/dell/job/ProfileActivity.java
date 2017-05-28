package com.example.dell.job;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created by Suraj Shakya on 5/14/2017.
 */

public class ProfileActivity extends SlidingFragmentActivity {

    LinearLayout slidMenuLayout;
    TextView downloadTxtView, contactTxtView;
    SlidingMenu sm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        init();
        clickListenr();

        setBehindView();
        sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        sm.setSlidingEnabled(false);

    }

    public void init(){

        slidMenuLayout = (LinearLayout)findViewById(R.id.slidMenuLayout);
        downloadTxtView = (TextView)findViewById(R.id.downloadTxtView);
        contactTxtView = (TextView)findViewById(R.id.contactTxtView);

    }

    public void clickListenr(){

        slidMenuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sm.toggle();
            }
        });

        downloadTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadTxtView.setBackgroundResource(R.color.yellow);
                contactTxtView.setBackgroundResource(R.color.colorAccent);
            }
        });

        contactTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadTxtView.setBackgroundResource(R.color.colorAccent);
                contactTxtView.setBackgroundResource(R.color.yellow);
            }
        });
    }

    private void setBehindView() {
        setBehindContentView(R.layout.menu_slide);
        transactionFragments(MenuFragment.newInstance(), R.id.menu_slide);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toggle();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void transactionFragments(Fragment fragment, int viewResource) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(viewResource, fragment);
        ft.commit();
        toggle();
    }
}
