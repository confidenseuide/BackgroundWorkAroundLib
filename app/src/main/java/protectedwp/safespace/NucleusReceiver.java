package protectedwp.safespace;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

public class NucleusReceiver extends BroadcastReceiver {

    private void showToast(Context context, final String text) {
        new Handler(Looper.getMainLooper()).post(() -> 
            Toast.makeText(context.getApplicationContext(), "NucleusReceiver: " + text, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        showToast(context, "Сигнал получен! goAsync...");
        final PendingResult pendingResult = goAsync();

        new Thread(() -> {
            try {
                Context appContext = context.getApplicationContext();
                Intent serviceIntent = new Intent(appContext, WatcherService.class);

                showToast(context, "Биндинг через App Context...");
                
                appContext.bindService(serviceIntent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        showToast(context, "УСПЕХ: Связь установлена!");
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        showToast(context, "ВНИМАНИЕ: Связь разорвана!");
                    }
                }, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT | Context.BIND_ABOVE_CLIENT);

            } finally {
                pendingResult.finish();
            }
        }).start();
    }
}
