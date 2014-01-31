package it.marcoberri.rovermobile;

import java.net.MalformedURLException;

import io.socket.SocketIO;
import it.marcoberri.rovermobile.helper.SocketHelper;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	
	SocketHelper socket;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		socket = new SocketHelper();
		try {
			socket.connect("http://172.18.205.88:3001/");
		} catch (MalformedURLException e) {
			Log.e("SOCKET",e.getMessage());
		}
	}

	
	public void callStop(View v){
		try {
			socket.call("/move/left");
		} catch (Exception e) {
			Log.e("SOCKET",e.getMessage());
		}
	        
	}
	
}
