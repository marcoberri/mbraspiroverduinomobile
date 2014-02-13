package it.marcoberri.rovermobile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.marcoberri.rovermobile.helper.SocketIOService;
import it.marcoberri.rovermobile.helper.SocketIOService.LocalBinder;
import it.marcoberri.rovermobile.helper.SocketIOServiceReceiver;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getName();
	private Intent socketIntenet;
	private SocketIOService socketIOService;
	private BroadcastReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		socketIntenet = new Intent(this, SocketIOService.class);
		socketIntenet.setData(Uri.parse("http://172.18.205.88:3001"));
		Log.d(TAG, "Call start service");
		startService(socketIntenet);
		Log.d(TAG, "END Call start service");

		bindService(socketIntenet, serviceConnection, BIND_AUTO_CREATE);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.d(TAG, "onNewIntent is called!");

		if (intent.getAction().equals(SocketIOServiceReceiver.class.getName())) {
			final String reponseString = intent.getStringExtra("return");
			final JsonParser parser = new JsonParser();
			final JsonObject o = (JsonObject) parser.parse(reponseString);
			Log.d(TAG, "response object:" + o);
			final TextView t = (TextView) findViewById(R.id.loggerView);
			t.setText(reponseString);
		}
		super.onNewIntent(intent);
	} // End of onNewIntent(Intent intent)

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

	public void camReset(View v) {
		cam("reset");
	}

	public void camLeft(View v) {
		cam("left");
	}

	public void camRight(View v) {
		cam("right");
	}

	public void camUp(View v) {
		cam("up");
	}

	public void camDown(View v) {
		cam("down");
	}

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
		// unregisterReceiver(receiver);
		super.onPause();
	}

	private void move(String move) {
		Log.d(TAG, "Call send " + move);
		if (socketIOService != null) {
			socketIOService.emit("/move/" + move);
		}
		Log.d(TAG, "end Call send " + move);
	}

	private void cam(String cam) {
		Log.d(TAG, "Call cam " + cam);
		if (socketIOService != null) {
			socketIOService.emit("/cam/" + cam);
		}
		Log.d(TAG, "end Call cam " + cam);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
