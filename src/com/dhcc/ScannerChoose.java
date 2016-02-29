package com.dhcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zxing.activity.CaptureActivity;

public class ScannerChoose extends Activity {

	private Button scanning; // 点击扫描按钮
	private TextView result; // 扫描显示对象

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scannerchoose);

		scanning = (Button) findViewById(R.id.btn_scanning);
		scanning.setOnClickListener(listener); // 注册按钮的点击事件

		result = (TextView) findViewById(R.id.result);
	}

	/**
	 * 点击事件处理
	 */
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.btn_scanning: {
				scanning();
				break;
			}
			}
		}
	};

	/**
	 * 转跳到扫描页面
	 * 
	 */
	private void scanning() {
		Intent intent = new Intent(this, CaptureActivity.class); // CaptureActivity是扫描的Activity类
		startActivityForResult(intent, 0); // 当前扫描完条码或二维码后,会回调当前类的onActivityResult方法,
	}

	/**
	 * 重写onActivityResult 方法 当前对象若以startActivityForResult方式转跳页面,当目标页面结束以后,会回调此方法
	 * 
	 * @param requestCode
	 *            该参数就是 startActivityForResult(intent, 0); 中的第二个参数值
	 * @param resultCode
	 *            是转跳的目标页面中,setResult(RESULT_OK, intent); 中的第一个参数值
	 * @param data
	 *            转跳的Intent对象,可以传递数值
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) { // 判断回调
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result"); // 这就获取了扫描的内容了
			result.setText(scanResult);
			Intent intent = new Intent(this, ProductActivity.class);
			intent.putExtra("BarCodeNum", scanResult );
			startActivity(intent);
		}
	}

}
