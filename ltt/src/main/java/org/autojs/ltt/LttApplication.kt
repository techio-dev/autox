package org.autojs.ltt

import androidx.multidex.MultiDexApplication
import com.stardust.app.GlobalAppContext
import java.lang.ref.WeakReference

/**
 * LTT Application class - Simplified version of main App
 * Khởi tạo AutoJs instance cho LTT module
 */
class LttApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        
        // Khởi tạo GlobalAppContext với đúng signature (Application + BuildConfig)
        GlobalAppContext.set(
            this, 
            com.stardust.app.BuildConfig.generate(BuildConfig::class.java)
        )
        
        // Store instance reference
        instance = WeakReference(this)
        
        // Khởi tạo AutoJs instance
        init()
    }
    
    private fun init() {
        // Khởi tạo AutoJs instance cho LTT
        AutoJs.initInstance(this)
    }
    
    companion object {
        private lateinit var instance: WeakReference<LttApplication>
        
        /**
         * Get application instance
         */
        val app: LttApplication
            get() = instance.get()!!
    }
}