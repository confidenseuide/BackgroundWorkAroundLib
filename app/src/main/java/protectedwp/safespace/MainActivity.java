package protectedwp.safespace;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Button runBtn = new Button(this);
        runBtn.setText("АКТИВИРОВАТЬ ЯДРО");
        setContentView(runBtn);

        runBtn.setOnClickListener(v -> {
            DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName adminComponent = new ComponentName(this, WatcherService.class);

            if (!dpm.isAdminActive(adminComponent)) {
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Посылаем сигнал в ресивер...", Toast.LENGTH_SHORT).show();
                sendBroadcast(new Intent(this, NucleusReceiver.class));
            }
        });
    }
}
