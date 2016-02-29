package com.dhcc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class FeedbackActivity extends Activity implements OnClickListener {
	private EditText et_email, et_feedback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		findViewById(R.id.btn_submit_feedback).setOnClickListener(this);
		et_email = (EditText) findViewById(R.id.et_email);
		et_feedback = (EditText) findViewById(R.id.et_feedback);
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btn_submit_feedback:
			break;
		case R.id.back:
			this.finish();
			break;
		default:
			break;
		}
	}

}
