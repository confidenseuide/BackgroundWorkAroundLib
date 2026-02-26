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

	private void DestroyPanic() {
		Intent intent = new Intent(getPackageName() + ".START");
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
        }
	}

	private void forceBindAndStart() {
    Intent intent = new Intent(this, RiderService.class);
    bindService(intent, connection, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT);
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
