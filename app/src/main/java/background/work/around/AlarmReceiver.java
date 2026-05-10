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

public final class AlarmReceiver extends BroadcastReceiver {

    private static final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {

        intent=null;
        Context appContext = context.getApplicationContext();
        context=null;
     
        final PendingResult pendingResult = goAsync();

        new Thread(() -> {
            try {                
                Intent serviceIntent = new Intent(appContext, HelperService.class);                
                
                appContext.bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT | Context.BIND_ABOVE_CLIENT);
                try {
                Class<?> serviceClass = Class.forName("background.work.around.RiderService");
                Intent serviceIntent2 = new Intent(appContext, serviceClass);
                appContext.startForegroundService(serviceIntent2);
                } catch (Throwable t) {}
                Thread.sleep(15_000);                
            } catch (Throwable t) {
               
            } finally {
                pendingResult.finish();
            }
        }).start();
    }
}
