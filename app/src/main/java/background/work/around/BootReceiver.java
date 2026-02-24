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

    /*
    private void showToast(Context context, final String text) {
    new Handler(Looper.getMainLooper()).post(() -> {
        try {
            Toast.makeText(context.getApplicationContext(), "Receiver: " + text, Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {}
    });
    }
    */


    @Override
    public void onReceive(Context context, Intent intent) {
        
        if (intent == null) return;
        if (intent.getAction() == null) return;
        String action = intent.getAction();
        if (!action.equals("android.intent.action.BOOT_COMPLETED") && !action.equals("android.intent.action.LOCKED_BOOT_COMPLETED") && !action.equals(context.getPackageName() + ".START")) return;

        //showToast(context, "Receiver goAsync");
        
       final PendingResult pendingResult = goAsync();

        new Thread(() -> {
            try {
                Context appContext = context.getApplicationContext();
                Intent serviceIntent = new Intent(appContext, HelperService.class);

               // showToast(context, "Starting bind...");
                
                appContext.bindService(serviceIntent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                       // showToast(context, "Binded...");
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        // showToast(context, "Bind lost!");
                    }
                }, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT | Context.BIND_ABOVE_CLIENT);

                Thread.sleep(Long.MAX_VALUE);
            } catch (Exception e) {
               // e.printStackTrace();
            } finally {
               // showToast(context, "Finish goAsync.");
                pendingResult.finish();
            }
        }).start();
    }
}
