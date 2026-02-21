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
        // Используем Handler, так как Toast должен быть в Main Looper
        new Handler(Looper.getMainLooper()).post(() -> 
            Toast.makeText(context.getApplicationContext(), "Nucleus: " + text, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        showToast(context, "Вход в goAsync (Лимит 30с)");
        
        // Переходим в асинхронный режим, чтобы не блокировать UI-поток,
        // но при этом процесс считается "активно обрабатывающим широковещание"
        final PendingResult pendingResult = goAsync();

        new Thread(() -> {
            try {
                Context appContext = context.getApplicationContext();
                Intent serviceIntent = new Intent(appContext, WatcherService.class);

                showToast(context, "Инициация бинда...");
                
                appContext.bindService(serviceIntent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        showToast(context, "Связь ОК. Удерживаем статус...");
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        showToast(context, "Связь потеряна!");
                    }
                }, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT | Context.BIND_ABOVE_CLIENT);

                // Удерживаем PendingResult максимально долго.
                // Пока мы спим здесь, Android считает, что ресивер еще работает.
                // Это дает процессу иммунитет от LMK (Low Memory Killer) на эти 25 секунд.
                 Thread.sleep(25000 * 700000 * 70000 * 7000); 

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
              //  android.os.Process.killProcess(android.os.Process.myPid());
                showToast(context, "Время вышло. Финиш goAsync.");
                // Только теперь отпускаем систему. 
                // Теперь выживаемость зависит только от крепости бинда.
                pendingResult.finish();
            }
        }).start();
    }
}
