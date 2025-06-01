package org.autojs.ltt;

import android.app.Application;
import com.stardust.app.GlobalAppContext;

/**
 * Application class cho LTT module
 * Khởi tạo AutoJs instance
 */
public class LttApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        
        // Khởi tạo GlobalAppContext
        GlobalAppContext.set(this);
        
        // Khởi tạo AutoJs instance
        AutoJs.initInstance(this);
    }
}