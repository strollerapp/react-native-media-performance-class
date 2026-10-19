# @strollerapp/react-native-media-performance-class

Read an Android device's Media Performance Class from React Native, to decide how much video work it can take.

Android only. On iOS and web it resolves `0` and nothing throws.

## What is Media Performance Class?

[Media Performance Class](https://developer.android.com/topic/performance/performance-class) (MPC) is an Android program for Android 12 and later in which a manufacturer declares that a device meets a minimum bar for media work: video decoding and encoding, camera, memory, storage and display. Declared devices must pass the Android Compatibility Test Suite (CTS), and the requirements for each level are listed in the Compatibility Definition Document, for example the [Android 12 handheld media performance class](https://source.android.com/docs/compatibility/12/android-12-cdd#227_handheld_media_performance_class).

A class is named after the Android version whose requirements the device meets: `30` (Android 11), `31` (Android 12), `33` (Android 13), `34` (Android 14) and `35` (Android 15). There is no `32`. `0` means no class.

This is useful when you have to size media work to the device, for example how many videos a feed autoplays at once, or whether to pick a higher quality stream.

## Installation

```sh
npm install @strollerapp/react-native-media-performance-class
```

Autolinking does the rest. There is nothing to install on iOS.

## Usage

```tsx
import { getMediaPerformanceClass } from '@strollerapp/react-native-media-performance-class';

const isHighPerformance = (await getMediaPerformanceClass()) >= 31;
```

## API

### `getMediaPerformanceClass(): Promise<number>`

The class from Jetpack [`PlayServicesDevicePerformance`](https://developer.android.com/reference/kotlin/androidx/core/performance/play/services/PlayServicesDevicePerformance): the class the manufacturer declared, or, for devices that don't declare one, the estimate from Google Play services. It resolves `0` when there is neither. Android 11 and earlier, iOS and web resolve `0`. On Android the promise rejects if the read fails.

The instance is created once per process and read off the main and JS threads.

Play services reports its estimate asynchronously and the library stores it on the device. The value is read once per process and then fixed. On the first launch after install, if the first call comes before Play services has answered, you get the declared value (often `0`), and the estimate shows up from the next launch. Don't treat an early `0` as permanent.

## Requirements

- React Native 0.86 or later, **New Architecture only**. On the legacy architecture the native module is never registered, so the library resolves `0`.
- Android `minSdk` 24 or later, as in the library's `build.gradle`.

## Caveats

- Most mid-range devices, and devices that launched before Android 12, declare no class. The Play services estimate covers some of them.
- The estimate needs Google Play services. Devices without it only have the declared class.
- iOS has no equivalent and always resolves `0`, so treat `0` as unknown, not as low-end.

## Contributing

See the [contributing guide](https://github.com/strollerapp/react-native-media-performance-class/blob/main/CONTRIBUTING.md).

## License

MIT
