package it.marcoberri.rovermobile.helper;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;


public class SocketHelper {

	private  SocketIO socket;
	
	public void connect(String url) throws MalformedURLException{
		socket = new SocketIO(url);
		
	}
	public void run() throws Exception {
		
		socket.connect(new IOCallback() {
			@Override
			public void onMessage(JSONObject json, IOAcknowledge ack) {
				try {
					Log.d("SOCKET", "Server said:" + json.toString(2));
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
			}

			@Override
			public void onMessage(String data, IOAcknowledge ack) {
				Log.d("SOCKET", "Server said: " + data);
			}

			@Override
			public void onError(SocketIOException socketIOException) {
				Log.d("SOCKET", "an Error occured");
				socketIOException.printStackTrace();
			}

			@Override
			public void onDisconnect() {
				Log.d("SOCKET", "Connection terminated.");
			}

			@Override
			public void onConnect() {
				Log.d("SOCKET", "Connection established");				
			}

			@Override
			public void on(String event, IOAcknowledge ack, Object... args) {
				Log.d("SOCKET", "Server triggered event '" + event + "'");				
			}
		});
	}
	
	public void call(String calling){
		socket.send(calling);
		Log.d("SOCKET", calling);
		
	}
}
