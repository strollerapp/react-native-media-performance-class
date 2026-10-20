package com.strollerapp.mediaperformanceclass

import com.facebook.react.BaseReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider

class MediaPerformanceClassPackage : BaseReactPackage() {
  override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? {
    return if (name == MediaPerformanceClassModule.NAME) {
      MediaPerformanceClassModule(reactContext)
    } else {
      null
    }
  }

  override fun getReactModuleInfoProvider() = ReactModuleInfoProvider {
    mapOf(
      MediaPerformanceClassModule.NAME to ReactModuleInfo(
        name = MediaPerformanceClassModule.NAME,
        className = MediaPerformanceClassModule.NAME,
        canOverrideExistingModule = false,
        needsEagerInit = true,
        isCxxModule = false,
        isTurboModule = true
      )
    )
  }
}
