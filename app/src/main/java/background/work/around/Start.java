package background.work.around;

import android.content.*;

public class Start {

    public static void RunService(Context context) {
        Intent intent = new Intent("background.work.around" + ".START");
        intent.setPackage(context.getPackageName());            
        context.sendBroadcast(intent);
    }
}
