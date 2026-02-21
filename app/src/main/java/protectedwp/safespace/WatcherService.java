package protectedwp.safespace;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class WatcherService extends Service {
    private MediaPlayer player;
    
    private final ServiceConnection connection = new ServiceConnection() {
        @Override public void onServiceConnected(ComponentName name, IBinder service) {}
        @Override
        public void onServiceDisconnected(ComponentName name) {
            bindToNeighbor(); // Воскрешаем соседа
        }
    };

    private void bindToNeighbor() {
        Intent intent = new Intent(this, WatcherService2.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Запуск непрерывной музыки
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.alarm_sound);
            player.setLooping(true); // Бесконечный повтор
            player.setVolume(1.0f, 1.0f); // Максимальная громкость
            player.start();
        }

        bindToNeighbor();
        return new Binder();
    }

    @Override
    public void onDestroy() {
        if (player != null) player.release();
        super.onDestroy();
    }
}
