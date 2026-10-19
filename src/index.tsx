import NativeMediaPerformanceClass from './NativeMediaPerformanceClass';

/**
 * The Media Performance Class declared by the device, or Google Play
 * services' estimate for devices that do not declare one. Resolves `0` when
 * neither is available, below Android 12 and on other platforms.
 */
export function getMediaPerformanceClass(): Promise<number> {
  if (NativeMediaPerformanceClass == null) {
    return Promise.resolve(0);
  }

  return NativeMediaPerformanceClass.getMediaPerformanceClass();
}
