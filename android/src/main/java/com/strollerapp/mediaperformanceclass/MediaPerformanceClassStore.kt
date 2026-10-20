package com.strollerapp.mediaperformanceclass

import android.content.Context
import android.os.Build
import androidx.core.performance.DefaultDevicePerformance
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.deviceperformance.DevicePerformance
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

internal object MediaPerformanceClassStore {
  private const val PREFERENCES_NAME = "media_performance_class"
  private const val GOOGLE_CLASS_KEY = "google_media_performance_class"
  private const val BUILD_FINGERPRINT_KEY = "build_fingerprint"
  private const val FIRST_SESSION_TIMEOUT_MS = 1000L

  private val listenerExecutor = Executors.newSingleThreadExecutor()

  private val declaredClass by lazy {
    runCatching { DefaultDevicePerformance().mediaPerformanceClass }.getOrDefault(0)
  }

  @Volatile
  private var request: Task<Int>? = null

  fun prefetch(context: Context) {
    synchronized(this) {
      if (request != null || !hasPlayServices(context)) {
        return
      }

      request = runCatching {
        DevicePerformance.getClient(context)
          .mediaPerformanceClass()
          .addOnSuccessListener(listenerExecutor) { googleClass ->
            runCatching { storeGoogleClass(context, googleClass) }
          }
      }.getOrNull()
    }
  }

  fun get(context: Context) = runCatching {
    maxOf(declaredClass, storedGoogleClass(context) ?: awaitGoogleClass())
  }.getOrDefault(declaredClass)

  private fun storedGoogleClass(context: Context): Int? {
    val preferences = preferences(context)

    if (preferences.getString(BUILD_FINGERPRINT_KEY, null) != Build.FINGERPRINT) {
      return null
    }

    return preferences.getInt(GOOGLE_CLASS_KEY, 0)
  }

  private fun awaitGoogleClass(): Int {
    val pendingRequest = request ?: return 0

    return runCatching {
      Tasks.await(pendingRequest, FIRST_SESSION_TIMEOUT_MS, TimeUnit.MILLISECONDS)
    }.getOrDefault(0)
  }

  private fun storeGoogleClass(context: Context, googleClass: Int) {
    preferences(context)
      .edit()
      .putInt(GOOGLE_CLASS_KEY, googleClass)
      .putString(BUILD_FINGERPRINT_KEY, Build.FINGERPRINT)
      .apply()
  }

  private fun hasPlayServices(context: Context) = runCatching {
    GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) ==
      ConnectionResult.SUCCESS
  }.getOrDefault(false)

  private fun preferences(context: Context) =
    context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
}
