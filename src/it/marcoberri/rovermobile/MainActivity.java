package it.marcoberri.rovermobile;

import it.marcoberri.rovermobile.helper.SocketIOService;
import it.marcoberri.rovermobile.helper.SocketIOService.LocalBinder;
import it.marcoberri.rovermobile.helper.SocketIOServiceReceiver;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getName();
	private Intent socketIntenet;
	private SocketIOService socketIOService;
	private SocketIOServiceReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		IntentFilter filter = new IntentFilter(SocketIOService.PROCESS_RESPONSE);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new SocketIOServiceReceiver();
		registerReceiver(receiver, filter);

		socketIntenet = new Intent(this, SocketIOService.class);
		socketIntenet.setData(Uri.parse("http://172.18.205.88:3001"));
		Log.d(TAG, "Call start service");
		startService(socketIntenet);
		Log.d(TAG, "END Call start service");

		bindService(socketIntenet, serviceConnection, BIND_AUTO_CREATE);

	}

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d(TAG, "onServiceConnected " + service.toString());
			LocalBinder mLocalBinder = (LocalBinder) service;
			socketIOService = mLocalBinder.getServerInstance();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d(TAG, "onServiceDisconnected " + name);
			socketIOService = null;
		}
	};

	public void callStop(View v) {
		move("stop");
	}

	public void callLeft(View v) {
		move("left");
	}

	public void callRight(View v) {
		move("right");
	}

	public void callForward(View v) {
		move("forward");
	}

	public void callBack(View v) {
		move("back");
	}

	@Override
	public void onPause() {
		unregisterReceiver(receiver);
		super.onPause();
	}

	private void move(String move) {
		Log.d(TAG, "Call send " + move);
		if (socketIOService != null) {
			socketIOService.emit("/move/" + move);
		}
		Log.d(TAG, "end Call send " + move);

	}

	@Override
	protected void onResume() {
		IntentFilter filter = new IntentFilter(SocketIOService.PROCESS_RESPONSE);
		receiver = new SocketIOServiceReceiver();
		registerReceiver(receiver, filter);
		super.onResume();
	}
}
