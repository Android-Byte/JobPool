package com.example.dell.job;

import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    //Signing Options
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 100;

    /*Facebook*/
    CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    LoginButton login_button;

    LinearLayout facebookLayout, googleLayout, registerLayout, loginLayout, findContactLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(HomeActivity.this);
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.home_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        showAccessTokens();
        getKeyHash();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                Log.e("Access Token", "Tracker");
            }
        };
        if (AccessToken.getCurrentAccessToken() != null) {
            Log.e("Access Token", "request data");
        }
        accessTokenTracker.startTracking();

        init();
        clickListener();
    }

    public  void init(){

        /*Facebook Button*/
        login_button = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        login_button.setReadPermissions("email");

        facebookLayout = (LinearLayout)findViewById(R.id.facebookLayout);
        googleLayout = (LinearLayout)findViewById(R.id.googleLayout);
        registerLayout = (LinearLayout)findViewById(R.id.registerLayout);
        loginLayout = (LinearLayout)findViewById(R.id.loginLayout);
        findContactLayout = (LinearLayout)findViewById(R.id.findContactLayout);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void showAccessTokens() {
        Log.e("showAccessToken", "accessToken");
        AccessToken access_token = AccessToken.getCurrentAccessToken();
    }

    private void getKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            String email = acct.getEmail();
            String name  = acct.getDisplayName();
            Toast.makeText(getApplicationContext(),"User Name :"+name+"\n Email :"+email,Toast.LENGTH_LONG).show();
        } else {
            //If login fails
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }


    //This function will option signing intent
    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            Log.e("onActivityResult", "data" + requestCode);
            RequestData();
        }
    }

    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.e("request data", "oncomplete" + response);
                JSONObject json = response.getJSONObject();
                System.out.println("Json data :" + json);
                try {
                    if (json != null) {
                        Log.e("","Email : "+json.getString("email"));
                        String text = "<b>Name :</b> " + json.getString("name") + "<br><br><b>Email :</b> " + json.getString("email") + "<br><br><b>Profile link :</b> ";
                        Toast.makeText(getApplicationContext(),"User Name :"+ json.getString("name")+"\nEmail :"+json.getString("email") ,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        //parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


    protected void onStop() {
        super.onStop();
        Log.e("onstop", "accesstokentracker");
        //Facebook login
        accessTokenTracker.stopTracking();
    }

    public void showDialogPopup(){
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);
        LinearLayout candiadte = (LinearLayout) dialog.findViewById(R.id.candidate_registration);
        LinearLayout employer = (LinearLayout) dialog.findViewById(R.id.employer_registration);

        candiadte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CondidateRegisterActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EmployerActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void clickListener(){

        findContactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("registercallback", "onSuccess" + loginResult);
                Log.e("loginResult", "else" + loginResult);


                if (AccessToken.getCurrentAccessToken() != null) {
                    Log.e("AccessToken", "onSuccessif" + loginResult);
                    Profile profile = Profile.getCurrentProfile();
                    if (profile != null) {
                        /*get all profile data*/
                    }
                }
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }

        });


        facebookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_button.performClick();
            }
        });

        googleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        loginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(in);
            }
        });


        registerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogPopup();
            }
        });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}