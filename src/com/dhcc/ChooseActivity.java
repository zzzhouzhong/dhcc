package com.dhcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ChooseActivity extends Activity implements OnClickListener {

	private static final int TYPE_COMPANY = 1;
	private static final int TYPE_STAFF = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose);
		findViewById(R.id.login_company).setOnClickListener(this);
		findViewById(R.id.login_staff).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		Intent intent = new Intent(this, LoginActivity.class);
		switch (id) {
		case R.id.login_company:
			DHCCApp.setUserType(TYPE_COMPANY);
			startActivity(intent);
			break;
		case R.id.login_staff:
			DHCCApp.setUserType(TYPE_STAFF);
			startActivity(intent);
			break;
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}
}
