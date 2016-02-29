package com.dhcc;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.dhcc.param.Constants;
import com.dhcc.utils.Utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

public class DHCCApp extends Application {

	private static int state = 0;
	private static String userName;
	private static String password;
	private static SharedPreferences sp;
	private static int userType = 0;
	private RequestQueue mQueue;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences(Constants.SP_USERINFO, MODE_PRIVATE);
		state = Utils.getIntSp(sp, Constants.USER_STATE);
		userName = Utils.getStringSp(sp, Constants.USERNAME);
		password = Utils.getStringSp(sp, Constants.PASSWORD);
		mQueue = Volley.newRequestQueue(this);

		checkState();
	}

	private void checkState() {
		// TODO Auto-generated method stub
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, Constants.URL_LOGIN, packData(), new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				// TODO Auto-generated method stub
				parseData(arg0);
			}

		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
		mQueue.add(request);
	}

	private void parseData(JSONObject result) {
		// TODO Auto-generated method stub
		try {
			if (result.getInt("returncode") == 0) {
				Log.i("this", "success:data=" + result.getString("daya"));
				DHCCApp.setState(1);
			} else {
				DHCCApp.setState(0);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private JSONObject packData() {
		// TODO Auto-generated method stub
		JSONObject data = new JSONObject();
		try {
			data.put("password", password);
			data.put("user_name", userName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public static int getState() {
		return state;
	}

	public static void setState(int state) {
		Utils.writeSp(sp, Constants.USER_STATE, state);
		DHCCApp.state = state;
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		Utils.writeSp(sp, Constants.USERNAME, userName);
		DHCCApp.userName = userName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Utils.writeSp(sp, Constants.PASSWORD, password);
		DHCCApp.password = password;
	}

	public static int getUserType() {
		return userType;
	}

	public static void setUserType(int userType) {
		Utils.writeSp(sp, Constants.USER_TYPE, userType);
		DHCCApp.userType = userType;
	}
}
