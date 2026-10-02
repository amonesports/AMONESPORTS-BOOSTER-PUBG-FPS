package me.binhmod.fps;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_OVERLAY = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Cream un meniu simplu direct din cod
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setGravity(android.view.Gravity.CENTER);
        layout.setPadding(50, 50, 50, 50);
        layout.setBackgroundColor(android.graphics.Color.parseColor("#121212")); // Temă întunecată (Dark Theme)

        TextView title = new TextView(this);
        title.setText("AMONESPORTS FPS Meter");
        title.setTextColor(android.graphics.Color.WHITE);
        title.setTextSize(22);
        title.setGravity(android.view.Gravity.CENTER);
        title.setPadding(0, 0, 0, 50);
        layout.addView(title);

        Button btnStart = new Button(this);
        btnStart.setText("PORNEȘTE FPS (ON)");
        btnStart.setBackgroundColor(android.graphics.Color.parseColor("#4CAF50"));
        btnStart.setTextColor(android.graphics.Color.WHITE);
        btnStart.setOnClickListener(v -> checkOverlayAndStart());
        layout.addView(btnStart);

        Button btnStop = new Button(this);
        btnStop.setText("Oprește FPS (OFF)");
        btnStop.setBackgroundColor(android.graphics.Color.parseColor("#F44336"));
        btnStop.setTextColor(android.graphics.Color.WHITE);
        btnStop.setPadding(0, 30, 0, 0);
        btnStop.setOnClickListener(v -> {
            stopService(new Intent(this, FPSService.class));
            Toast.makeText(this, "Serviciul a fost oprit", Toast.LENGTH_SHORT).show();
        });
        // adăugăm spațiu între butoane
        android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
            android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
            android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 30, 0, 0);
        btnStop.setLayoutParams(params);
        layout.addView(btnStop);

        setContentView(layout);
    }

    private void checkOverlayAndStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQ_OVERLAY);
        } else {
            startFPS();
        }
    }

    private void startFPS() {
        Intent intent = new Intent(this, FPSService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        Toast.makeText(this, "FPS Meter pornit!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_OVERLAY) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                startFPS();
            } else {
                Toast.makeText(this, "Este necesară permisiunea de afișare peste alte aplicații!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
