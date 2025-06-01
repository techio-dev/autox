package org.autojs.ltt;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

/**
 * Service chạy nền để thực thi script
 * Dựa trên ScriptExecutionIntentService gốc nhưng được đơn giản hóa
 */
public class ScriptExecutionIntentService extends IntentService {

    public ScriptExecutionIntentService() {
        super("ScriptExecutionIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null)
            return;
        try {
            ScriptIntents.handleIntent(this, intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}