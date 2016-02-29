package com.dhcc;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dhcc.param.Constants;
import com.dhcc.utils.Utils;

public class RegisterActivity extends Activity implements OnClickListener {
	private EditText et_newusername;
	private EditText et_newpassword;
	public static final int RESULT_OK = 0;
	private RequestQueue mQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		mQueue = Volley.newRequestQueue(this);

		et_newusername = (EditText) findViewById(R.id.new_username);
		et_newpassword = (EditText) findViewById(R.id.new_password);
		findViewById(R.id.new_validationcode);
		findViewById(R.id.startregister).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.back:
			finish();
			break;
		case R.id.startregister:
			startRegister();
			break;
		default:
			break;
		}
	}

	private void startRegister() {
		JSONObject params = packData();
		if (params == null)
			return;
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, Constants.URL_REGISTER, params, new Response.Listener<JSONObject>() {

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

	private JSONObject packData() {
		// TODO Auto-generated method stub
		String newusername = et_newusername.getText().toString().trim();
		String newpassword = et_newpassword.getText().toString().trim();
		if (TextUtils.isEmpty(newusername) || TextUtils.isEmpty(newpassword)) {
			Utils.printToast(this, "用户名或密码不能为空");
			return null;
		}
		JSONObject data = new JSONObject();
		try {
			data.put("user_name", newusername);
			data.put("password", newpassword);
			data.put("flag", DHCCApp.getUserType());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	private void parseData(JSONObject data) {
		// TODO Auto-generated method stub
		try {
			if (data != null && data.getInt("returncode") == 0) {

				Utils.printToast(RegisterActivity.this, "注册成功");
				Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
				startActivity(intent);
				RegisterActivity.this.finish();
			} else {
				Utils.printToast(RegisterActivity.this, "注册失败" + data.getString("message"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
