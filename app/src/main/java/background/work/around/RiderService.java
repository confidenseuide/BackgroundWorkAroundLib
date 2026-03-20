/*
ATTENTION: THIS IS A DEMONSTRATIONAL PROJECT. IF YOU DO NOT OVERRIDE SOME METHODS, THE SERVICE MAY PLAY SOUND TO DEMONSTRATE ITS WORK.
*/

package background.work.around;

import java.util.*;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.media.*;
import android.os.*;
import android.provider.*;

public class RiderService extends Service {
    private MediaPlayer player;
    private boolean isRunning = false;

	protected void initLogicVoid() {
	}

	protected void serviceMainVoid() {
		if (player == null) {
            player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
            if (player != null) {
                player.setLooping(true);
                player.setVolume(1.0f, 1.0f);
                player.start();
            }
        }
	}

	protected void DestroyCleaner() {
		isRunning = false;
		if (player != null) {
            player.stop();
            player.release();
			player = null;
        }
	}

	protected String NotificationTitle() {
        return "Media";
    }

	protected String NotificationBody() {
        return "Play";
    }

	private void EndLessWL() {	
	new Thread(() -> {
	android.os.PowerManager pm = (android.os.PowerManager) getSystemService(android.content.Context.POWER_SERVICE);
	android.os.PowerManager.WakeLock[] wl = new android.os.PowerManager.WakeLock[10]; 
	int i = 0;
	while (true) {
	try {
	if (i<0) i=10;
	if (i<10) wl[i%10] = pm.newWakeLock(android.os.PowerManager.PARTIAL_WAKE_LOCK, "BackgroundWorkAround"+String.valueOf(i%10)+"::WakeLock"+String.valueOf(i%10));
	wl[i%10].acquire(3000); 
	i++;
	} catch (Throwable t) {}
	android.os.SystemClock.sleep(333); }
	}).start(); }
	
	private void startForegroundAlarm() {
    new Thread(() -> {
        Context ctx = getApplicationContext();

        try {
            AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            if (am == null) return;

			Intent serviceIntent = new Intent(getPackageName() + ".RIDER");
            serviceIntent.setPackage(getPackageName());
            PendingIntent operation = PendingIntent.getForegroundService(
                    ctx, 
                    333, 
                    serviceIntent, 
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            Intent showIntent = new Intent("background.work.around" + ".ACT");
            showIntent.setPackage(getPackageName());
            PendingIntent showAction = PendingIntent.getActivity(
                    ctx, 
                    0, 
                    showIntent, 
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            long triggerAt = System.currentTimeMillis() + 30000;

            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(
                    triggerAt, 
                    showAction 
			);
           
			am.setAlarmClock(alarmClockInfo, operation);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }).start(); 
	}


	private void startWatchdogThread() {
    new Thread(() -> {
        Context ctx = getApplicationContext();

        while (true) {
            try {
                AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
                
                Intent intent = new Intent("background.work.around" + ".START");
                intent.setPackage(ctx.getPackageName());

                PendingIntent pi = PendingIntent.getBroadcast(
                        ctx, 
                        777, 
                        intent, 
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );

                if (am != null) {
               am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000, pi);
                }
            } catch (Throwable t) {
              
            } 
            android.os.SystemClock.sleep(30000);
        }
    }).start();
}

	private void DestroyPanic() {
		Intent intent = new Intent("background.work.around" + ".START");
        intent.setPackage(getPackageName());            
        sendBroadcast(intent);
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
            .setContentTitle(NotificationTitle())
            .setContentText(NotificationBody())
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .setOngoing(true)
            .build();

    if (android.os.Build.VERSION.SDK_INT >= 34) {
        startForeground(1, notif, ServiceInfo.FOREGROUND_SERVICE_TYPE_SYSTEM_EXEMPTED);
    } else {
        startForeground(1, notif);
    }
	}

	private void TryStartEnforcedService() {
		try {startEnforcedService();} 
        catch (Throwable t) {}
	}

	
    private void initBindAndStart() {
	   if (!isRunning) {
        isRunning = true;
		forceBindAndStart();
		startForegroundAlarm();
		startWatchdogThread();
		initLogicVoid();
		TryStartEnforcedService();
		EndLessWL();
		serviceMainVoid();
        }
	}

	private void forceBindAndStart() {
    Intent intent = new Intent("background.work.around" + ".HELPER");
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
	TryStartEnforcedService();
    return START_STICKY;
    }

    @Override
    public void onDestroy() {
        DestroyPanic();
		DestroyCleaner();
        super.onDestroy();
    }
}
