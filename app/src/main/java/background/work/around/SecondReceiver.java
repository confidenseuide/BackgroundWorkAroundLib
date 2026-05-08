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

public final class SecondReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        intent = null;
        Context appContext = context.getApplicationContext();
        context = null;
        
        final PendingResult pendingResult = goAsync();

        new Thread(() -> {
            try {
                Intent serviceIntent = new Intent(appContext, HelperService.class);
                
                appContext.bindService(serviceIntent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {

                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        
                    }
                }, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT | Context.BIND_ABOVE_CLIENT);

                Thread.sleep(5_000);
                background.work.around.Start.RunService2(appContext);
            } catch (Throwable t) {
               
            } finally {
                pendingResult.finish();
            }
        }).start();
    }
}
