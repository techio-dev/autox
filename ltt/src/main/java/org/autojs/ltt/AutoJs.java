package org.autojs.ltt;

import android.app.Application;
import android.content.Context;

import com.stardust.app.GlobalAppContext;
import com.stardust.autojs.core.console.GlobalConsole;
import com.stardust.autojs.runtime.ScriptRuntime;
import com.stardust.autojs.runtime.api.AppUtils;
import com.stardust.autojs.runtime.accessibility.AccessibilityConfig;
import com.stardust.autojs.runtime.exception.ScriptException;
import com.stardust.autojs.runtime.exception.ScriptInterruptedException;
import com.stardust.view.accessibility.AccessibilityService;

/**
 * AutoJs đơn giản cho LTT module
 * Bao gồm accessibility service cho automation tools
 */
public class AutoJs extends com.stardust.autojs.AutoJs {

    private static AutoJs instance;

    public static AutoJs getInstance() {
        return instance;
    }

    public synchronized static void initInstance(Application application) {
        if (instance != null) {
            return;
        }
        instance = new AutoJs(application);
    }

    private AutoJs(final Application application) {
        super(application);
    }

    @Override
    protected AppUtils createAppUtils(Context context) {
        return new AppUtils(context);
    }

    @Override
    protected GlobalConsole createGlobalConsole() {
        return new GlobalConsole(getUiHandler());
    }

    @Override
    protected AccessibilityConfig createAccessibilityConfig() {
        return super.createAccessibilityConfig();
    }

    @Override
    protected ScriptRuntime createRuntime() {
        return super.createRuntime();
    }

    public void ensureAccessibilityServiceEnabled() {
        if (AccessibilityService.Companion.getInstance() != null) {
            return;
        }
        // Cho LTT module, chỉ throw exception để thông báo cần accessibility service
        throw new ScriptException("Accessibility service is required for automation tools. Please enable it in system settings.");
    }

    @Override
    public void waitForAccessibilityServiceEnabled() {
        if (AccessibilityService.Companion.getInstance() != null) {
            return;
        }
        // Chờ accessibility service được enable
        if (!AccessibilityService.Companion.waitForEnabled(-1)) {
            throw new ScriptInterruptedException();
        }
    }
}