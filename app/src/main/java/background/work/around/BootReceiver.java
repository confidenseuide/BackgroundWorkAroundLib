package background.work.around;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        
        if (intent == null) return;
        String action = intent.getAction();
        if (action == null) return;
        if (!action.equals("android.intent.action.BOOT_COMPLETED") && !action.equals("android.intent.action.LOCKED_BOOT_COMPLETED") && !action.equals("android.intent.action.MY_PACKAGE_REPLACED") && !action.equals("android.intent.action.TIME_SET") && !action.equals("android.intent.action.TIMEZONE_CHANGED") && !action.equals("android.intent.action.LOCALE_CHANGED")) return;

        if (action.equals("android.intent.action.TIME_SET") || action.equals("android.intent.action.TIMEZONE_CHANGED") || action.equals("android.intent.action.LOCALE_CHANGED")) {
            try {

                Context appCtx = context.getApplicationContext();
              
                AlarmManager am = (AlarmManager) appCtx.getSystemService(Context.ALARM_SERVICE);
                
                Intent intentAlarm = new Intent(appCtx.getPackageName() + ".START");
                intent.setPackage(appCtx.getPackageName());

                PendingIntent pi = PendingIntent.getBroadcast(
                        appCtx, 
                        777, 
                        intentAlarm, 
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );

                if (am != null) {
               am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000, pi);
                }
            } catch (Throwable t) {
              
            } 
        }
        
        final PendingResult pendingResult = goAsync();

        new Thread(() -> {
            try {
                Context appContext = context.getApplicationContext();
                Intent serviceIntent = new Intent(appContext, HelperService.class);

        
                appContext.bindService(serviceIntent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {

                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        
                    }
                }, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT | Context.BIND_ABOVE_CLIENT);

                Thread.sleep(Long.MAX_VALUE);
            } catch (Exception e) {
               
            } finally {
                pendingResult.finish();
            }
        }).start();
    }
}
