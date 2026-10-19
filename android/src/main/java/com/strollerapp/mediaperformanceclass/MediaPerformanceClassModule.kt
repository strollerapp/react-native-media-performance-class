package com.strollerapp.mediaperformanceclass

import androidx.core.performance.play.services.PlayServicesDevicePerformance
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import java.util.concurrent.Executors

class MediaPerformanceClassModule(reactContext: ReactApplicationContext) :
  NativeMediaPerformanceClassSpec(reactContext) {

  override fun getMediaPerformanceClass(promise: Promise) {
    val context = reactApplicationContext.applicationContext

    executor.execute {
      try {
        promise.resolve(devicePerformance(context).mediaPerformanceClass.toDouble())
      } catch (error: Throwable) {
        promise.reject("E_MEDIA_PERFORMANCE_CLASS", error.message, error)
      }
    }
  }

  companion object {
    const val NAME = NativeMediaPerformanceClassSpec.NAME

    private val executor = Executors.newSingleThreadExecutor()

    @Volatile
    private var instance: PlayServicesDevicePerformance? = null

    private fun devicePerformance(context: android.content.Context): PlayServicesDevicePerformance {
      return instance ?: synchronized(this) {
        instance ?: PlayServicesDevicePerformance(context).also { instance = it }
      }
    }
  }
}
