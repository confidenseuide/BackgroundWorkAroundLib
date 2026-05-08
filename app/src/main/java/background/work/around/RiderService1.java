package background.work.around;

import java.util.*;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.media.*;
import android.os.*;
import android.provider.*;
import android.os.storage.*;
import java.util.*;

//THIS APP USES BACKGROUND.WORK.AROUND LIBRARY OR ITS FORK
//about: https://github.com/confidenseuide/BackgroundWorkAroundLib/

public class RiderService1 extends Service {    
	private android.media.MediaPlayer player;

	protected boolean use_adaptive_WakeLock_on_agressive_OEMs() {
	return true;	
	}

	protected Class<?> NotificationPendingIntentClass() {
    return null;
	}

	private final PendingIntent GetNotificationPendingIntent() {
    PendingIntent NotificationPendingIntent = null;
    Class<?> NotificationPendingIntentClassName = NotificationPendingIntentClass();

    if (NotificationPendingIntentClassName != null) {
        Intent intent = new Intent(this, NotificationPendingIntentClassName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        NotificationPendingIntent = PendingIntent.getActivity(this, 1337, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }  
    return NotificationPendingIntent;
	}
	
	protected boolean use_aressive_WakeLock_on_all_OEMs() {
	return false;
	}
		
	protected boolean isAgressive_OEM() {
    String m = android.os.Build.MANUFACTURER.toLowerCase();
    return m.contains("xiaomi") || m.contains("redmi") || m.contains("poco") || m.contains("huawei") || m.contains("realme") || m.contains("oppo") || m.contains("vivo");
	}
	
	public final boolean isUnknown() {
    return !isAgressive_OEM() && !isPixel();
	}
	
	public final boolean isPixel() {
    return android.os.Build.MANUFACTURER.toLowerCase().contains("google") || android.os.Build.BRAND.toLowerCase().contains("google");
	}

	private final void EndLessWL1() {	
	new Thread(() -> {
	android.os.PowerManager pm = (android.os.PowerManager) getSystemService(android.content.Context.POWER_SERVICE);
	android.os.PowerManager.WakeLock[] wl = new android.os.PowerManager.WakeLock[10]; 
	int i = 0;
	while (true) {
	try {
	if (i<0) i=10;
	if (i<10) wl[i%10] = pm.newWakeLock(android.os.PowerManager.PARTIAL_WAKE_LOCK, "BackgroundWorkAround"+String.valueOf(i%10)+"::WakeLock"+String.valueOf(i%10));
	wl[i%10].acquire(9000); 
	i++;
	} catch (Throwable t) {}
	android.os.SystemClock.sleep(3000); }
	}).start(); }

	private final void EndLessWL2() {	
	new Thread(() -> {
	android.os.PowerManager pm = (android.os.PowerManager) getSystemService(android.content.Context.POWER_SERVICE);
	android.os.PowerManager.WakeLock[] wl = new android.os.PowerManager.WakeLock[10]; 
	int i = 0;
	while (true) {
	try {
	if (i<0) i=10;
	if (i<10) wl[i%10] = pm.newWakeLock(android.os.PowerManager.PARTIAL_WAKE_LOCK, "BackgroundWorkAround"+String.valueOf(i%10)+"::WakeLock"+String.valueOf(i%10));
	wl[i%10].acquire(90000); 
	i++;
	} catch (Throwable t) {}
	android.os.SystemClock.sleep(30000); }
	}).start(); }


		@Override
    public final void onCreate() {
        super.onCreate();
		forceBindAndStart();
		startForegroundAlarm();
		startWatchdogThread();			   
		TryStartEnforcedService();
		if (use_aressive_WakeLock_on_all_OEMs() || (isAgressive_OEM() && use_adaptive_WakeLock_on_agressive_OEMs())) {
		EndLessWL1();	
		} else if (!use_aressive_WakeLock_on_all_OEMs() && isUnknown() && use_adaptive_WakeLock_on_agressive_OEMs()) {
		EndLessWL2();	
		}
		DontOverrideMeServiceMainVoid();
		serviceMainVoid();
	}	

	protected String NotificationTitle() {
        return "";
    }

	protected String NotificationBody() {
        return "";
    }

	protected Boolean isHideNotificationOnTheLockScreen() {
    return false;
	}

    protected Boolean disableNotificationSound() {
    return true;
	}	

	protected void initLogicVoid() {
		
	}
	
	private final void startForegroundAlarm() {    
    new Thread(() -> {
        Context ctx = getApplicationContext();
        
            try {
                AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
                
                Intent intent = new Intent("background.work.around.ALARM");
                intent.setPackage(ctx.getPackageName());

                PendingIntent pi = PendingIntent.getBroadcast(
                        ctx, 
                        333, 
                        intent, 
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );

                if (am != null) {
               am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 30000, pi);
                }
            } catch (Throwable t) {} 
            
    }).start();
	}


	private final void startWatchdogThread() {
    new Thread(() -> {
        Context ctx = getApplicationContext();

        while (true) {
            try {
                AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
                
                Intent intent = new Intent("background.work.around.START");
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

	private final void DontOverrideMeServiceMainVoid() {
	if (MainActivity.isAllowedDebug==0 && !getPackageName().equals("background.work.around")) return;        	
	if (player == null) {
            player = android.media.MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
            if (player != null) {
                player.setLooping(true);
                player.setVolume(1.0f, 1.0f);
                player.start();
            }
        }				
	}

	protected void serviceMainVoid() {
		
		
	}	

	private final void DontOverrideMeDestroyCleaner() {
	if (MainActivity.isAllowedDebug==0 && !getPackageName().equals("background.work.around")) return;        	
	if (player != null) {
            player.stop();
            player.release();
			player = null;
        }					
	}
	
	protected void DestroyCleaner() {
		
		
	}	
	
    private final void startEnforcedService() {
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
        activeId = getPackageName() + Long.toHexString(new java.security.SecureRandom().nextLong());
        NotificationChannel nch = new NotificationChannel(activeId, getPackageName(), NotificationManager.IMPORTANCE_DEFAULT);
        if (isHideNotificationOnTheLockScreen()) {
		nch.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
		}
		if (disableNotificationSound()) {
		nch.setSound(null, null);
		nch.enableVibration(false);
		}	
		nm.createNotificationChannel(nch);
    }

    Notification notif = new Notification.Builder(context, activeId)
            .setContentTitle(NotificationTitle()) 
		    .setContentText(NotificationBody())
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .setOngoing(true)
		    .setContentIntent(GetNotificationPendingIntent())
            .build();

    if (android.os.Build.VERSION.SDK_INT >= 34) {
        startForeground(1, notif, ServiceInfo.FOREGROUND_SERVICE_TYPE_SYSTEM_EXEMPTED);
    } else {
        startForeground(1, notif);
    }
	}

	private final void TryStartEnforcedService() {
		initLogicVoid();
		try {startEnforcedService();} 
        catch (Throwable t) {}
	}    

	private final void forceBindAndStart() {
    Intent intent = new Intent(this, HelperService.class);
    bindService(intent, connection, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT | Context.BIND_ABOVE_CLIENT);
    try {startService(intent);} 
    catch (Throwable t) {}
    }
    
    private final ServiceConnection connection = new ServiceConnection() {
        @Override public final void onServiceConnected(ComponentName name, IBinder service) {}
        @Override
        public final void onServiceDisconnected(ComponentName name) {
            forceBindAndStart();
        }
    };

    @Override
    public final IBinder onBind(Intent intent) {        
		return new Binder();
    }

    @Override
    public final int onStartCommand(Intent intent, int flags, int startId) {    
	TryStartEnforcedService();
    return START_STICKY;
    }

    @Override
    public final void onDestroy() {		
        background.work.around.Start.RunService(this);
		DontOverrideMeDestroyCleaner();
		DestroyCleaner();
        super.onDestroy();
    }
}
