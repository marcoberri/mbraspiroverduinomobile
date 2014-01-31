package it.marcoberri.rovermobile.helper;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;


public class Utils {

	public static void getToast(Activity act, String message, int duration){
		CharSequence text = message;
    	Toast toast = Toast.makeText(act, text, duration);
    	toast.show();
	}

	
	
	public static void launchFacebookPage(String page_id, PackageManager packageManager, Activity activity) {
        final String urlFb = "fb://page/"+page_id;
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        final List<ResolveInfo> list =  packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() == 0) {
            final String urlBrowser = "https://www.facebook.com/pages/"+page_id;
            intent.setData(Uri.parse(urlBrowser));
        }

        activity.startActivity(intent);
    }
	
	
	public static void launchWebBrowser(String url, Activity activity){
    	final Intent i = new Intent(Intent.ACTION_VIEW);
    	i.setData(Uri.parse(url));
    	activity.startActivity(i);
	}
}
