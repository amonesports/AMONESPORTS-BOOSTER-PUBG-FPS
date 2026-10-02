package me.binhmod.fps;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.graphics.Color;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_OVERLAY = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(40, 40, 40, 40);
        layout.setBackgroundColor(Color.parseColor("#0F172A"));

        TextView title = new TextView(this);
        title.setText("AMONKODE BOOSTER");
        title.setTextColor(Color.parseColor("#38BDF8"));
        title.setTextSize(24);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 60);
        layout.addView(title);

        Button btnStart = new Button(this);
        btnStart.setText("PORNEȘTE AMONKODE (ON)");
        btnStart.setBackgroundColor(Color.parseColor("#10B981"));
        btnStart.setTextColor(Color.parseColor("#FFFFFF"));
        btnStart.setOnClickListener(v -> checkOverlayAndStart());
        layout.addView(btnStart);

        Button btnStop = new Button(this);
        btnStop.setText("oprește AMONKODE (OFF)");
        btnStop.setBackgroundColor(Color.parseColor("#EF4444"));
        btnStop.setTextColor(Color.parseColor("#FFFFFF"));
        btnStop.setOnClickListener(v -> {
            stopService(new Intent(this, AmonKodeService.class));
            Toast.makeText(this, "AmonKode a fost oprit!", Toast.LENGTH_SHORT).show();
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 40, 0, 0);
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
            startAmonKode();
        }
    }

    private void startAmonKode() {
        Intent intent = new Intent(this, AmonKodeService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        Toast.makeText(this, "AmonKode pornit!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_OVERLAY) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                startAmonKode();
            } else {
                Toast.makeText(this, "Permisiunea de suprapunere este necesară!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
