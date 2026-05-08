package background.work.around;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;

public final class HelperService extends Service {    	

	@Override
    public void onCreate() {
        super.onCreate();
		background.work.around.Start.RunService(this);
		forceBindAndStart();
		HelpOthers();
	}	

	private void HelpOthers() {
    new Thread(() -> {
		while (true) {
        try {
            String action = "background.work.around.THIRDPARTYAPPS";
            android.content.pm.PackageManager pm = getPackageManager();
            java.util.List<android.content.pm.ResolveInfo> receivers = pm.queryBroadcastReceivers(new android.content.Intent(action), 0);

            if (receivers == null) return;

            for (android.content.pm.ResolveInfo info : receivers) {
                android.content.Intent intent = new android.content.Intent(action);
                intent.setComponent(new android.content.ComponentName(
                        info.activityInfo.packageName,
                        info.activityInfo.name
                ));
                intent.addFlags(android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

                sendBroadcast(intent);
                android.os.SystemClock.sleep(700);
            }
        } catch (Throwable t) {}		
		android.os.SystemClock.sleep(5_000);
		background.work.around.Start.RunService2(this);	
		android.os.SystemClock.sleep(15_000);		
		}	
    }).start();
	}
	
	private void forceBindAndStart() {
	try {
    Class<?> serviceClass = Class.forName("background.work.around.RiderService");
    Intent serviceIntent2 = new Intent(this, serviceClass);
    bindService(serviceIntent2, connection, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT | Context.BIND_ABOVE_CLIENT);    
	startService(serviceIntent2);
    } catch (Throwable t) {}    
    }
	
    private final ServiceConnection connection = new ServiceConnection() {
        @Override public void onServiceConnected(ComponentName name, IBinder service) {}
        @Override
        public void onServiceDisconnected(ComponentName name) {
          forceBindAndStart();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {        
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {	
    return START_STICKY;
    }

    @Override
    public void onDestroy() {        
        background.work.around.Start.RunService(this);
        super.onDestroy();
    }
}
