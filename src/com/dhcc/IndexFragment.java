package com.dhcc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.zip.Inflater;

import com.dhcc.utils.Utils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.zxing.activity.CaptureActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class IndexFragment extends Fragment implements OnClickListener {
	private PullToRefreshListView mPullRefreshListView;
	private View mViewRoot;
	private View scan;
	private TextView nav_top_tv;
	private RelativeLayout gotoScanner2;
	private ImageView icon_scan;
	private ListView actualListView;
	private SimpleAdapter mAdapter;
	private ViewGroup mainView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mViewRoot = inflater.inflate(R.layout.fragment_index, null);

		return mViewRoot;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		init();
		// super.onViewCreated(view, savedInstanceState);
	}

	private void init() {

		mPullRefreshListView = (PullToRefreshListView) mViewRoot.findViewById(R.id.lv_index_pullrefresh);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				if (!Utils.checkLogin(IndexFragment.this.getActivity(), new Intent()))
					return;

				String label = Utils.getLabelTime(IndexFragment.this.getActivity());

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				new GetDataTask().execute();
			}

		});

		mainView = (ViewGroup) LayoutInflater.from(this.getActivity()).inflate(R.layout.item_index, null);
		// mainView = (ViewGroup) inflater.
		actualListView = mPullRefreshListView.getRefreshableView();
		 actualListView.addHeaderView(mainView);
		mAdapter = new SimpleAdapter(getActivity(), new LinkedList<Map<String, String>>(), R.layout.item_index, null, null);
		actualListView.setAdapter(mAdapter);

		View container_nav_top = mViewRoot.findViewById(R.id.container_nav_top);
		container_nav_top.findViewById(R.id.saoyisao).setOnClickListener(this);
		container_nav_top.findViewById(R.id.icon_scan).setOnClickListener(this);
		container_nav_top.findViewById(R.id.goto_sign).setOnClickListener(this);
		gotoScanner2 = (RelativeLayout) mainView.findViewById(R.id.goto_choosescanner);
		gotoScanner2.setOnClickListener(this);
	}

	/**
	 * 转跳到扫描页面
	 * 
	 */
	private void scanning() {
		Intent intent = new Intent(this.getActivity(), CaptureActivity.class); // CaptureActivity是扫描的Activity类
		startActivityForResult(intent, 0); // 当前扫描完条码或二维码后,会回调当前类的onActivityResult方法,
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		Intent intent = new Intent();
		if (!Utils.checkLogin(this.getActivity(), intent))
			return;
		switch (id) {
		case R.id.saoyisao:
		case R.id.icon_scan:
		case R.id.goto_choosescanner:
			// intent.setClass(this.getActivity(), ScannerChoose.class);
			if (Build.MODEL.equalsIgnoreCase("i6200S")) {
				intent.setClass(this.getActivity(), BarCodeScannerActivity.class);
				startActivityForResult(intent, 0);
			} else {
				intent.setClass(this.getActivity(), CaptureActivity.class);
				startActivityForResult(intent, 0);
			}
			break;
		case R.id.goto_sign:
			intent.setClass(this.getActivity(), SignActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
}
