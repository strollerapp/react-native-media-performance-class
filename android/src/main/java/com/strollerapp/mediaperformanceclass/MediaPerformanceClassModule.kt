package com.strollerapp.mediaperformanceclass

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import java.util.concurrent.Executors

class MediaPerformanceClassModule(reactContext: ReactApplicationContext) :
  NativeMediaPerformanceClassSpec(reactContext) {

  init {
    MediaPerformanceClassStore.prefetch(reactContext.applicationContext)
  }

  override fun getMediaPerformanceClass(promise: Promise) {
    val context = reactApplicationContext.applicationContext

    executor.execute {
      promise.resolve(MediaPerformanceClassStore.get(context).toDouble())
    }
  }

  companion object {
    const val NAME = NativeMediaPerformanceClassSpec.NAME

    private val executor = Executors.newSingleThreadExecutor()
  }
}
