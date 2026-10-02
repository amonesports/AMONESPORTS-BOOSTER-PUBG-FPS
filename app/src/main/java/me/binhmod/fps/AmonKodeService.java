package me.binhmod.fps;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AmonKodeService extends Service {
    private AmonKodeView amonKodeView;

    @Override
    public void onCreate() {
        super.onCreate();
        amonKodeView = new AmonKodeView(this);
        amonKodeView.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (amonKodeView != null) {
            amonKodeView.destroy();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
