package com.dhcc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.dhcc.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProductActivity extends Activity implements OnClickListener {
	private static final int RESULT_OK = 0;
	private String barCodeNum;
	private TextView jingxiaosang, dingdanhao, dingdanleixing, waibudingdanhao, tuhao, lingjianzhongwenming, tiaoxingma, shuliang, cangwei, rukushijian,
			chaxun;

	private ProgressBar progressbar;
	private EditText saotiaoma;

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case RESULT_OK:
				progressbar.setVisibility(View.GONE);
				if (msg.obj == null)
					return;
				JSONObject data = (JSONObject) msg.obj;
				showInfo(data);
				break;

			default:
				break;
			}
		}

		private void showInfo(JSONObject data) {
			try {

				if (data.getInt("returncode") == 1) {
					Utils.printToast(ProductActivity.this, "查无此码");
				} else {
					data = data.getJSONObject("daya");
					jingxiaosang.setText(getResources().getString(R.string.waibudingdanhao) + data.getString("user_id"));

					dingdanhao.setText(getResources().getString(R.string.dingdanhao) + data.getString("code_no"));

					dingdanleixing.setText(getResources().getString(R.string.dingdanleixing) + data.getString("product_category_id"));

					waibudingdanhao.setText(getResources().getString(R.string.waibudingdanhao) + data.getString("id"));

					tuhao.setText(getResources().getString(R.string.tuhao) + data.getString("product_id"));
					lingjianzhongwenming.setText(getResources().getString(R.string.lingjianzhongwenming) + data.getString("product_name"));
					tiaoxingma.setText(getResources().getString(R.string.tiaoxingma) + data.getString("product_code"));
					shuliang.setText(getResources().getString(R.string.shuliang) + data.getString("product_num"));
					cangwei.setText(getResources().getString(R.string.cangwei) + data.getString("id"));
					rukushijian.setText(getResources().getString(R.string.rukushijian) + data.getString("add_time"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
		saotiaoma = (EditText) findViewById(R.id.saotiaoma_result);

		chaxun = (TextView) findViewById(R.id.chaxun);
		chaxun.setOnClickListener(this);
		progressbar = (ProgressBar) findViewById(R.id.prgressbar);

		Intent intent = getIntent();
		barCodeNum = intent.getStringExtra("BarCodeNum");
		saotiaoma.setText(barCodeNum);

		findViewById(R.id.back).setOnClickListener(this);

		// barCodeNum = "7688164";
		jingxiaosang = (TextView) findViewById(R.id.jingxiaosang);
		dingdanhao = (TextView) findViewById(R.id.dingdanhao);
		dingdanleixing = (TextView) findViewById(R.id.dingdanleixing);
		waibudingdanhao = (TextView) findViewById(R.id.waibudingdanhao);
		tuhao = (TextView) findViewById(R.id.tuhao);
		lingjianzhongwenming = (TextView) findViewById(R.id.lingjianzhongwenming);
		tiaoxingma = (TextView) findViewById(R.id.tiaoxingma);
		shuliang = (TextView) findViewById(R.id.shuliang);
		cangwei = (TextView) findViewById(R.id.cangwei);
		rukushijian = (TextView) findViewById(R.id.rukushijian);

	}

	private void startRequestInfo() {
		progressbar.setVisibility(View.VISIBLE);

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				requestDetailedInfo();
			}
		}).start();
	}

	protected void requestDetailedInfo() {
		// TODO Auto-generated method stub

		if (TextUtils.isEmpty(barCodeNum) || barCodeNum.contains("w")) {
			return;
		}
		String path = "http://114.215.172.39/api/getProductInfo.php";
		// String path = "http://114.215.172.39/api/updateStatus.php";
		try {
			JSONObject data = new JSONObject();
			barCodeNum = saotiaoma.getText().toString().trim();
			if (TextUtils.isEmpty(barCodeNum)) {
				Utils.printToast(ProductActivity.this, "条码为空");
				return;
			}
			Log.i("this", barCodeNum + "");
			// barCodeNum = "0001";

			data.put("product_code", barCodeNum);
			// data.put("flag", 1);
			Log.i("this", data.toString());
			InputStream is = null;
			OutputStream os = null;

			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(50000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			os = conn.getOutputStream();
			os.write(data.toString().getBytes());

			int code = conn.getResponseCode();
			if (code == 200) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				is = conn.getInputStream();
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = is.read(buf)) > -1) {
					bos.write(buf, 0, len);
				}
				JSONObject result = new JSONObject(bos.toString());
				mHandler.obtainMessage(RESULT_OK, result).sendToTarget();

				Log.i("this", result.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.back:
			finish();
			break;
		case R.id.chaxun:
			startRequestInfo();
			break;
		default:
			break;
		}
	}
}
