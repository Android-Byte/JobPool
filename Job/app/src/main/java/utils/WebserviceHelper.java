package utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

            case Constant.SINGUP:

                String[] regParm = new String[3];
                httppost = new HttpPost(Constant.SIGNUP_URL);
                Log.e("", "Constant.SIGNUP_URL : " + Constant.SIGNUP_URL);
                try {
                    MultipartEntity entity = new MultipartEntity();
                    try {
                        File file = new File("");
                        FileBody bin = new FileBody(file);
                        entity.addPart("image", bin);
                    } catch (Exception e) {
                        Log.v("Exception in Image", "" + e);
                    }

                    /*entity.addPart("user_type", new StringBody(Constant.USER_TYPE));
                    entity.addPart("device_token", new StringBody(Constant.TOKEN));*/

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
                    JSONObject object = null;

                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        stringBuilder.append(bufferedStrChunk);
                        encodeRes = stringBuilder.toString();
                        try {
                            object = new JSONObject(encodeRes);
                            Log.d("", "jsonObj responce... " + object);
                            regParm[0] = object.getString("success");
                            regParm[1] = object.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            regParm[0] = object.getString("success");
                            regParm[1] = object.getString("message");
                        }
                        break;
                    }

                    return regParm;

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
