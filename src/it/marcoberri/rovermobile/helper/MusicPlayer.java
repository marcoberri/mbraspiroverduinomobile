package it.marcoberri.rovermobile.helper;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer {

	static MediaPlayer media;

	public static void start(Context context, int music) {
		if (media == null) {
			media = MediaPlayer.create(context, music);
			media.start();
			return;
		}
		if (media.isPlaying()) {
			return;
		}
		if (!media.isPlaying()) {
			media.start();
		}

	}

	/*
	 * playing = true pause/stop = false
	 */
	public static boolean toggle(Context context, int music) {

		if (media == null) {
			start(context, music);
			return true;
		}

		
		if (media.isPlaying()) {
			pause();
			return false;
		}

		if(!media.isPlaying()){
			start(context, music);
			return true;
		}
		
		return true;
	}

	public static boolean isPlay() {

		if (media == null || !media.isPlaying()) {
			return false;
		}
		
		return true;
	}

	
	public static void pause() {
		if (media == null) {
			return;
		}
		if (!media.isPlaying()) {
			return;
		}
		media.pause();
	}

}
