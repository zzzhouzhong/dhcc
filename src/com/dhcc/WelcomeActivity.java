package com.dhcc;

import java.util.Timer;
import java.util.TimerTask;

import com.dhcc.param.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class WelcomeActivity extends Activity {
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wel);
		intent = new Intent();
		intent.setClass(this, MainActivity.class);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				startActivity(intent);
				WelcomeActivity.this.finish();
			}
		};
		timer.schedule(task, 1000 * 2); // 10秒后
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
