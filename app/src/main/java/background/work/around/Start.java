package background.work.around;

import android.content.*;

public final class Start {

    public static void RunService(Context context) {
        try{
          
        Intent intent = new Intent("background.work.around.START");
        intent.setPackage(context.getPackageName());            
        context.sendBroadcast(intent);        
        context.sendOrderedBroadcast(intent, null);   
          
        } catch(Throwable t) {}
    }

    public static void RunService2(Context context) {
        try{
          
        Intent intent = new Intent("background.work.around.SECOND");
        intent.setPackage(context.getPackageName());            
        context.sendBroadcast(intent);        
        context.sendOrderedBroadcast(intent, null);   
          
        } catch(Throwable t) {}
    }    
    
}
