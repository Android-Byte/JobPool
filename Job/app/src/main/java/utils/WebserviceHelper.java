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
                                Constant.USER_NAME = data.getString("username");
                                Constant.EMAIL = data.getString("email");
                                Constant.PHONE_NUMBER = data.getString("phone");
                                Constant.LOCATION = data.getString("location");

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

                            forgotpassword[0] = object.getString("success");
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

                            changepassword[0] = object.getString("success");
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
