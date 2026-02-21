package protectedwp.safespace;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Binder;
import android.os.IBinder;

public class WatcherService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(() -> {
            try {
                ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                while (true) {
                    tg.startTone(ToneGenerator.TONE_PROP_BEEP, 150);
                    try { 
                        Thread.sleep(2000); 
                    } catch (Throwable t) {
                        // Даже если прервали сон — продолжаем цикл
                    }
                }
            } catch (Throwable t) {
                // Если сдох сам ToneGenerator или еще что — тут уже всё
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Прямой вызов логики
        onStartCommand(intent, 0, -1);
        return new Binder();
    }
}
