package it.marcoberri.rovermobile;

import it.marcoberri.rovermobile.helper.SocketIOService;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	
	private Intent socketIntenet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		

		
		
		
		
		
		/*
		
		socket = new SocketIOService();
		try {
			socket.connect("http://172.18.205.88:3001/");
			socket.run();
		} catch (MalformedURLException e) {
			Log.e("SOCKET",e.getMessage());
		} catch (Exception e) {
			Log.e("SOCKET",e.getMessage());
		} */
	}

	
	public void callStop(View v){
		
		socketIntenet = new Intent(this, SocketIOService.class); 
		 startService(socketIntenet);
/*
		Intent mServiceIntent = new Intent(this, SocketIOService.class);
		mServiceIntent.setData(Uri.parse("http://172.18.205.88:3001/"));
		startService(mServiceIntent);
*/
		Log.d("SOCKET", "START");
	}
	
}
