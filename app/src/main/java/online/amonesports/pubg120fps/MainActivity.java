package online.amonesports.pubg120fps;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import rikka.shizuku.Shizuku;

public class MainActivity extends AppCompatActivity {

    private static final int SHIZUKU_PERMISSION_REQUEST_CODE = 1001;
    private WebView webView;

    private final Shizuku.OnRequestPermissionResultListener permissionListener = (requestCode, grantResult) -> {
        if (requestCode == SHIZUKU_PERMISSION_REQUEST_CODE) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                runInjectionScript();
            } else {
                Toast.makeText(MainActivity.this, "Permisiunea Shizuku a fost refuzată!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Shizuku.addRequestPermissionResultListener(permissionListener);

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.addJavascriptInterface(new WebAppInterface(), "AndroidBridge");
        webView.setWebViewClient(new WebViewClient());

        // Încarcă exact adresa ta cu subfolderul pubgm
        webView.loadUrl("https://kode.amonesports.online/pubgm");
    }

    public class WebAppInterface {
        @JavascriptInterface
        public void triggerBooster() {
            runOnUiThread(() -> {
                if (Shizuku.isPreV11() || Shizuku.getVersion() < 11) {
                    Toast.makeText(MainActivity.this, "Te rog actualizează Shizuku!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
                    runInjectionScript();
                } else {
                    try {
                        Shizuku.requestPermission(SHIZUKU_PERMISSION_REQUEST_CODE);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Eroare Shizuku: Pornește Wireless Debugging!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void runInjectionScript() {
        new Thread(() -> {
            try {
                String command = 
                    "PKG='com.tencent.ig';" +
                    "DIR='/data/data/'$PKG'/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/Config/Android';" +
                    "mkdir -p $DIR;" +
                    "cat << 'EOF' > $DIR/UserCustom.ini\n" +
                    "[UserCustom DeviceProfile]\n" +
                    "+CVars=0B572C0A1C0B280C1815100D002A1C0D0D10171E4449\n" +
                    "+CVars=0B572C0A1C0B2A11181D160E2A0E100D1A114449\n" +
                    "+CVars=0B572A11181D160E280C1815100D004449\n" +
                    "+CVars=0B5734161B10151C3A16170D1C170D2A1A18151C3F181A0D160B4448\n" +
                    "+CVars=0B572C0A1C0B2F0C151218172A1C0D0D10171E4449\n" +
                    "+CVars=0B572C0A1C0B3C171C0B1E002A180F10171E4449\n" +
                    "+CVars=0B5734161B10151C313D2B44495749\n" +
                    "+CVars=0B5734161B10151C572A1A1C171C3A1615160B3F160B14180D44495749\n" +
                    "+CVars=0B572F2B2A573F160B1A1C2A11181D10171E2B180D1C4454485749\n" +
                    "+CVars=0B5734161B10151C573C17181B151C29292B44495749\n" +
                    "+CVars=0B572A11181D160E573418013A2A342B1C0A16150C0D101617444D\n" +
                    "+CVars=0B5734180D1C0B101815280C1815100D002A0C091C0B31101E1144495749\n" +
                    "+CVars=0B572A1200380D14160A09111C0B1C44495749\n" +
                    "+CVars=0B573E2D383657280C1815100D004449\n" +
                    "+CVars=0B572C0A1C0B313D2B2A1C0D0D10171E444B\n" +
                    "+CVars=0B57383A3C2A2A0D00151C444B\n" +
                    "+CVars=0B572C0A1C0B342A38382A1C0D0D10171E4449\n" +
                    "+CVars=0B572C0A1C0B342A38382F18150C1C4449\n" +
                    "+CVars=0B573D1C1F180C150D3F1C180D0C0B1C5738170D10381510180A10171E44495749\n" +
                    "+CVars=0B5734161B10151C342A383844485749\n" +
                    "+CVars=0B57342A38383A160C170D444D5749\n" +
                    "+CVars=0B5730171D100F101D0C181529180B0D101A151C35363D3B10180A444B\n" +
                    "+CVars=0B5734180D1C0B101815280C1815100D00351C0F1C154449\n" +
                    "+CVars=0B572A11181D160E573A2A345734180134161B10151C3A180A1A181D1C0A4449\n" +
                    "+CVars=0B572A11181D160E573D100A0D18171A1C2A1A18151C4449\n" +
                    "+CVars=0B5734161B10151C573D00171814101A361B131C1A0D2A11181D160E4449\n" +
                    "+CVars=0B572A0D180D101A341C0A1135363D3D100A0D18171A1C2A1A18151C4448574A\n" +
                    "+CVars=1F161510181E1C5735363D3D100A0D18171A1C2A1A18151C4449574F\n" +
                    "+CVars=0B573D1C0D18101534161D1C4449\n" +
                    "+CVars=0B572A0D0B1C181410171E57291616152A10031C444B4949\n" +
                    "+CVars=0B573C14100D0D1C0B2A09180E172B180D1C2A1A18151C4449574C\n" +
                    "+CVars=0B5729180B0D101A151C35363D3B10180A444B\n" +
                    "+CVars=1F01573710181E180B1835363D3B10180A444B\n" +
                    "+CVars=0B5734161B10151C370C143D00171814101A291610170D35101E110D0A4449\n" +
                    "+CVars=0B573D1C090D11361F3F101C151D280C1815100D004449\n" +
                    "+CVars=0B572B1C1F0B181A0D101617280C1815100D004449\n" +
                    "+CVars=1F161510181E1C5734101735363D4449\n" +
                    "+CVars=0B573418013817100A160D0B160900444D\n" +
                    "+CVars=1D1000572A1C0D3D1C1A18153B181210171E2B2D2A10031C301735161B1B004448494B4D\n" +
                    "+CVars=0B57292C3B3E2F1C0B0A101617444C\n" +
                    "+CVars=0B5734161B10151C2A101409151C2A11181D1C0B4449\n" +
                    "EOF\n" +
                    "chmod 444 $DIR/UserCustom.ini;\n" +
                    "chown 1000:1000 $DIR/UserCustom.ini;";

                Process process = Shizuku.newProcess(new String[]{"sh", "-c", command}, null, null);
                int exitCode = process.waitFor();

                runOnUiThread(() -> {
                    if (exitCode == 0) {
                        Toast.makeText(MainActivity.this, "[SUCCES] 120 FPS activate prin Shizuku!", Toast.LENGTH_LONG).show();
                        webView.evaluateJavascript("addLogMessage('[ok] 120 FPS activate cu succes!', '#00ff66');", null);
                    } else {
                        Toast.makeText(MainActivity.this, "Eroare la scriere.", Toast.LENGTH_LONG).show();
                        webView.evaluateJavascript("addLogMessage('[-] Eroare la injectare.', '#ff3333');", null);
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Eroare: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Shizuku.removeRequestPermissionResultListener(permissionListener);
    }
}
