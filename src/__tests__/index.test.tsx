import { afterEach, describe, expect, it, jest } from '@jest/globals';

function loadWithNativeModule(nativeModule: object | null) {
  jest.resetModules();
  jest.doMock('react-native', () => ({
    TurboModuleRegistry: { get: () => nativeModule },
  }));

  return require('../index') as typeof import('../index');
}

afterEach(() => {
  jest.dontMock('react-native');
});

describe('getMediaPerformanceClass', () => {
  it('resolves 0 without a native module (iOS, web)', async () => {
    const lib = loadWithNativeModule(null);

    await expect(lib.getMediaPerformanceClass()).resolves.toBe(0);
  });

  it('resolves the value from the native module (Android)', async () => {
    const getMediaPerformanceClass = jest.fn(() => Promise.resolve(33));
    const lib = loadWithNativeModule({ getMediaPerformanceClass });

    await expect(lib.getMediaPerformanceClass()).resolves.toBe(33);
    expect(getMediaPerformanceClass).toHaveBeenCalledTimes(1);
  });
});
