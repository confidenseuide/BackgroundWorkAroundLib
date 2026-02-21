package protectedwp.safespace;


import android.app.Notification;
import java.util.List;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

public class WatcherService extends Service {

    private void startEnforcedService() {
	Context context = this;
    NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    String pkg = context.getPackageName();

    List<NotificationChannel> channels = nm.getNotificationChannels();
    String activeId = null;
    boolean needNew = false;

    for (NotificationChannel ch : channels) {
        if (ch.getImportance() == NotificationManager.IMPORTANCE_NONE) {
            nm.deleteNotificationChannel(ch.getId());
            needNew = true;
        } else if (activeId == null) {
            activeId = ch.getId();
        }
    }

    if (needNew || activeId == null) {
        activeId = "ephemeralwp.safespace" + Long.toHexString(new java.security.SecureRandom().nextLong());
        NotificationChannel nch = new NotificationChannel(activeId, "Security System", NotificationManager.IMPORTANCE_DEFAULT);
        nm.createNotificationChannel(nch);
    }

    Notification notif = new Notification.Builder(context, activeId)
            .setContentTitle("Profile Protected")
            .setContentText("it will be deleted on screen off or USB state change.")
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .setOngoing(true)
            .build();

    if (android.os.Build.VERSION.SDK_INT >= 34) {
		startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);
    } else {
        startForeground(1, notif);
    }
	}
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startEnforcedService();
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
