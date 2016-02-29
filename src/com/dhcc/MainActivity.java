package com.dhcc;

import com.dhcc.utils.Utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private int FGM_NUM = 5;
	private int current = 0;
	private int[] navBottomIds = new int[] { R.id.nav_bottom_0, R.id.nav_bottom_1, R.id.nav_bottom_2, R.id.nav_bottom_3, R.id.nav_bottom_4 };
	private int[] navActiveDrawableIds = new int[] { R.drawable.index_10, R.drawable.index_11, R.drawable.index_12, R.drawable.index_13, R.drawable.index_14 };
	private int[] navInactiveDrawableIds = new int[] { R.drawable.index_00, R.drawable.index_01, R.drawable.index_02, R.drawable.index_03, R.drawable.index_04 };
	private TextView[] navBottomViews = new TextView[5];
	private int[] fgmId = new int[] {};
	private View container_fragment;
	private View container_nav_bottom;
	private Fragment[] fgms = new Fragment[FGM_NUM];
	private FragmentManager fm;
	private FragmentTransaction ft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();

		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		// float width = dm.widthPixels * dm.density;
		// float height = dm.heightPixels * dm.density;
		// Log.i("this",dm.density+"");
		// Log.i("this",dm.widthPixels+":"+dm.heightPixels);
	}

	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		// super.onSaveInstanceState(outState);
		// //将这一行注释掉，阻止activity保存fragment的状态
	}

	private void init() {
		container_nav_bottom = findViewById(R.id.container_nav_bottom);
		container_fragment = findViewById(R.id.container_fragment);

		for (int i = 0; i < FGM_NUM; i++) {
			navBottomViews[i] = (TextView) container_nav_bottom.findViewById(navBottomIds[i]);
			navBottomViews[i].setOnClickListener(this);
		}
		fgms[0] = new IndexFragment();
		fgms[1] = new HistoryFragment();
		fgms[2] = new MessageFragment();
		fgms[3] = new RepoFragment();
		fgms[4] = new UserFragment();
		fm = getFragmentManager();
		ft = fm.beginTransaction();
		// ft.add(R.id.container_fragment, fgms[0], "Index");
		ft.add(R.id.container_fragment, fgms[0], "Index").add(R.id.container_fragment, fgms[1], "History").hide(fgms[1])
				.add(R.id.container_fragment, fgms[2], "Message").hide(fgms[2]).add(R.id.container_fragment, fgms[3], "Repo").hide(fgms[3])
				.add(R.id.container_fragment, fgms[4], "User").hide(fgms[4]);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (!Utils.checkLogin(this, new Intent()))
			return;

		int id = v.getId();
		for (int i = 0; i < FGM_NUM; i++) {
			if (id == navBottomIds[i]) {
				setFrontFragment(i);
			}
		}

	}

	private void setFrontFragment(int i) {
		// TODO Auto-generated method stub
		Log.i("this", "i=" + i);
		if (i == current)
			return;
		fm = getFragmentManager();
		ft = fm.beginTransaction();
		Drawable tmp;
		ft.show(fgms[i]);
		tmp = getResources().getDrawable(navActiveDrawableIds[i]);
		tmp.setBounds(0, 0, tmp.getMinimumWidth(), tmp.getMinimumHeight());
		navBottomViews[i].setCompoundDrawables(null, tmp, null, null);
		ft.hide(fgms[current]);
		tmp = getResources().getDrawable(navInactiveDrawableIds[current]);
		tmp.setBounds(0, 0, tmp.getMinimumWidth(), tmp.getMinimumHeight());
		navBottomViews[current].setCompoundDrawables(null, tmp, null, null);
		current = i;
		// ft.addToBackStack("");
		ft.commit();
	}
}
