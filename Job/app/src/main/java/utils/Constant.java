package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*Define all constant variables here
 */
public class Constant {

	public static final int CONDIDATE_RAGISTRATION = 1;
	public static final int EMPLOYER_RAGISTRATION = 2;
	public static final int LOGIN = 3;
	public static final int LOGOUT = 4;
	public static final int FORGOTPASSWORD = 5;
	public static final int CHANGEPASSWORD = 6;

	public static String BASE_URL ="http://smtgroup.in/jobpools/wdiapi/index.php/";

	public static String CONDIDATE_RAGISTRATION_URL = BASE_URL+"candidate_register";
	public static String EMPLOYER_RAGISTRATION_URL = BASE_URL+"employer_register";
	public static String LOGIN_URL = BASE_URL+"user_login";
	public static String LOGOUT_URL = BASE_URL+"candidate_logout";
	public static String FORGOT_URL = BASE_URL+"candidate_forgot_password";
	public static String CHANGE_PASSWORD_URL = BASE_URL+"candidate_changed_password";


	public static String NAME="";
	public static String USER_NAME="";
	public static String EMAIL="";
	public static String PASSWORD="";
	public static String PHONE_NUMBER="";
	public static String GENDER="";
	public static String USER_ID="";

	/*EMPLOYER REGISTER*/
	public static String COMPANY_NAME="";
	public static String CONTACTPERSON="";
	public static String CURRENT_REQUIRMENT="";
	public static String EXPERIENCE="";
	public static String SKILLES="";
	public static String JOBROLL="";
	public static String LOCATION="";
	public static String ADDRESS="";

	public static String OLDPASSWORD="";

	public static String EMAIL_PETTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9][.com])?)*$";
	/*^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$*/

	public static boolean emailValidation(String emailtxt) {
		boolean isValid = false;
		CharSequence inputStr = emailtxt;
		Pattern pattern = Pattern.compile(Constant.EMAIL_PETTERN, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean checkInternetConnection(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}

	public static String gerDeviceId(Context context) {
		String android_id = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);

		return android_id;
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		params.height = params.height+60;
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	public static String getDeviceName() {
		String str = android.os.Build.MODEL;
		return str;
	}

	public static int getDeviceWidth(Context context) {
		int width;

		DisplayMetrics displayMetrics = context.getResources()
				.getDisplayMetrics();
		width = displayMetrics.widthPixels;

		return width;
	}
}
