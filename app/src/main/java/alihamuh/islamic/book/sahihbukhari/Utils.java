package alihamuh.islamic.book.sahihbukhari;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;

import static alihamuh.islamic.book.sahihbukhari.MainHadithContentpage.appClass;

public class Utils {
	//private static int sTheme;




	public static void changeToTheme(Activity activity, Boolean mode) {
		appClass.setNightmode(mode);
		activity.finish();
		activity.startActivity(new Intent(activity, activity.getClass()));
		/*TaskStackBuilder.create(activity)
				.addNextIntent(new Intent(activity, activity.getClass()))
				.addNextIntent(activity.getIntent())
				.startActivities();*/
		activity.overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);


	}

	public static void onActivityCreateSetTheme(Activity activity) {
		if(appClass.getNightmode()){
			activity.setTheme(R.style.NightAppThem_NoActionBar);
		}else{
			activity.setTheme(R.style.AppTheme_NoActionBar);

		}
	}
}