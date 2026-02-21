package background.work.around;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Button runBtn = new Button(this);
        runBtn.setText("START");
        setContentView(runBtn);

        runBtn.setOnClickListener(v -> {
            
            Intent intent = new Intent("background.work.around.START_NUCLEUS");
            
            intent.setPackage("background.work.around");
            
            sendBroadcast(intent);
        });
    }
}
