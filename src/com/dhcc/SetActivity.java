package com.dhcc;

import com.dhcc.param.Constants;
import com.dhcc.utils.Utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SetActivity extends Activity implements OnClickListener {
	private TextView btn_logout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		findViewById(R.id.back).setOnClickListener(this);
		btn_logout = (TextView) findViewById(R.id.btn_logout);
		btn_logout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		if (DHCCApp.getState() != Constants.STATE_LOGIN) {
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
			return;
		}
		int id = v.getId();

		switch (id) {
		case R.id.back:
			this.finish();
			break;
		case R.id.btn_logout:
			if (DHCCApp.getState() == Constants.STATE_LOGIN) {

				DHCCApp.setState(Constants.STATE_LOGOUT);
				intent.setClass(this, WelcomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				this.finish();
			} else {
				Utils.printToast(this, "用户未登录");
			}
			break;

		default:
			break;
		}
	}
}
