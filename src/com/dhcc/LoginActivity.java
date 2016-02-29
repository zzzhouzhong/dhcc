package com.dhcc;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dhcc.param.Constants;
import com.dhcc.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText et_username;
	private EditText et_password;
	private RequestQueue mQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();

	}

	private void init() {
		// TODO Auto-generated method stubs
		mQueue = Volley.newRequestQueue(this);

		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.goto_register).setOnClickListener(this);
		et_username = (EditText) findViewById(R.id.input_username);
		et_password = (EditText) findViewById(R.id.input_password);
		et_username.setText(DHCCApp.getUserName());
		et_password.setText(DHCCApp.getPassword());
		findViewById(R.id.startlogin).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.back:
			finish();
			break;
		case R.id.goto_register:
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.startlogin:
			login();
			break;
		default:
			break;
		}
	}

	private void login() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				connect();
			}
		}).start();
	}

	private void connect() {
		JSONObject params = packData();
		if (params == null)
			return;
		JsonObjectRequest mRequest = new JsonObjectRequest(Method.POST, Constants.URL_LOGIN, params, new Response.Listener<JSONObject>() {
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
		mQueue.add(mRequest);

	}

	// 解析返回数据
	protected void parseData(JSONObject result) {
		// TODO Auto-generated method stub
		try {
			if (result.getInt("returncode") == 0) {
				Utils.printToast(LoginActivity.this, "登录成功");
				Log.i("this", "success:data=" + result.getString("daya"));
				DHCCApp.setState(1);
				LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
				LoginActivity.this.finish();
			} else {
				Utils.printToast(LoginActivity.this, "登录失败");
				DHCCApp.setState(0);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 组装数据，json封装用户名密码
	private JSONObject packData() {
		String userName = et_username.getText().toString().trim();
		String password = et_password.getText().toString().trim();
		DHCCApp.setUserName(userName);
		DHCCApp.setPassword(password);
		if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
			Utils.printToast(this, "用户名或密码不能为空");
			return null;
		}
		JSONObject data = new JSONObject();
		try {
			data.put("password", password);
			data.put("user_name", userName);
			data.put("flag", DHCCApp.getUserType());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}
