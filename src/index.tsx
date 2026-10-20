import NativeMediaPerformanceClass from './NativeMediaPerformanceClass';

/**
 * The higher of the Media Performance Class declared by the device and Google
 * Play services' estimate. Resolves `0` when neither is available and on other
 * platforms. Never rejects.
 */
export function getMediaPerformanceClass(): Promise<number> {
  if (NativeMediaPerformanceClass == null) {
    return Promise.resolve(0);
  }

  return NativeMediaPerformanceClass.getMediaPerformanceClass();
}
