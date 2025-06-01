package org.autojs.ltt;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.Toast;

import com.stardust.autojs.script.StringScriptSource;
import com.stardust.pio.PFiles;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Activity chạy nền để thực thi script từ intent
 * Dựa trên RunIntentActivity gốc nhưng được đơn giản hóa
 */
public class RunIntentActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            handleIntent(getIntent());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi xử lý intent: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        finish();
    }

    private void handleIntent(Intent intent) throws FileNotFoundException {
        Uri uri = intent.getData();
        if (uri != null && "content".equals(uri.getScheme())) {
            // Xử lý content URI
            InputStream stream = getContentResolver().openInputStream(uri);
            String scriptContent = PFiles.read(stream);
            ScriptIntents.executeScript(this, new StringScriptSource(scriptContent));
        } else {
            // Xử lý các loại intent khác
            ScriptIntents.handleIntent(this, intent);
        }
    }
}