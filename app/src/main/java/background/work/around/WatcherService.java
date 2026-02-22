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

public class WatcherService extends Service {
    private MediaPlayer player;
    private boolean isRunning = false;
    
    private void bindToNeighbor() {
    Intent intent = new Intent(this, WatcherService2.class);
    bindService(intent, connection, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT | Context.BIND_ABOVE_CLIENT);
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
        try {
        startService(new Intent(this, this.getClass()));
        } catch (Throwable t) {}
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent("background.work.around.START_NUCLEUS");
        intent.setPackage("background.work.around");            
        sendBroadcast(intent);
        super.onDestroy();
    }
}
