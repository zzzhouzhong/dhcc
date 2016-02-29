package com.dhcc;

import com.dhcc.param.Constants;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserFragment extends Fragment implements OnClickListener {
	private View mViewRoot;
	private TextView user_username, user_state;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mViewRoot = inflater.inflate(R.layout.fragment_user, null);
		init();
		return mViewRoot;
	}

	private void init() {
		// TODO Auto-generated method stub
		mViewRoot.findViewById(R.id.user_info).setOnClickListener(this);

		mViewRoot.findViewById(R.id.user_feedback).setOnClickListener(this);
		mViewRoot.findViewById(R.id.user_help).setOnClickListener(this);
		mViewRoot.findViewById(R.id.user_about).setOnClickListener(this);
		mViewRoot.findViewById(R.id.user_set).setOnClickListener(this);

		mViewRoot.findViewById(R.id.user_portrait).setOnClickListener(this);
		user_username = (TextView) mViewRoot.findViewById(R.id.user_username);
		user_username.setOnClickListener(this);
		user_state = (TextView) mViewRoot.findViewById(R.id.user_state);
		user_state.setOnClickListener(this);

		if (DHCCApp.getState() == Constants.STATE_LOGIN) {
			user_username.setText(DHCCApp.getUserName());
			user_state.setText("已登录");
		} else {
			user_username.setText("请登录");
			user_state.setText("请登录");
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		int id = v.getId();

		switch (id) {
		case R.id.user_username:
			if (DHCCApp.getState() == Constants.STATE_LOGOUT) {
				intent.setClass(getActivity(), LoginActivity.class);
			}
			startActivity(intent);
			break;
		case R.id.user_state:
		case R.id.user_info:
			if (DHCCApp.getState() == Constants.STATE_LOGOUT) {
				intent.setClass(getActivity(), LoginActivity.class);
			} else {
				intent.setClass(getActivity(), UserInfoActivity.class);
			}
			startActivity(intent);
			break;
		case R.id.user_feedback:
			intent.setClass(getActivity(), FeedbackActivity.class);
			startActivity(intent);
			break;
		case R.id.user_help:

			break;
		case R.id.user_about:

			break;
		case R.id.user_set:
			intent.setClass(getActivity(), SetActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	private int getState() {
		return DHCCApp.getState();
	}
}
