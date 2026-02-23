package background.work.around;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity extends Activity {

    @Override
    protected void onPause() {
    super.onPause();    
    // System may kills apps after user-forced clearing of the task stack. No task — nothing to kill.
    finishAndRemoveTask();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.BLACK);
        scroll.setFillViewport(true);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(64, 64, 64, 64);
        layout.setGravity(Gravity.CENTER);

        TextView tv = new TextView(this);
        tv.setTextSize(18);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.LEFT);

        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("ru")) {
            tv.setText("Это приложение это пример самого живучего foreground сервиса на Android без специальных прав. Для демонстрации работы вопроизводит звук и перезапускается при перезагрузке (если система не блокирует автозапуск boot ресиверов).");
        } else {
            tv.setText("This app is example of the most survival foreground service on Android without special rights. For demonstration of work plays sound and restarts at reboot (if system does not block autostart of boot receivers).");
        }

        Button runBtn = new Button(this);        
        if (lang.equals("ru")) {
        runBtn.setText("ЗАПУСТИТЬ ЗВУК");
        } else {
        runBtn.setText("START SOUND");
        }
        runBtn.setBackgroundColor(Color.WHITE);
        runBtn.setTextColor(Color.BLACK);
        
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 150);
        btnParams.setMargins(0, 50, 0, 0);
        runBtn.setLayoutParams(btnParams);

        runBtn.setOnClickListener(v -> {
            Intent intent = new Intent("background.work.around.START_NUCLEUS");
            intent.setPackage("background.work.around");            
            sendBroadcast(intent);
        });

        layout.addView(tv);
        layout.addView(runBtn);
        scroll.addView(layout);
        setContentView(scroll);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
