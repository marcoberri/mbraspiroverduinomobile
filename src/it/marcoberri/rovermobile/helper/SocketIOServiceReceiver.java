package it.marcoberri.rovermobile.helper;

import it.marcoberri.rovermobile.MainActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SocketIOServiceReceiver extends BroadcastReceiver {

	private static final String TAG = SocketIOServiceReceiver.class.getName();
	public static final int NOTIFY_ME_ID = 1337;

	@Override
	public void onReceive(Context context, Intent intent) {
		String reponseString = intent
				.getStringExtra(SocketIOService.RESPONSE_STRING);
		Log.d(TAG, "response string:" + reponseString);
		JsonParser parser = new JsonParser();

		final Intent intent2open = new Intent(context, MainActivity.class);
		intent2open.setAction(SocketIOServiceReceiver.class.getName());
		intent2open.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent2open.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent2open.putExtra("return", reponseString);
		context.startActivity(intent2open);
	}

}
