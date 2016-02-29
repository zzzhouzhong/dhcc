package com.dhcc;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RepoFragment extends Fragment {
	private View mViewRoot;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mViewRoot = inflater.inflate(R.layout.fragment_repo, null);
		return mViewRoot;
	}
}
