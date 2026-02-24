package background.work.around;

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
        if (!action.equals("android.intent.action.BOOT_COMPLETED") && !action.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) return;

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
