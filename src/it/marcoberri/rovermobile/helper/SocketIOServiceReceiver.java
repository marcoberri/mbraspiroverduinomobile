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
	 private static final int NOTIFY_ME_ID=1337;
	 
	@Override
        public void onReceive(Context context, Intent intent) {
            String reponseString= intent.getStringExtra(SocketIOService.RESPONSE_STRING);
            Log.d(TAG,"response string:" + reponseString);
            JsonParser parser = new JsonParser();
            JsonObject o = (JsonObject)parser.parse(reponseString);
            Log.d(TAG,"response object:" + o);
            Toast.makeText(context, reponseString, Toast.LENGTH_SHORT).show();
            
          
            NotificationManager mgr=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
            Notification n  = new Notification.Builder(context).setContentText(o.toString()) .setContentIntent(pIntent).build();
            mgr.notify(123, n);
            
          /*  
            Notification note = new Notification("NODE_ID", o,System.currentTimeMillis());
            
           PendingIntent i=PendingIntent.getActivity(context, 0,new Intent(context, MainActivity.class),0);

note.setLatestEventInfo(context, "You Care About This!",
        "...but not enough to keep the activity running",
        i);

mgr.notify(NOTIFY_ME_ID, note);*/
            
        }
    
}
