package com.dhcc;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanManager;
import android.device.scanner.configuration.Triggering;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class BarCodeScannerActivity extends Activity {

	private final static String SCAN_ACTION = "urovo.rcv.message";// 扫描结束action

	private EditText showScanResult;
	private Button btn;
	private Button mScan;
	private Button mClose;
	private int type;
	private int outPut;

	private Vibrator mVibrator;
	private ScanManager mScanManager;
	private SoundPool soundpool = null;
	private int soundid;
	private String barcodeStr;
	private boolean isScaning = false;
	private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			isScaning = false;
			soundpool.play(soundid, 1, 1, 0, 0, 1);
			showScanResult.setText("");
			mVibrator.vibrate(100);

			byte[] barcode = intent.getByteArrayExtra("barocode");
			// byte[] barcode = intent.getByteArrayExtra("barcode");
			int barocodelen = intent.getIntExtra("length", 0);
			byte temp = intent.getByteExtra("barcodeType", (byte) 0);
			android.util.Log.i("debug", "----codetype--" + barocodelen + ""
					+ temp);
			barcodeStr = new String(barcode, 0, barocodelen);

			showScanResult.setText(barcodeStr);

			Intent intent2 = new Intent(BarCodeScannerActivity.this,
					ProductActivity.class);
			intent2.putExtra("BarCodeNum", barcodeStr);
			startActivity(intent2);

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_barcodescanner);
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		setupView();

	}

	private void initScan() {
		// TODO Auto-generated method stub
		mScanManager = new ScanManager();
		mScanManager.openScanner();

		mScanManager.switchOutputMode(0);
		soundpool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100); // MODE_RINGTONE
		soundid = soundpool.load("/etc/Scan_new.ogg", 1);
	}

	private void setupView() {
		// TODO Auto-generated method stub
		showScanResult = (EditText) findViewById(R.id.scan_result);
		btn = (Button) findViewById(R.id.manager);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mScanManager.getTriggerMode() != Triggering.CONTINUOUS)
					mScanManager.setTriggerMode(Triggering.CONTINUOUS);
			}
		});

		mScan = (Button) findViewById(R.id.scan);
		mScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// if(type == 3)
				mScanManager.stopDecode();
				isScaning = true;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mScanManager.startDecode();
			}
		});

		mClose = (Button) findViewById(R.id.close);
		mClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// if(isScaning) {
				// isScaning = false;
				mScanManager.stopDecode();
				// }
			}
		});

		// btn.setVisibility(View.GONE);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mScanManager != null) {
			mScanManager.stopDecode();
			isScaning = false;
		}
		unregisterReceiver(mScanReceiver);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initScan();
		showScanResult.setText("");
		IntentFilter filter = new IntentFilter();
		filter.addAction(SCAN_ACTION);
		registerReceiver(mScanReceiver, filter);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

}
