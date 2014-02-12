package it.marcoberri.rovermobile.helper;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

public class SocketIOService extends IntentService {

	public static final String PROCESS_RESPONSE = "SocketIOService";
	public static final String REQUEST_STRING = "myRequest";
	public static final String RESPONSE_STRING = "response";

	@Override
	public boolean bindService(Intent service, ServiceConnection conn, int flags) {
		Log.d(TAG, "bindService: " + service + " ServiceConnection:" + conn
				+ " flags:" + flags);
		return super.bindService(service, conn, flags);
	}

	public SocketIOService() {
		super("SocketIOService");
	}

	private static final String TAG = SocketIOService.class.getName();

	private SocketIO socket;

	public void run() throws Exception {

		socket.connect(new IOCallback() {

			@Override
			public void onMessage(JSONObject json, IOAcknowledge ack) {
				try {
					Log.d(TAG, "Server said:" + json.toString(2));
					// sendBroadcast(json.toString(2));
				} catch (JSONException e) {
					Log.e(TAG, e.getMessage());

				}
			}

			@Override
			public void onMessage(String data, IOAcknowledge ack) {
				Log.d(TAG, "Server said: " + data);
				// sendBroadcast("Server said: " + data);
			}

			@Override
			public void onError(SocketIOException socketIOException) {
				Log.d(TAG, "an Error occured");
				sendBroadcast("Server onError: " + socketIOException.toString());
				socketIOException.printStackTrace();
			}

			@Override
			public void onDisconnect() {
				Log.d(TAG, "Connection terminated.");
				// sendBroadcast("Connection terminated.");
			}

			@Override
			public void onConnect() {
				Log.d(TAG, "Connection established");
				// sendBroadcast("Connection established.");

			}

			@Override
			public void on(String event, IOAcknowledge ack, Object... args) {
				Log.d(TAG, "Server triggered event '" + event + "'" + args);
				sendBroadcast(args[0].toString());
			}
		});
	}

	public void emit(String calling) {
		socket.emit(calling);
		Log.d(TAG, calling);

	}

	@Override
	protected void onHandleIntent(Intent intent) {

		if (socket == null) {
			Log.d(TAG, "onHandleIntent: " + intent.getDataString());
			try {
				socket = new SocketIO(intent.getDataString());
				run();
			} catch (MalformedURLException e) {
				Log.e(TAG, e.getMessage());
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
		}

	}

	protected void sendBroadcast(String msg) {
		final Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(TAG);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		broadcastIntent.putExtra(RESPONSE_STRING, msg);
		sendBroadcast(broadcastIntent);

	}

	IBinder mBinder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public class LocalBinder extends Binder {
		public SocketIOService getServerInstance() {
			return SocketIOService.this;
		}
	}
}
