package utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dtos.CandidateDTO;
import dtos.CompanyDTO;


//import org.apache.http.entity.mime.MultipartEntity;
@SuppressWarnings("deprecation")
public class WebserviceHelper extends AsyncTask<Void, Void, String[]> {

    private RequestReceiver mContext;
    @SuppressWarnings("unused")
    private String method = null;
    private Map<String, String> paramMap = new HashMap<String, String>();
    private String errorMessage;
    private boolean error_flag = false;
    ProgressDialog mProgressDialog;

    public static int action;

    ProgressDialog dialog;
    Activity mcont;

    public WebserviceHelper() {
    }

    public WebserviceHelper(RequestReceiver context, Activity mcontext) {
        mContext = context;
        mcont = mcontext;
        dialog = new ProgressDialog(mcontext);
    }

    WebserviceHelper(RequestReceiver context, String setMethod) {
        mContext = context;
        method = setMethod;
    }

    private void clearErrors() {
        this.errorMessage = null;
        this.error_flag = false;
    }

    public void setMethod(String m) {
        method = m;
    }

    public void addParam(String key, String value) {
        paramMap.put(key, value);
    }

    @Override
    protected void onPreExecute() {
        this.clearErrors();

        dialog.setMessage("Please Wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String[] doInBackground(Void... params) {

        Log.e("in background", "");
        Log.d("d  in background", "");
        // Create a newhome HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        JSONObject jsonObj = new JSONObject();
        HttpResponse response1 = null;
        HttpPost httppost = null;
        HttpGet httpGet = null;
        JSONObject jsonData = new JSONObject();
        switch (action) {

            case Constant.EMPLOYER_RAGISTRATION:
                String[] emloyer = new String[3];
                httppost = new HttpPost(Constant.EMPLOYER_RAGISTRATION_URL);
                try {
                    try {

                        jsonData.accumulate("company_name", Constant.COMPANY_NAME);
                        jsonData.accumulate("contact_person", Constant.CONTACTPERSON);
                        jsonData.accumulate("email", Constant.EMAIL);
                        jsonData.accumulate("password", Constant.PASSWORD);
                        jsonData.accumulate("phone", Constant.PHONE_NUMBER);
                        jsonData.accumulate("current_requirment", Constant.CURRENT_REQUIRMENT);

                        jsonData.accumulate("experience", Constant.EXPERIENCE);
                        jsonData.accumulate("skill", Constant.SKILLES);
                        jsonData.accumulate("job_role", Constant.JOBROLL);
                        jsonData.accumulate("location", Constant.LOCATION);
                        jsonData.accumulate("address", Constant.ADDRESS);

                        Log.e("","URL "+Constant.EMPLOYER_RAGISTRATION_URL);
                        Log.e("Json : ",""+jsonData.toString(5));
                        StringEntity se = new StringEntity(jsonData.toString());
                        httppost.setEntity(se);
                        httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");
                        try {
                            response1 =httpclient.execute(httppost);
                            if(response1!=null){
                                Log.e("","responce");
                                jsonData.has("success");
                            }else {
                                Log.e("","Null responce");
                            }
                            response1.getStatusLine().getStatusCode();
                            StatusLine statusLine = response1.getStatusLine();
                            Log.e("myapp", "response statau.." + response1.getStatusLine().getStatusCode());
                            Log.e("myapp", "response.. " + response1.getEntity());

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    InputStream inputStream = response1.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF8"),8);
                    StringBuilder sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line="0";
                    String result = "";
                    JSONObject object = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        result = sb.toString();
                        Log.e("","encodeRes : "+result);

                        try {
                            object = new JSONObject(result);
                            Log.d("","jsonObj responce... "+object);

                            emloyer[0] = object.getString("success");
                            emloyer[1] = object.getString("message");

                            try {

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            emloyer[0] = object.getString("success");
                            emloyer[1] = object.getString("message");
                        }
                        break;
                    }
                    return emloyer;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Constant.CONDIDATE_RAGISTRATION:
                String[] signParm = new String[3];
                httppost = new HttpPost(Constant.CONDIDATE_RAGISTRATION_URL);
                try {
                    try {

                        jsonData.accumulate("name", Constant.NAME);
                        jsonData.accumulate("user_name", Constant.USER_NAME);
                        jsonData.accumulate("email", Constant.EMAIL);
                        jsonData.accumulate("password", Constant.PASSWORD);
                        jsonData.accumulate("phone", Constant.PHONE_NUMBER);
                        jsonData.accumulate("gender", Constant.GENDER);

                        Log.e("","URL "+Constant.CONDIDATE_RAGISTRATION_URL);
                        Log.e("Json : ",""+jsonData.toString(5));
                        StringEntity se = new StringEntity(jsonData.toString());
                        httppost.setEntity(se);
                        httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");
                        try {
                            response1 =httpclient.execute(httppost);
                            if(response1!=null){
                                Log.e("","responce");
                                jsonData.has("success");
                            }else {
                                Log.e("","Null responce");
                            }
                            response1.getStatusLine().getStatusCode();
                            StatusLine statusLine = response1.getStatusLine();
                            Log.e("myapp", "response statau.." + response1.getStatusLine().getStatusCode());
                            Log.e("myapp", "response.. " + response1.getEntity());

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    InputStream inputStream = response1.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF8"),8);
                    StringBuilder sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line="0";
                    String result = "";
                    JSONObject object = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        result = sb.toString();
                        Log.e("","encodeRes : "+result);

                        try {
                            object = new JSONObject(result);
                            Log.d("","jsonObj responce... "+object);

                            signParm[0] = object.getString("success");
                            signParm[1] = object.getString("message");

                            try {

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            signParm[0] = object.getString("success");
                            signParm[1] = object.getString("message");
                        }
                        break;
                    }
                    return signParm;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Constant.LOGIN:
                String[] login = new String[3];
                httppost = new HttpPost(Constant.LOGIN_URL);
                try {
                    try {

                        jsonData.accumulate("email", Constant.EMAIL);
                        jsonData.accumulate("password", Constant.PASSWORD);
                        jsonData.accumulate("fbid", Constant.FB_ID);
                        jsonData.accumulate("gmail_id", Constant.GOOGLE_ID);


                        Log.e("","URL "+Constant.EMPLOYER_RAGISTRATION_URL);
                        Log.e("Json : ",""+jsonData.toString(5));
                        StringEntity se = new StringEntity(jsonData.toString());
                        httppost.setEntity(se);
                        httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");
                        try {
                            response1 =httpclient.execute(httppost);
                            if(response1!=null){
                                Log.e("","responce");
                                jsonData.has("success");
                            }else {
                                Log.e("","Null responce");
                            }
                            response1.getStatusLine().getStatusCode();
                            StatusLine statusLine = response1.getStatusLine();
                            Log.e("myapp", "response statau.." + response1.getStatusLine().getStatusCode());
                            Log.e("myapp", "response.. " + response1.getEntity());

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    InputStream inputStream = response1.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF8"),8);
                    StringBuilder sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line="0";
                    String result = "";
                    JSONObject object = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        result = sb.toString();
                        Log.e("","encodeRes : "+result);

                        try {
                            object = new JSONObject(result);
                            Log.d("","jsonObj responce... "+object);

                            login[0] = object.getString("success");
                            login[1] = object.getString("message");


                            try {
                                JSONObject data =  object.getJSONObject("data");
                                Constant.USER_ID = data.getString("user_id");
                                try{
                                    Constant.USER_NAME = data.getString("username");
                                }catch (Exception e){
                                    e.printStackTrace();
                                    Constant.COMPANY_NAME = data.getString("company_name");
                                }

                                Constant.EMAIL = data.getString("email");
                                Constant.PHONE_NUMBER = data.getString("phone");
                                Constant.LOCATION = data.getString("location");
                                Constant.USER_TYPE = data.getString("user_type");

                            }catch (Exception e){
                                e.printStackTrace();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            login[0] = object.getString("success");
                            login[1] = object.getString("message");
                        }
                        break;
                    }
                    return login;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Constant.LOGOUT:
                String[] logout = new String[3];
                httppost = new HttpPost(Constant.LOGOUT_URL);
                try {
                    try {

                        jsonData.accumulate("email", Constant.EMAIL);

                        Log.e("","URL "+Constant.LOGOUT_URL);
                        Log.e("Json : ",""+jsonData.toString(5));
                        StringEntity se = new StringEntity(jsonData.toString());
                        httppost.setEntity(se);
                        httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");
                        try {
                            response1 =httpclient.execute(httppost);
                            if(response1!=null){
                                Log.e("","responce");
                                jsonData.has("success");
                            }else {
                                Log.e("","Null responce");
                            }
                            response1.getStatusLine().getStatusCode();
                            StatusLine statusLine = response1.getStatusLine();
                            Log.e("myapp", "response statau.." + response1.getStatusLine().getStatusCode());
                            Log.e("myapp", "response.. " + response1.getEntity());

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    InputStream inputStream = response1.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF8"),8);
                    StringBuilder sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line="0";
                    String result = "";
                    JSONObject object = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        result = sb.toString();
                        Log.e("","encodeRes : "+result);

                        try {
                            object = new JSONObject(result);
                            Log.d("","jsonObj responce... "+object);

                            logout[0] = object.getString("success");
                            logout[1] = object.getString("message");

                            try {

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            logout[0] = object.getString("success");
                            logout[1] = object.getString("message");
                        }
                        break;
                    }
                    return logout;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Constant.FORGOTPASSWORD:
                String[] forgotpassword = new String[3];
                httppost = new HttpPost(Constant.FORGOT_URL);
                try {
                    try {

                        jsonData.accumulate("email", Constant.EMAIL);

                        Log.e("","URL "+Constant.FORGOT_URL);
                        Log.e("Json : ",""+jsonData.toString(5));
                        StringEntity se = new StringEntity(jsonData.toString());
                        httppost.setEntity(se);
                        httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");
                        try {
                            response1 =httpclient.execute(httppost);
                            if(response1!=null){
                                Log.e("","responce");
                                jsonData.has("success");
                            }else {
                                Log.e("","Null responce");
                            }
                            response1.getStatusLine().getStatusCode();
                            StatusLine statusLine = response1.getStatusLine();
                            Log.e("myapp", "response statau.." + response1.getStatusLine().getStatusCode());
                            Log.e("myapp", "response.. " + response1.getEntity());

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    InputStream inputStream = response1.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF8"),8);
                    StringBuilder sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line="0";
                    String result = "";
                    JSONObject object = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        result = sb.toString();
                        Log.e("","encodeRes : "+result);

                        try {
                            object = new JSONObject(result);
                            Log.d("","jsonObj responce... "+object);

                            forgotpassword[0] ="010"+ object.getString("success");
                            forgotpassword[1] = object.getString("message");

                            try {

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            forgotpassword[0] = object.getString("success");
                            forgotpassword[1] = object.getString("message");
                        }
                        break;
                    }
                    return forgotpassword;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case Constant.CHANGEPASSWORD:
                String[] changepassword = new String[3];
                httppost = new HttpPost(Constant.CHANGE_PASSWORD_URL);
                try {
                    try {

                        jsonData.accumulate("email", Constant.EMAIL);
                        jsonData.accumulate("old_password", Constant.OLDPASSWORD);
                        jsonData.accumulate("new_password", Constant.PASSWORD);

                        Log.e("","URL "+Constant.FORGOT_URL);
                        Log.e("Json : ",""+jsonData.toString(5));
                        StringEntity se = new StringEntity(jsonData.toString());
                        httppost.setEntity(se);
                        httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");
                        try {
                            response1 =httpclient.execute(httppost);
                            if(response1!=null){
                                Log.e("","responce");
                                jsonData.has("success");
                            }else {
                                Log.e("","Null responce");
                            }
                            response1.getStatusLine().getStatusCode();
                            StatusLine statusLine = response1.getStatusLine();
                            Log.e("myapp", "response statau.." + response1.getStatusLine().getStatusCode());
                            Log.e("myapp", "response.. " + response1.getEntity());

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    InputStream inputStream = response1.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF8"),8);
                    StringBuilder sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line="0";
                    String result = "";
                    JSONObject object = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        result = sb.toString();
                        Log.e("","encodeRes : "+result);

                        try {
                            object = new JSONObject(result);
                            Log.d("","jsonObj responce... "+object);

                            changepassword[0] = "10"+object.getString("success");
                            changepassword[1] = object.getString("message");

                            try {

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            changepassword[0] = object.getString("success");
                            changepassword[1] = object.getString("message");
                        }
                        break;
                    }
                    return changepassword;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Constant.SOCIALLOGIN:
                String[] social_login = new String[3];
                httppost = new HttpPost(Constant.SOCIAL_LOGIN_URL);
                try {
                    try {

                        jsonData.accumulate("fbid", Constant.FB_ID);
                        jsonData.accumulate("email", Constant.EMAIL);
                        jsonData.accumulate("gmail_id", Constant.GOOGLE_ID);

                        Log.e("","URL "+Constant.SOCIAL_LOGIN_URL);
                        Log.e("Json : ",""+jsonData.toString(5));
                        StringEntity se = new StringEntity(jsonData.toString());
                        httppost.setEntity(se);
                        httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");
                        try {
                            response1 =httpclient.execute(httppost);
                            if(response1!=null){
                                Log.e("","responce");
                                jsonData.has("success");
                            }else {
                                Log.e("","Null responce");
                            }
                            response1.getStatusLine().getStatusCode();
                            StatusLine statusLine = response1.getStatusLine();
                            Log.e("myapp", "response statau.." + response1.getStatusLine().getStatusCode());
                            Log.e("myapp", "response.. " + response1.getEntity());

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    InputStream inputStream = response1.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF8"),8);
                    StringBuilder sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line="0";
                    String result = "";
                    JSONObject object = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        result = sb.toString();
                        Log.e("","encodeRes : "+result);

                        try {
                            object = new JSONObject(result);
                            Log.d("","jsonObj responce... "+object);

                            social_login[0] = object.getString("success");
                            social_login[1] = object.getString("message");


                            try {
                                JSONObject data =  object.getJSONObject("data");

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            social_login[0] = object.getString("success");
                            social_login[1] = object.getString("message");
                        }
                        break;
                    }
                    return social_login;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Constant.GET_COMPANY_PROFILE:
                String[] getprofile = new String[3];
                httppost = new HttpPost(Constant.GET_PROFILE_URL);
                try {
                    try {

                        jsonData.accumulate("email", Constant.EMAIL);

                        Log.e("","URL "+Constant.GET_PROFILE_URL);
                        Log.e("Json : ",""+jsonData.toString(1));
                        StringEntity se = new StringEntity(jsonData.toString());
                        httppost.setEntity(se);
                        httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");
                        try {
                            response1 =httpclient.execute(httppost);
                            if(response1!=null){
                                Log.e("","responce");
                                jsonData.has("success");
                            }else {
                                Log.e("","Null responce");
                            }
                            response1.getStatusLine().getStatusCode();
                            StatusLine statusLine = response1.getStatusLine();
                            Log.e("myapp", "response statau.." + response1.getStatusLine().getStatusCode());
                            Log.e("myapp", "response.. " + response1.getEntity());

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    InputStream inputStream = response1.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF8"),8);
                    StringBuilder sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line="0";
                    String result = "";
                    JSONObject object = null;
                    Global.companylist.clear();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        result = sb.toString();
                        Log.e("","encodeRes : "+result);

                        try {
                            object = new JSONObject(result);
                            Log.d("","jsonObj responce... "+object);
                            getprofile[0] = "0"+object.getString("success");
                            getprofile[1] = object.getString("message");
                            try {
                                JSONObject object1 = object.getJSONObject("data");
                                CompanyDTO companyDTO = new CompanyDTO();

                                companyDTO.setEmployer_id(object1.getString("employer_id"));
                                companyDTO.setCompany_name(object1.getString("company_name"));
                                companyDTO.setContact_person(object1.getString("contact_person"));
                                companyDTO.setEmail(object1.getString("email"));
                                companyDTO.setPhone(object1.getString("phone"));
                                companyDTO.setCurrent_requirment(object1.getString("current_requirment"));
                                companyDTO.setExperience(object1.getString("experience"));
                                companyDTO.setSkill(object1.getString("skill"));
                                companyDTO.setJob_role(object1.getString("job_role"));
                                companyDTO.setLocation(object1.getString("location"));
                                companyDTO.setAddress(object1.getString("address"));
                                companyDTO.setExp_date(object1.getString("exp_date"));
                                companyDTO.setEmppackage(object1.getString("package"));
                                companyDTO.setAmount(object1.getString("amount"));
                                companyDTO.setUser_type(object1.getString("user_type"));

                                Global.companylist.add(companyDTO);
                                Log.e("","List Size : "+Global.companylist.size());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            getprofile[0] = object.getString("success");
                            getprofile[1] = object.getString("message");
                        }
                        break;
                    }
                    return getprofile;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Constant.GET_CANDIDATE_PROFILE:
                String[] getcandidateprofile = new String[3];
                httppost = new HttpPost(Constant.GET_PROFILE_URL);
                try {
                    try {

                        jsonData.accumulate("email", Constant.EMAIL);

                        Log.e("","URL "+Constant.GET_PROFILE_URL);
                        Log.e("Json : ",""+jsonData.toString(1));
                        StringEntity se = new StringEntity(jsonData.toString());
                        httppost.setEntity(se);
                        httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");
                        try {
                            response1 =httpclient.execute(httppost);
                            if(response1!=null){
                                Log.e("","responce");
                                jsonData.has("success");
                            }else {
                                Log.e("","Null responce");
                            }
                            response1.getStatusLine().getStatusCode();
                            StatusLine statusLine = response1.getStatusLine();
                            Log.e("myapp", "response statau.." + response1.getStatusLine().getStatusCode());
                            Log.e("myapp", "response.. " + response1.getEntity());

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    InputStream inputStream = response1.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF8"),8);
                    StringBuilder sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line="0";
                    String result = "";
                    JSONObject object = null;
                    Global.candidatelist.clear();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        result = sb.toString();
                        Log.e("","encodeRes : "+result);

                        try {
                            object = new JSONObject(result);
                            Log.d("","jsonObj responce... "+object);
                            getcandidateprofile[0] = "00"+ object.getString("success");
                            getcandidateprofile[1] = object.getString("message");
                            try {
                                JSONObject object1 = object.getJSONObject("data");
                                CandidateDTO candidateDTO = new CandidateDTO();

                                candidateDTO.setName(object1.getString("name"));
                                candidateDTO.setPhone(object1.getString("phone"));
                                candidateDTO.setGender(object1.getString("gender"));
                                candidateDTO.setLocation(object1.getString("location"));
                                candidateDTO.setExperience(object1.getString("experience"));
                                candidateDTO.setSkill(object1.getString("skill"));
                                candidateDTO.setStrength(object1.getString("strength"));
                                candidateDTO.setExpected_salary(object1.getString("expected_salary"));
                                candidateDTO.setAddress(object1.getString("address"));
                                candidateDTO.setPrefered_location(object1.getString("prefered_location"));

                                candidateDTO.setObjective(object1.getString("objective"));
                                candidateDTO.setBrief_description(object1.getString("brief_description"));
                                candidateDTO.setEmail(object1.getString("email"));
                                candidateDTO.setUser_type(object1.getString("user_type"));

                                Global.candidatelist.add(candidateDTO);

                                Log.e("","Size of list : "+Global.candidatelist.size());

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            getcandidateprofile[0] = object.getString("success");
                            getcandidateprofile[1] = object.getString("message");
                        }
                        break;
                    }
                    return getcandidateprofile;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Constant.UPDATE_CANDIDATE_PROFILE:
                String[] updatecandidate = new String[3];
                httppost = new HttpPost(Constant.UPDATE_CANDIDATE_PROFILE_URL);
                try {
                    try {

                        jsonData.accumulate("name", Constant.USER_NAME);
                        jsonData.accumulate("phone", Constant.PHONE_NUMBER);
                        jsonData.accumulate("gender", "");
                        jsonData.accumulate("location", Constant.LOCATION);
                        jsonData.accumulate("experience", Constant.EXPERIENCE);
                        jsonData.accumulate("skill", Constant.SKILLES);
                        jsonData.accumulate("strength", Constant.STRENGHT);
                        jsonData.accumulate("expected_salary", Constant.EXP_SALARY);
                        jsonData.accumulate("address", Constant.ADDRESS);
                        jsonData.accumulate("prefered_location", "");
                        jsonData.accumulate("objective", Constant.OBJECTIVE);
                        jsonData.accumulate("brief_description", Constant.BRIEFDESCRIPTION);
                        jsonData.accumulate("email", Constant.EMAIL);


                        Log.e("","URL "+Constant.UPDATE_CANDIDATE_PROFILE_URL);
                        Log.e("Json : ",""+jsonData.toString(12));
                        StringEntity se = new StringEntity(jsonData.toString());
                        httppost.setEntity(se);
                        httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");
                        try {
                            response1 =httpclient.execute(httppost);
                            if(response1!=null){
                                Log.e("","responce");
                                jsonData.has("success");
                            }else {
                                Log.e("","Null responce");
                            }
                            response1.getStatusLine().getStatusCode();
                            StatusLine statusLine = response1.getStatusLine();
                            Log.e("myapp", "response statau.." + response1.getStatusLine().getStatusCode());
                            Log.e("myapp", "response.. " + response1.getEntity());

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    InputStream inputStream = response1.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF8"),8);
                    StringBuilder sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line="0";
                    String result = "";
                    JSONObject object = null;
                    Global.candidatelist.clear();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        result = sb.toString();
                        Log.e("","encodeRes : "+result);

                        try {
                            object = new JSONObject(result);
                            Log.d("","jsonObj responce... "+object);
                            updatecandidate[0] = object.getString("success");
                            updatecandidate[1] = object.getString("message");
                            try {
                                JSONObject object1 = object.getJSONObject("data");
                                CandidateDTO candidateDTO = new CandidateDTO();

                                candidateDTO.setName(object1.getString("name"));
                                candidateDTO.setPhone(object1.getString("phone"));
                                candidateDTO.setGender(object1.getString("gender"));
                                candidateDTO.setLocation(object1.getString("location"));
                                candidateDTO.setExperience(object1.getString("experience"));
                                candidateDTO.setSkill(object1.getString("skill"));
                                candidateDTO.setStrength(object1.getString("strength"));
                                candidateDTO.setExpected_salary(object1.getString("expected_salary"));
                                candidateDTO.setAddress(object1.getString("address"));
                                candidateDTO.setPrefered_location(object1.getString("prefered_location"));
                                candidateDTO.setObjective(object1.getString("objective"));
                                candidateDTO.setBrief_description(object1.getString("brief_description"));
                                candidateDTO.setEmail(object1.getString("email"));
                                candidateDTO.setUser_type(object1.getString("user_type"));

                                Global.candidatelist.add(candidateDTO);
                                Log.e("","Size of list : "+Global.candidatelist.size());

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            updatecandidate[0] = object.getString("success");
                            updatecandidate[1] = object.getString("message");
                        }
                        break;
                    }
                    return updatecandidate;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Constant.UPDATE_EMPLOYER_PROFILE:
                String[] updateemployer = new String[3];
                httppost = new HttpPost(Constant.UPDATE_EMPLOYER_PROFILE_URL);
                try {
                    try {

                        jsonData.accumulate("company_name", Constant.COMPANY_NAME);
                        jsonData.accumulate("contact_person", Constant.CONTACTPERSON);
                        jsonData.accumulate("phone", Constant.PHONE_NUMBER);
                        jsonData.accumulate("current_requirment", Constant.CURRENT_REQUIRMENT);
                        jsonData.accumulate("experience", Constant.EXPERIENCE);
                        jsonData.accumulate("skill", Constant.SKILLES);
                        jsonData.accumulate("job_role", Constant.JOBROLL);
                        jsonData.accumulate("location", Constant.LOCATION);
                        jsonData.accumulate("address", Constant.ADDRESS);
                        jsonData.accumulate("email", Constant.EMAIL);

                        Log.e("","URL "+Constant.UPDATE_EMPLOYER_PROFILE_URL);
                        Log.e("Json : ",""+jsonData.toString(12));
                        StringEntity se = new StringEntity(jsonData.toString());
                        httppost.setEntity(se);
                        httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");
                        try {
                            response1 =httpclient.execute(httppost);
                            if(response1!=null){
                                Log.e("","responce");
                                jsonData.has("success");
                            }else {
                                Log.e("","Null responce");
                            }
                            response1.getStatusLine().getStatusCode();
                            StatusLine statusLine = response1.getStatusLine();
                            Log.e("myapp", "response statau.." + response1.getStatusLine().getStatusCode());
                            Log.e("myapp", "response.. " + response1.getEntity());

                        } catch (ClientProtocolException e) {
                            e.printStackTrace();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    InputStream inputStream = response1.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF8"),8);
                    StringBuilder sb = new StringBuilder();
                    sb.append(reader.readLine() + "\n");
                    String line="0";
                    String result = "";
                    JSONObject object = null;
                    Global.candidatelist.clear();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        result = sb.toString();
                        Log.e("","encodeRes : "+result);

                        try {
                            object = new JSONObject(result);
                            Log.d("","jsonObj responce... "+object);
                            updateemployer[0] = object.getString("success");
                            updateemployer[1] = object.getString("message");
                            try {
                                JSONObject object1 = object.getJSONObject("data");
                                CandidateDTO candidateDTO = new CandidateDTO();

                                candidateDTO.setName(object1.getString("name"));
                                candidateDTO.setPhone(object1.getString("phone"));
                                candidateDTO.setGender(object1.getString("gender"));
                                candidateDTO.setLocation(object1.getString("location"));
                                candidateDTO.setExperience(object1.getString("experience"));
                                candidateDTO.setSkill(object1.getString("skill"));
                                candidateDTO.setStrength(object1.getString("strength"));
                                candidateDTO.setExpected_salary(object1.getString("expected_salary"));
                                candidateDTO.setAddress(object1.getString("address"));
                                candidateDTO.setPrefered_location(object1.getString("prefered_location"));
                                candidateDTO.setObjective(object1.getString("objective"));
                                candidateDTO.setBrief_description(object1.getString("brief_description"));
                                candidateDTO.setEmail(object1.getString("email"));
                                candidateDTO.setUser_type(object1.getString("user_type"));

                                Global.candidatelist.add(candidateDTO);
                                Log.e("","Size of list : "+Global.candidatelist.size());

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            updateemployer[0] = object.getString("success");
                            updateemployer[1] = object.getString("message");
                        }
                        break;
                    }
                    return updateemployer;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Constant.UPDATE_EMPLOYER_PIC:

                String[] updateempPic = new String[3];
                httppost = new HttpPost(Constant.UPDATE_EMPLOYER_PIC_URL);
                Log.e("", "Constant.SIGNUP_URL : " + Constant.UPDATE_EMPLOYER_PIC_URL);
                try {
                    MultipartEntity entity = new MultipartEntity();
                    try {
                        Log.e("", "ImagePathe : " + Constant.USER_IMAGE);
                        File file = new File(Constant.USER_IMAGE);
                        FileBody bin = new FileBody(file);
                        entity.addPart("image", bin);
                    } catch (Exception e) {
                        Log.v("Exception in Image", "" + e);
                    }
                    entity.addPart("user_type", new StringBody(Constant.USER_TYPE));

                    httppost.setEntity(entity);
                    try {
                        response1 = httpclient.execute(httppost);
                        Log.d("myapp", "response " + response1.getEntity());
                        Log.e("myapp", "response.. statau.." + response1.getStatusLine().getStatusCode());
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    InputStream inputStream = response1.getEntity().getContent();
                    InputStreamReader inputStreamReader = new InputStreamReader(
                            inputStream);
                    BufferedReader bufferedReader = new BufferedReader(
                            inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String bufferedStrChunk = null;
                    String encodeRes = "";
                    JSONObject jsondata = null;
                    JSONObject object = null;
                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        stringBuilder.append(bufferedStrChunk);
                        encodeRes = stringBuilder.toString();
                        try {
                            object = new JSONObject(encodeRes);
                            Log.d("", "jsonObj responce... " + object);
                            updateempPic[0] = object.getString("success");
                            updateempPic[1] = object.getString("message");

                            jsondata = object.getJSONObject("data");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            updateempPic[0] = object.getString("success");
                            updateempPic[1] = object.getString("message");
                        }
                        break;
                    }
                    return updateempPic;
                } catch (Exception e) {
                    // TODO: handle exception
                }
                break;

            default:
                break;
        }
        return null;
    }


    @Override
    protected void onPostExecute(String[] result) {

        if (dialog.isShowing()) {
            dialog.cancel();
        }
        try {
            ((RequestReceiver) mContext).requestFinished(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;

    }

    public boolean errors_occurred() {
        return this.error_flag;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    private void releaseListMemory() {

    }

    public void setAction(int action) {
        WebserviceHelper.action = action;
    }

}
