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
    
    private final ServiceConnection connection = new ServiceConnection() {
        @Override public void onServiceConnected(ComponentName name, IBinder service) {}
        @Override
        public void onServiceDisconnected(ComponentName name) {
            bindToNeighbor();
        }
    };

    private void bindToNeighbor() {
        Intent intent = new Intent(this, WatcherService2.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

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
        return new Binder();
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.stop();
            player.release();
        }
        super.onDestroy();
    }
}
