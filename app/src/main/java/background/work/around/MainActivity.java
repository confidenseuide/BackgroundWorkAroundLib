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
        runBtn.setText("ПУСК");
        setContentView(runBtn);

        runBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Удар по ресиверу!", Toast.LENGTH_SHORT).show();
            
            // Создаем интент с ТВОИМ акшеном из манифеста
            Intent intent = new Intent("background.work.around.START_NUCLEUS");
            
            // Явно указываем пакет нашего приложения. 
            // Без этого на Android 8.0+ бродкаст с кастомным Action часто игнорится.
            intent.setPackage("background.work.around");
            
            sendBroadcast(intent);
        });
    }
}
