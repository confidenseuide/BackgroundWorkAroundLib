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
		Intent intent = new Intent("background.work.around.START");
        intent.setPackage("background.work.around");            
        sendBroadcast(intent);
	}
	
    private void DestroyCleaner() {
		isRunning = false;
	}
    
    private void bindToNeighbor() {
    Intent intent = new Intent(this, RiderService.class);
    bindService(intent, connection, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT | Context.BIND_ABOVE_CLIENT);
    try {startService(intent);} 
    catch (Throwable t) {}
    }
    
    private final ServiceConnection connection = new ServiceConnection() {
        @Override public void onServiceConnected(ComponentName name, IBinder service) {}
        @Override
        public void onServiceDisconnected(ComponentName name) {
          bindToNeighbor();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        if (!isRunning) {
        isRunning = true;
        bindToNeighbor();
        }
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    if (!isRunning) {
        isRunning = true;
        bindToNeighbor();
        }
    return START_STICKY;
    }

    @Override
    public void onDestroy() {
        DestroyPanic();
        DestroyCleaner();
        super.onDestroy();
    }
}
