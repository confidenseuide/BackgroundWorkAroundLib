//THIS FILE NEED BE HERE: app/src/main/java/background/work/around/RiderService.java

//This service live in android:process=":rider1337backgroundworkaround"
//use same process if you wanna contact it, for example change shared prefs.

/*
To start use:
background.work.around.Start.RunService(this);
or your context instead of "this".
Please start on any activity launch.
*/

package background.work.around;

public class RiderService extends RiderService1 {														

	@Override
	protected Class<?> NotificationPendingIntentClass() {
    //return null; //if wanna no activity
	return background.work.around.MainActivity.class; //or your
	}
	
	@Override
	protected boolean use_adaptive_WakeLock_on_agressive_OEMs() {
	return true;	
	}

	@Override
	protected boolean use_aressive_WakeLock_on_all_OEMs() {
	return false;
	}

	@Override
	protected boolean isAgressive_OEM() {
    String m = android.os.Build.MANUFACTURER.toLowerCase();
    return m.contains("xiaomi") || m.contains("redmi") || m.contains("poco") || m.contains("huawei") || m.contains("realme") || m.contains("oppo") || m.contains("vivo");
	}
	
	@Override
    protected String NotificationTitle() { return "YourTitleName"; }

    @Override
    protected String NotificationBody() { return "YourBodyName"; }

	@Override
	protected Boolean isHideNotificationOnTheLockScreen() {
    return false;
	}

	@Override
    protected Boolean disableNotificationSound() {
    return true;
	}

    @Override
    protected void initLogicVoid() {
		/*
		This void runs right before the void responsible for startForeground. It's be useful if you need to do something before that. For example, requesting notification permissions (not required). in other cases don't use it. And even if you do, don't put complicated logic in it.
		*/
	}

    @Override
    protected void serviceMainVoid() {
        //YOUR CORE LOGIC
        //This method is called when the service is first fully started (like OnCreate).
    }

    @Override
    protected void DestroyCleaner() {		
        //cleaning resources when finished.
        //Don't call super.ondestroy here.
    }	

}
