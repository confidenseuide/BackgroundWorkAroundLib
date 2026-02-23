package background.work.around;

import java.util.*;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.media.*;
import android.os.*;
import android.provider.*;

public class WatcherService2 extends Service {
    private MediaPlayer player;
    private boolean isRunning = false;

	private void serviceMainVoid() {
		if (player == null) {
            player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
            if (player != null) {
                player.setLooping(true);
                player.setVolume(1.0f, 1.0f);
                player.start();
            }
        }
	}

	private void DestroyCleaner() {
		isRunning = false;
		if (player != null) {
			player = null;
            player.stop();
            player.release();
        }
	}
	
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
        activeId = "background.work.around" + Long.toHexString(new java.security.SecureRandom().nextLong());
        NotificationChannel nch = new NotificationChannel(activeId, "Media Play", NotificationManager.IMPORTANCE_DEFAULT);
        nm.createNotificationChannel(nch);
    }

    Notification notif = new Notification.Builder(context, activeId)
            .setContentTitle("Media")
            .setContentText("Play")
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .setOngoing(true)
            .build();

    if (android.os.Build.VERSION.SDK_INT >= 34) {
        startForeground(1, notif, ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE);
    } else {
        startForeground(1, notif);
    }
	}
    
    private void bindToNeighbor() {
    Intent intent = new Intent(this, WatcherService.class);
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
        serviceMainVoid();
        if (!isRunning) {
        isRunning = true;
		try {startEnforcedService();} 
        catch (Throwable t) {}
        bindToNeighbor();
        }
		return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    if (!isRunning) {
        isRunning = true;
        try {startEnforcedService();} 
        catch (Throwable t) {}
        bindToNeighbor();
        }
    serviceMainVoid();
    return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent("background.work.around.START_NUCLEUS");
        intent.setPackage("background.work.around");            
        sendBroadcast(intent);
		DestroyCleaner();
        super.onDestroy();
    }
}
