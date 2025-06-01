package org.autojs.ltt;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.widget.Toast;

import com.stardust.app.GlobalAppContext;
import com.stardust.view.accessibility.AccessibilityService;
import com.stardust.view.accessibility.AccessibilityServiceUtils;

/**
 * AccessibilityServiceTool đơn giản cho LTT module
 * Loại bỏ dependency vào app module
 */
public class AccessibilityServiceTool {

    private static final Class<com.stardust.autojs.core.accessibility.AccessibilityService> sAccessibilityServiceClass = 
        com.stardust.autojs.core.accessibility.AccessibilityService.class;

    public static void enableAccessibilityService() {
        goToAccessibilitySetting();
    }

    public static void goToAccessibilitySetting() {
        Context context = GlobalAppContext.get();
        Toast.makeText(context, "Please enable " + context.getPackageName() + " in Accessibility settings", Toast.LENGTH_LONG).show();
        
        try {
            AccessibilityServiceUtils.INSTANCE.goToAccessibilitySetting(context);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Please go to Settings > Accessibility and enable " + context.getPackageName(), Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isAccessibilityServiceEnabled(Context context) {
        return AccessibilityServiceUtils.INSTANCE.isAccessibilityServiceEnabled(context, sAccessibilityServiceClass);
    }
}