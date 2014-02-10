package it.marcoberri.rovermobile.helper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SocketIOServiceReceiver extends BroadcastReceiver {

	
	private static final String TAG = SocketIOServiceReceiver.class.getName();

	@Override
        public void onReceive(Context context, Intent intent) {
            String reponseString= intent.getStringExtra(SocketIOService.RESPONSE_STRING);
            Log.d(TAG,"response string:" + reponseString);
            JsonParser parser = new JsonParser();
            JsonObject o = (JsonObject)parser.parse(reponseString);
            Log.d(TAG,"response object:" + o);
            Toast.makeText(context, reponseString, Toast.LENGTH_SHORT).show();
            
        }
    
}
