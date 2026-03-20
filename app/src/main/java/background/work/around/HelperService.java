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

public class HelperService extends Service {
    private boolean isRunning = false;

	private void startWatchdogThread() {
        new Thread(() -> {
            while (true) {
                try {
					/* 
					Extremely important: a call of a static receiver increases the app priority, and a clogged queue is actually good, because as soon as all processes die, the system can revisit the broadcast queue and restart the application. The receiver is located in a separate process, so there is no need to worry that this will lead to failures in the service.
					*/
                    DestroyPanic();
                } catch (Throwable t) {}
                android.os.SystemClock.sleep(15000);
            }
        }).start();
    }
	
	private void DestroyPanic() {
		Intent intent = new Intent("background.work.around" + ".START");
        intent.setPackage(getPackageName());            
        sendBroadcast(intent);
	}
	
    private void DestroyCleaner() {
		isRunning = false;
	}
	

	private void initBindAndStart() {
	   if (!isRunning) {
        isRunning = true;
        forceBindAndStart();
		startWatchdogThread();
        }
	}

	private void forceBindAndStart() {
    Intent intent = new Intent(getPackageName() + ".RIDER");
    intent.setPackage(getPackageName());
    bindService(intent, connection, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT | Context.BIND_ABOVE_CLIENT);
    try {startService(intent);} 
    catch (Throwable t) {}
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
        initBindAndStart();
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	initBindAndStart();
    return START_STICKY;
    }

    @Override
    public void onDestroy() {
        DestroyPanic();
        DestroyCleaner();
        super.onDestroy();
    }
}
