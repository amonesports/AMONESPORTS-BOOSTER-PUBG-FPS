package me.binhmod.fps;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AmonKodeView {
    private final Context context;
    private final WindowManager windowManager;
    private LinearLayout floatingView;
    private boolean isDarkTheme = true;

    public AmonKodeView(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public void show() {
        floatingView = new LinearLayout(context);
        floatingView.setOrientation(LinearLayout.VERTICAL);
        floatingView.setPadding(30, 30, 30, 30);
        updateThemeStyle();

        TextView header = new TextView(context);
        header.setText("⚡ AMONKODE POPUP");
        header.setTextColor(Color.parseColor("#38BDF8"));
        header.setTextSize(14);
        header.setGravity(Gravity.CENTER);
        floatingView.addView(header);

        TextView fpsText = new TextView(context);
        fpsText.setText("FPS: 60 (OPTIMIZAT)");
        fpsText.setTextColor(Color.parseColor("#10B981"));
        fpsText.setTextSize(16;
        fpsText.setGravity(Gravity.CENTER);
        fpsText.setPadding(0, 15, 0, 15);
        floatingView.addView(fpsText);

        // Buton pentru schimbarea temei direct din pop-up
        TextView themeToggle = new TextView(context);
        themeToggle.setText("🎨 Schimbă Tema");
        themeToggle.setTextColor(Color.parseColor("#FFFFFF"));
        themeToggle.setBackgroundColor(Color.parseColor("#334155"));
        themeToggle.setGravity(Gravity.CENTER);
        themeToggle.setPadding(15, 15, 15, 15);
        themeToggle.setOnClickListener(v -> {
            isDarkTheme = !isDarkTheme;
            updateThemeStyle();
        });
        floatingView.addView(themeToggle);

        int layoutParamType;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParamType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParamType = WindowManager.LayoutParams.TYPE_PHONE;
        }

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutParamType,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 100;
        params.y = 200;

        windowManager.addView(floatingView, params);
    }

    private void updateThemeStyle() {
        if (floatingView != null) {
            if (isDarkTheme) {
                floatingView.setBackgroundColor(Color.parseColor("#CC0F172A")); // Dark semi-transparent
            } else {
                floatingView.setBackgroundColor(Color.parseColor("#CCFFFFFF")); // Light semi-transparent
            }
        }
    }

    public void destroy() {
        if (floatingView != null && windowManager != null) {
            windowManager.removeView(floatingView);
            floatingView = null;
        }
    }
}
