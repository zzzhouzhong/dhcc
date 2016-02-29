package com.dhcc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dhcc.utils.Utils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MessageFragment extends Fragment {
	private View mViewRoot;
	private PullToRefreshListView mPullRefreshListView;
	private SimpleAdapter mAdapter;
	String testContent = "百度百科是一部内容开放、自由的网络百科全书,旨在创造一个涵盖所有领域知识,服务所有互联网用户的中文知识性百科全书。在这里你可以参与词条编辑,分享贡献你的知识。";
	private LinkedList<Map<String, String>> datas = new LinkedList<Map<String, String>>();
	private ListView actualListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mViewRoot = inflater.inflate(R.layout.fragment_message, null);

		return mViewRoot;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// super.onViewCreated(view, savedInstanceState);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		mPullRefreshListView = (PullToRefreshListView) mViewRoot
				.findViewById(R.id.lv_message_pullrefresh);
		// message_swipeRefresh.setColorSchemeColors(android.R.color.holo_blue_bright,
		// android.R.color.holo_green_light, android.R.color.holo_orange_light,
		// android.R.color.holo_red_light);

		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = Utils.getLabelTime(MessageFragment.this
								.getActivity());

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						new GetDataTask().execute();
					}

				});
		mPullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						Utils.printToast(MessageFragment.this.getActivity(),
								"最后一项");
					}
				});

		actualListView = mPullRefreshListView.getRefreshableView();

		mAdapter = new SimpleAdapter(getActivity(), getData(),
				R.layout.item_message, new String[] { "messageTime",
						"messageContent" }, new int[] { R.id.item_message_time,
						R.id.item_message_content });
		actualListView.setAdapter(mAdapter);
	}

	private List<? extends Map<String, ?>> getData() {
		// TODO Auto-generated method stub

		for (int i = 3; i > 0; i--) {
			Map<String, String> data = new HashMap<String, String>();
			data.put("messageTime", "2月25日 23:10");
			data.put("messageContent", testContent);
			datas.add(data);
		}
		return datas;
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
			Map<String, String> newData = new HashMap<String, String>();
			newData.put("messageTime", Utils.getLabelTime(getActivity()));
			newData.put("messageContent", "刷新后添加" + testContent);
			datas.addFirst(newData);
			mAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

}
