package org.autojs.ltt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.stardust.autojs.execution.ExecutionConfig;
import com.stardust.autojs.script.ScriptSource;
import com.stardust.autojs.script.StringScriptSource;

import org.autojs.ltt.AutoJs;

/**
 * Xử lý các intent liên quan đến script
 * Dựa trên ScriptIntents gốc nhưng được đơn giản hóa
 */
public class ScriptIntents {

    public static final String EXTRA_KEY_PRE_EXECUTE_SCRIPT = "script";
    public static final String EXTRA_KEY_NAME = "name";
    public static final String EXTRA_KEY_LOOP_TIMES = "loop";
    public static final String EXTRA_KEY_LOOP_INTERVAL = "interval";
    public static final String EXTRA_KEY_DELAY = "delay";

    public static boolean isTaskerBundleValid(Bundle bundle) {
        return bundle.containsKey(EXTRA_KEY_PRE_EXECUTE_SCRIPT);
    }

    public static boolean handleIntent(Context context, Intent intent) {
        String script = intent.getStringExtra(ScriptIntents.EXTRA_KEY_PRE_EXECUTE_SCRIPT);
        // LOG: Debugging getStringExtra issue - Intent API only accepts 1 parameter
        // Original problematic line: String name = intent.getStringExtra(EXTRA_KEY_NAME, "main");
        String name = intent.getStringExtra(EXTRA_KEY_NAME);
        if (name == null) {
            name = "main"; // Apply default value manually
        }
        int loopTimes = intent.getIntExtra(EXTRA_KEY_LOOP_TIMES, 1);
        long delay = intent.getLongExtra(EXTRA_KEY_DELAY, 0);
        long interval = intent.getLongExtra(EXTRA_KEY_LOOP_INTERVAL, 0);
        
        if (script == null) {
            return false;
        }
        
        ScriptSource source = new StringScriptSource(name, script);
        ExecutionConfig config = new ExecutionConfig();
        config.setDelay(delay);
        config.setLoopTimes(loopTimes);
        config.setInterval(interval);
        config.setArgument("intent", intent);
        
        return executeScript(context, source, config);
    }

    public static boolean executeScript(Context context, ScriptSource source) {
        return executeScript(context, source, new ExecutionConfig());
    }

    public static boolean executeScript(Context context, ScriptSource source, ExecutionConfig config) {
        try {
            AutoJs.getInstance().getScriptEngineService().execute(source, config);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}