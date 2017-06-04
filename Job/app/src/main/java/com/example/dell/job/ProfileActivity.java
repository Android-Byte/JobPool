package com.example.dell.job;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProfileActivity.this, SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
                final Dialog dialog = new Dialog(ProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.share_dialog);
                TextView mailTxt = (TextView)dialog.findViewById(R.id.mailTxt);
                TextView messageTxt = (TextView)dialog.findViewById(R.id.messageTxt);

                mailTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto","", null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ConTact");
                        emailIntent.putExtra(Intent.EXTRA_TEXT,  "");
                        startActivity(Intent.createChooser(emailIntent, "Send Email..."));
                        dialog.dismiss();
                    }
                });

                messageTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String smsBody= "";
                        Uri uri = Uri.parse("smsto:0800000123");
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                        sendIntent.putExtra("sms_body", smsBody);
                        sendIntent.setType("vnd.android-dir/mms-sms");
                         startActivity(sendIntent);
                        dialog.dismiss();
                    }
                });

                dialog.show();
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
